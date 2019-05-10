package com.sankuai.inf.hulk;
/**
 * Created by hujunxiang on 2019-04-26
 */

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
            hulkConfig=load("/conf/HulkConfig.yaml");
        }
        return hulkConfig;
    }

    public static HulkConfig load(String path){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(HulkConfig.class.getResourceAsStream(path), HulkConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
