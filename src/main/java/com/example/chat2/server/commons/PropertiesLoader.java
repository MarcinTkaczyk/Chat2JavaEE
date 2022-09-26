package com.example.chat2.server.commons;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    public static Properties loadProperties(){
        Properties properties = new Properties();
        try(InputStream in = PropertiesLoader.class
                .getClassLoader().getResourceAsStream("config.properties")){
            properties.load(in);
        } catch (IOException e) {
            e.getMessage();
        }
    return properties;
    }
}
