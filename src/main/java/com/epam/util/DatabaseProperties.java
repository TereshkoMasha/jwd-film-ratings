package com.epam.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public final class DatabaseProperties {
    private static final Logger LOGGER = LogManager.getLogger(DatabaseProperties.class);
    private String url;
    private String user;
    private String password;
    private Integer initPoolSize;
    private Integer maxPoolSize;
    private String characterEncoding;

    DatabaseProperties(Properties properties) {
        initConfiguration(properties);
        LOGGER.info("Database config added successfully");
    }

    private void initConfiguration(Properties properties) {
        url = properties.getProperty("db.url");
        user = properties.getProperty("user");
        password = properties.getProperty("password");
        initPoolSize = Integer.parseInt(properties.getProperty("initpoolsize"));
        maxPoolSize = Integer.parseInt(properties.getProperty("maxpoolsize"));
        characterEncoding= properties.getProperty("characterEncoding");
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public Integer getInitPoolSize() {
        return initPoolSize;
    }

    public Integer getMaxPoolSize() {
        return maxPoolSize;
    }

    public String getCharacterEncoding() {
        return characterEncoding;
    }

}

