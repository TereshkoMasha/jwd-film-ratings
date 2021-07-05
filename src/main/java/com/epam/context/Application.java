package com.epam.context;

import com.epam.context.impl.FilmRatingsContext;

public interface Application {
    static void start() {
        final ApplicationContext filmRatingsContext = new FilmRatingsContext();
        filmRatingsContext.init();
    }
}