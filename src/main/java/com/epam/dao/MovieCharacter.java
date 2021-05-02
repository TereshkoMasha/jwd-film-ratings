package com.epam.dao;

import com.epam.exception.DAOException;

import java.util.List;

public interface MovieCharacter {

    List<MovieCharacter> getCharacterByMovieID(int id) throws DAOException;

    void deleteCharacterFromMovie(int movieId, int characterId);
}
