package com.epam.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReaderUtil {
    private static final Logger logger = LogManager.getLogger(PropertyReaderUtil.class);

    private PropertyReaderUtil() {

    }

    public static Properties readProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = PropertyReaderUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Failed while loading properties file", e);
        }
        return properties;
    }

}
