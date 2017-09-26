package org.verlet.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtil {

    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private static Properties props;

    static{
        /**
         * 默认加载当前模块下的autumn.properties文件
         */
        loadProperties("autumn.properties");
    }

    /**
      * @param filaName 以classpath路径为参考
     */
    private static void loadProperties(String filaName){
        if(props == null){
            props = new Properties();
        }
        try{
            props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(filaName),"UTF-8"));
        }catch (IOException e){
            logger.error("{} 配置文件读取异常", filaName,e);
            e.printStackTrace();
        }
    }

    public static String getProperty(String key){
        String value = props.getProperty(key.trim());
        if(StringUtils.isBlank(value)){
            logger.info("配置文件中没有key为{}的配置",key);
            return null;
        }
        return value.trim();
    }

    public static String getProperty(String key,String defaultValue){
        String value = props.getProperty(key.trim());
        if(StringUtils.isBlank(value)){
            value = defaultValue;
        }
        return value.trim();
    }
}
