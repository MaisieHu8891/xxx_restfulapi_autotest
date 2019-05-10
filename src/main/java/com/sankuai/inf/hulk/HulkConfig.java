package com.sankuai.inf.hulk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.util.HashMap;

public class HulkConfig {

    public String current="test";
    public HashMap<String, HashMap<String, String>> env;

    private static HulkConfig hulkConfig;
    public static HulkConfig getInstance(){
        if(hulkConfig==null){
            hulkConfig=load("/conf/WeworkConfig.yaml");
            System.out.println(hulkConfig);
        }
        return hulkConfig;
    }

    public static HulkConfig load(String path){
        //fixed: read from yaml or json
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(HulkConfig.class.getResourceAsStream(path), HulkConfig.class);
            //System.out.println(mapper.writeValueAsString(WeworkConfig.getInstance()));

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
