package com.epam.service;

import com.epam.entity.enums.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findAll();
}
