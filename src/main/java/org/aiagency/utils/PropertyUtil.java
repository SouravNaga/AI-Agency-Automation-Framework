package org.aiagency.utils;

import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

    private static Properties properties = new Properties();

    static {
        try {
            InputStream is =
                    PropertyUtil.class
                            .getClassLoader()
                            .getResourceAsStream("config.properties");

            properties.load(is);
        } catch (Exception e) {
            throw new RuntimeException("Unable to load config.properties");
        }
    }

    public static String getStepsFile() {
        return properties.getProperty("filename");
    }
}
