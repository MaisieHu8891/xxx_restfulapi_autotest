package com.xxx.inf.xxx.model2;
/**
 * Created by hujunxiang on 2019-04-26
 */
import com.xxx.inf.xxx.RestApi;
import io.restassured.builder.ResponseBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class model2xxxApi extends RestApi {

    @Override
    public RequestSpecification getDefaultRequestSpecification() {
        RequestSpecification requestSpecification = super.getDefaultRequestSpecification();
        requestSpecification.contentType(ContentType.JSON);
       /**
        * 需要时，可对请求 ，响应做修改， 比如加密、修改内容、格式等
       */
        requestSpecification.filter((req, res, ctx) -> {
        //eg:
        //if(req.getURI().matches("xxx")){
        //req.header("","");
        //}
        //return ctx.next(req, res);
            Response resOrgin = ctx.next(req,res);
            ResponseBuilder responseBuilder = new ResponseBuilder().clone(resOrgin);
            responseBuilder.setContentType(ContentType.JSON);//发现很多接口返回格式不是json,而是string但内容是json样式的，为了方便使用json path校验返回结果，这里设置为json
            Response resNew = responseBuilder.build();
            return resNew;
    });
        return requestSpecification;
    }
}
