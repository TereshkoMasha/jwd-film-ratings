package com.epam.db;


import com.epam.util.PropertyReaderUtil;

import java.util.Properties;

public final class DatabaseConfiguration {

    private static DatabaseConfiguration instance;
    private String url;
    private String user;
    private String password;
    private Integer initPoolSize;
    private Integer maxPoolSize;

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
        }
        return instance;
    }


    private DatabaseConfiguration() {
        initConfiguration();
    }

    public void initConfiguration() {
        Properties properties = PropertyReaderUtil.readProperties();
        url = properties.getProperty("com.epam.db.url");
        user = properties.getProperty("com.epam.db.user");
        password = properties.getProperty("com.epam.db.password");
        initPoolSize = Integer.parseInt(properties.getProperty("com.epam.db.initpoolsize"));
        maxPoolSize = Integer.parseInt(properties.getProperty("com.epam.db.maxpoolsize"));
    }


}

