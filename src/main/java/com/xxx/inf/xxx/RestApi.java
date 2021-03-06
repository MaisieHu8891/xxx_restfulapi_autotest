package com.xxx.inf.xxx;

/**
 * 封装rest assured, 提供不同的方式去请求，获取返回
 * Created by hujunxiang on 2019-04-26
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.Har;
import de.sstoehr.harreader.model.HarEntry;
import de.sstoehr.harreader.model.HarRequest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class RestApi {
    private static final Logger logger = LoggerFactory.getLogger(RestApi.class);

    HashMap<String, Object> query = new HashMap<String, Object>();

    public RestApi() {
        useRelaxedHTTPSValidation();
    }

    public RequestSpecification getDefaultRequestSpecification() {
        return given();
    }

    /**
     * 多环境支持，根据domain，组装url
     */
    private String composeUrl(String uri, String appkey, String protocol) {
        HashMap<String, String> appkeyDomain = xxxConfig.getInstance().env.get(xxxConfig.getInstance().current);
        String domain = "";
        String url = "";
        for (Map.Entry<String, String> entry : appkeyDomain.entrySet()) {
            if (appkey.contains(entry.getKey())) {
                domain = entry.getValue();
                url = protocol + "://" + domain + "/" + uri;
                logger.debug("当前环境下的地址是： " + url);
                return url;
            }
        }
        return null;
    }


    public Restful getApiFromYaml(String path) {

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(RestApi.class.getResourceAsStream(path), Restful.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 通过抓包，将请求存到har文件，该方法更适用于有前端的项目
     */
    public Restful getApiFromHar(String path, String pattern) {
        HarReader harReader = new HarReader();
        try {
            Har har = harReader.readFromFile(new File(
                    URLDecoder.decode(
                            getClass().getResource(path).getPath(), "utf-8"
                    )));
            HarRequest request = new HarRequest();
            Boolean match = false;
            for (HarEntry entry : har.getLog().getEntries()) {
                request = entry.getRequest();
                if (request.getUrl().matches(pattern)) {
                    match = true;
                    break;
                }
            }

            if (match == false) {
                request = null;
                throw new Exception("match false");
            }

            Restful restful = new Restful();
            restful.method = request.getMethod().name().toLowerCase();
            restful.uri = request.getUrl().replaceAll("(.*)//(.*)/", "").replaceAll("\\?(.*)", "");
            request.getQueryString().forEach(q -> {
                restful.query.put(q.getName(), q.getValue());
            });
            restful.body = request.getPostData().getText();
            return restful;
        } catch (HarReaderException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public Restful updateApiFromMap(Restful restful, HashMap<String, Object> map) {
        if (map == null) {
            return restful;
        }
        if (restful.method.toLowerCase().contains("get")) {
            map.entrySet().forEach(entry -> {
                restful.query.replace(entry.getKey(), entry.getValue().toString());
            });
        }
        if (restful.method.toLowerCase().contains("post")) {
            if (map.containsKey("_body")) {
                //map本身是json数据
                restful.body = map.get("_body").toString();
            }
            if (map.containsKey("_file")) {
                //map是一个给定的json文件，从中取json数据
                String filePath = map.get("_file").toString();
                map.remove("_file");
                restful.body = templateFromJsonFile(filePath, map);
            }
        }
        return restful;
    }


    public static String templateFromJsonFile(String path, HashMap<String, Object> map) {
        DocumentContext documentContext = JsonPath.parse(RestApi.class
                .getResourceAsStream(path));
        map.entrySet().forEach(entry -> {
            documentContext.set(entry.getKey(), entry.getValue());
        });
        return documentContext.jsonString();
    }

//    /**
//     * 如果post数据不是json格式，可使用Mustache模板技术
//     */
//    public static String templateFromMustache(String tempFileName, HashMap<String, Object> map) {
//        try {
//            String filePath = this.getClass().getClassLoader().getResource(tempFileName).getPath();
//            logger.debug(filePath);
//            Writer writer = new OutputStreamWriter(System.out);
//            MustacheFactory mf = new DefaultMustacheFactory();
//            Mustache mustache = mf.compile(filePath);
//            mustache.execute(writer,map);
//            writer.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    /**
     * 根据yaml生成接口定义并发送
     */
    public Response getResponseFromYaml(String path, HashMap<String, Object> map) {

        Restful restful = getApiFromYaml(path);
        restful = updateApiFromMap(restful, map);

        RequestSpecification requestSpecification = getDefaultRequestSpecification();

        if (restful.query != null) {
            restful.query.entrySet().forEach(entry -> {
                requestSpecification.queryParam(entry.getKey(), entry.getValue());
            });
        }
        if (restful.body != null) {
            requestSpecification.body(restful.body);
        }
        String url = composeUrl(restful.uri, restful.appkey, restful.protocol);
        if (url != null) {
            return requestSpecification
                    .when().request(restful.method, url)
                    .then().extract().response();

        }
        return null;

    }

    public Response getResponseFromHar(String path, String pattern, HashMap<String, Object> map) {
        Restful restful = getApiFromHar(path, pattern);
        restful = updateApiFromMap(restful, map);
        return getResponseFromRestful(restful);
    }

    public Response getResponseFromRestful(Restful restful) {
        RequestSpecification requestSpecification = getDefaultRequestSpecification();
        if (restful.query != null) {
            restful.query.entrySet().forEach(entry -> {
                requestSpecification.queryParam(entry.getKey(), entry.getValue());
            });
        }
        if (restful.body != null) {
            requestSpecification.body(restful.body);
        }
        String url = composeUrl(restful.uri, restful.appkey, restful.protocol);
        if (url != null) {
            return requestSpecification
                    .when().request(restful.method, url)
                    .then().extract().response();

        }
        return null;
    }


}

