package com.sankuai.inf.hulk;

import java.util.HashMap;

public class Restful {
    public String url;
    public String method;
    public HashMap<String, String> headers = new HashMap<String, String>();
    public HashMap<String, String> query=new HashMap<String, String>();
    public String body;
}