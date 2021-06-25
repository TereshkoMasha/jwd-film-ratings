package com.epam.service.impl;

import com.epam.dao.GenreDao;
import com.epam.dao.impl.GenreDaoImpl;
import com.epam.entity.enums.Genre;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.service.GenreService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class GenreServiceImpl implements GenreService {
    private static final Logger LOGGER = LogManager.getLogger(GenreService.class);
    private static final GenreDao genreDao = GenreDaoImpl.INSTANCE;

    public List<Genre> findAll() throws ServiceException {
        List<Genre> genres;
        try {
            genres = genreDao.findAll();
            if (!genres.isEmpty()) {
                Collections.sort(genres);
                return genres;
            }
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        return genres;
    }
}
