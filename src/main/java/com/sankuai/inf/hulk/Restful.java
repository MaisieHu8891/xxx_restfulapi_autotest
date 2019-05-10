package com.sankuai.inf.hulk;
/**
 * Created by hujunxiang on 2019-04-26
 */

import java.util.HashMap;

public class Restful {
    public String appkey;
    public String protocol;
    public String method;
    public String uri;
    public HashMap<String, String> headers = new HashMap<String, String>();
    public HashMap<String, String> query=new HashMap<String, String>();
    public String body;
}