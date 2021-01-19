package com.fan.tank.util;

import java.io.IOException;
import java.util.Properties;

public class PropertyMgr {
    private static final Properties props = new Properties();

    static {
        try {
            props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String get(String key) {
        return (String) props.get(key);
    }

    public static void main(String[] args) {
        System.out.println(props.get("tankFireStrategy"));
    }
}
