package com.sankuai.inf.hulk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;

public class HulkConfig {

    private static final Logger logger = LoggerFactory.getLogger(HulkConfig.class);
    public String current="test";
    public HashMap<String, HashMap<String, String>> env;
    private static HulkConfig hulkConfig;

    public static HulkConfig getInstance(){
        if(hulkConfig==null){
            hulkConfig=load("/conf/HulkConfig.yaml");
            logger.debug(hulkConfig.toString());
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
