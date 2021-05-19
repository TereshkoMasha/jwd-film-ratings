package com.epam.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReaderUtil {
    private static PropertyReaderUtil readerUtil;
    private static final Logger logger = LogManager.getLogger(PropertyReaderUtil.class);
    private static Properties properties;
    private final DatabaseProperties databaseProperties;

    private PropertyReaderUtil() {
        readProperties();
        databaseProperties = new DatabaseProperties(properties);
    }

    public static PropertyReaderUtil getInstance() {
        if (readerUtil == null) {
            readerUtil = new PropertyReaderUtil();
        }
        return readerUtil;
    }

    private static void readProperties() {
        properties = new Properties();
        try (InputStream inputStream = PropertyReaderUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Failed while loading properties file", e);
        }
    }

    public DatabaseProperties getDatabaseProperties() {
        return databaseProperties;
    }
}
