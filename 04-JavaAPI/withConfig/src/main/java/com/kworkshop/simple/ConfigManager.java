package com.kworkshop.simple;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ConfigManager {
    public static Config config = ConfigFactory.load();


    public static Properties getProducerConfig(){
        return toProperties("kafka.producer");
    }

    public static Properties getConsumerConfig(){
        return toProperties("kafka.consumer");
    }

    public static Properties toProperties(String confName){

        Properties properties = new Properties();
        config.getConfig(confName)
                .entrySet()
                .forEach(e -> properties.setProperty(e.getKey(), e.getValue().unwrapped().toString()));
        return properties;
    }

    public static String getTopicName(){
        return config.getString("kafka.topic-name");
    }
}
