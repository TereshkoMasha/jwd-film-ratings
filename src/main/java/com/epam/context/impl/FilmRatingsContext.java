package com.epam.context.impl;

import com.epam.context.ApplicationContext;
import com.epam.entity.BaseEntity;
import com.epam.entity.Movie;
import com.epam.exception.UnknownEntityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;

public class FilmRatingsContext implements ApplicationContext {

    private static final Logger logger = LogManager.getLogger(FilmRatingsContext.class);
    private static final FilmRatingsContext instance = new FilmRatingsContext();

    private Collection<Movie> movies = new ArrayList<>();


    @Override
    public <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass) {
        switch (tClass.getSimpleName()) {
            case "Movie": {
                return (Collection<T>) movies;
            }
            default: {
                throw new UnknownEntityException("Invalid entity - " + tClass);
            }
        }
    }

    @Override
    public void init() throws UnknownEntityException {
        logger.info("FilmRatings context launched");
    }

    public static FilmRatingsContext getInstance() {
        return instance;
    }
}
