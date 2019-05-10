package com.sankuai.inf.hulk.registryapi;

import com.sankuai.inf.hulk.RestApi;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RegistryApi extends RestApi {

    @Override
    public RequestSpecification getDefaultRequestSpecification() {
        RequestSpecification requestSpecification = super.getDefaultRequestSpecification();
        requestSpecification.contentType(ContentType.JSON);

        requestSpecification.filter((req, res, ctx) -> {
            //todo: 需要时，可对请求 ，响应做封装， 比如加密
            return ctx.next(req, res);
        });
        return requestSpecification;

    }
}
