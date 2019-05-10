package com.sankuai.inf.hulk.registryapi;


import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class Repository extends RegistryApi{
    private static final Logger logger = LoggerFactory.getLogger(Repository.class);

    public Response pathStatus(HashMap<String, Object> map){
        map.put("_file", "/repositorydata/path_status.json");
        return getResponseFromYaml("/repositoryapi/path_status.yaml", map);

    }

    public Response pathList(String digest,Integer syncType){
        HashMap<String, Object> map=new HashMap<String, Object>();
        map.put("digest", digest);
        map.put("syncType", syncType);
        return getResponseFromYaml("/repositoryapi/path_list.yaml", map);

    }

}
