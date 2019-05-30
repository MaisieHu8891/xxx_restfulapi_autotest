package com.xxx.inf.xxx;
/**
 * 从文件获取全局配置，单例
 * Created by hujunxiang on 2019-04-26
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.util.HashMap;

public class xxxConfig {

    public String current="test";
    public HashMap<String, HashMap<String, String>> env;
    private static xxxConfig xxxConfig;

    public static xxxConfig getInstance(){
        if(xxxConfig ==null){
            xxxConfig =load("/conf/xxxxConfig.yaml");
        }
        return xxxConfig;
    }

    public static xxxConfig load(String path){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(xxxConfig.class.getResourceAsStream(path), xxxConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
