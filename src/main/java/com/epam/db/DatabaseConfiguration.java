package com.epam.db;


import com.epam.util.PropertyReaderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class DatabaseConfiguration {
    private static final Logger LOGGER = LogManager.getLogger(DatabaseConfiguration.class);
    private static DatabaseConfiguration instance;
    private String url;
    private String user;
    private String password;
    private Integer initPoolSize;
    private Integer maxPoolSize;

    private DatabaseConfiguration() {
        initConfiguration();
    }

    private void initConfiguration() {
        var properties = PropertyReaderUtil.readProperties();
        url = properties.getProperty("db.url");
        user = properties.getProperty("user");
        password = properties.getProperty("password");
        initPoolSize = Integer.parseInt(properties.getProperty("initpoolsize"));
        maxPoolSize = Integer.parseInt(properties.getProperty("maxpoolsize"));
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

    public static DatabaseConfiguration getInstance() {
        if (instance == null) {
            instance = new DatabaseConfiguration();
            LOGGER.info("Database config added successfully");
        }
        return instance;
    }


}

