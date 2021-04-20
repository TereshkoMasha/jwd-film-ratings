package com.epam.dao.impl;

import com.epam.dao.AbstractController;
import com.epam.db.ConnectionPool;
import com.epam.entity.Genre;
import com.epam.entity.Movie;
import com.epam.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MovieDaoImpl extends AbstractController<Movie, Integer> {
    private static final String SQL_CREATE = "INSERT INTO movie (name, password, login, role_id, rating)" +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_FIND_ALL = "SELECT FROM movie(id, name, login, password, role_id, rating)";
    private static final String SQL_UPDATE_LOGIN = "UPDATE movie SET login = ? WHERE password = ? ";
    private static final String SQL_DELETE = "DELETE FROM movie WHERE id = ? ";
    private static final String SQL_FIND_BY_ID = "SELECT FROM movie (name, password, login, role_id, rating) WHERE id = ?";

    protected MovieDaoImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    protected void prepareCreateStatement(PreparedStatement preparedStatement, Movie entity) throws SQLException {

    }

    @Override
    protected void prepareUpdateStatement(PreparedStatement preparedStatement, Movie entity) throws SQLException {

    }

    @Override
    protected Optional<Movie> parseResultSet(ResultSet resultSet) throws SQLException, DAOException {
        Movie movie = new Movie();
        movie.setId(resultSet.getInt("id"));
        movie.setName(resultSet.getString("name"));
        movie.setReleaseYear(resultSet.getInt("publication_year"));
        movie.setDuration(resultSet.getTime("duration"));
        movie.setTagline(resultSet.getString("tagline"));
        // movie.setReleaseCountry(resultSet.getString("country_id"));
        movie.setGenre(Genre.resolveRoleById(resultSet.getInt("genre_id")));

        return Optional.of(movie);
    }

    @Override
    protected String getUpdateSql() {
        return SQL_UPDATE_LOGIN;
    }

    @Override
    protected String getFindAllSql() {
        return SQL_FIND_ALL;
    }

    @Override
    protected String getCreateSql() {
        return SQL_CREATE;
    }

    @Override
    protected String getDeleteSql() {
        return SQL_DELETE;
    }

    @Override
    protected String getFindByIdSql() {
        return SQL_FIND_BY_ID;
    }

}
