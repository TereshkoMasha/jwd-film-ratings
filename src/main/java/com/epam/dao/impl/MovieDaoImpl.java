package com.epam.dao.impl;

import com.epam.dao.MovieDao;
import com.epam.db.ConnectionPool;
import com.epam.entity.Movie;
import com.epam.entity.enums.Genre;
import com.epam.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieDaoImpl extends AbstractDaoImpl<Movie> implements MovieDao {
    public static MovieDaoImpl INSTANCE = new MovieDaoImpl(ConnectionPool.getInstance());
    private static final Logger LOGGER = LogManager.getLogger(MovieDaoImpl.class);


    private static final String SQL_CREATE = "INSERT INTO movie (name, publication_year, duration, tagline, genre_id)"
            + "VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM movie WHERE id = ? ";

    private static final String SQL_FIND_ALL = "SELECT * FROM movie";
    private static final String SQL_FIND_BY_ID = "SELECT FROM movie WHERE id = ?";
    private static final String SQL_FIND_BY_NAME = "SELECT FROM movie WHERE name = ?";
    private static final String SQL_FIND_BY_PUBLICATION_YEAR = "SELECT FROM movie WHERE publication_year = ?";
    private static final String SQL_FIND_BY_GENRE = "SELECT FROM movie () WHERE id = ?";

    private static final String SQL_UPDATE_NAME = "UPDATE movie SET name = ? WHERE id = ?";


    protected MovieDaoImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    protected void prepareStatement(PreparedStatement preparedStatement, Movie entity) throws SQLException {
        preparedAllMovieStatements(preparedStatement, entity);
    }

    @Override
    protected Optional<Movie> parseResultSet(ResultSet resultSet) throws SQLException, DAOException {
        var movie = new Movie();
        movie.setId(resultSet.getInt("id"));
        movie.setName(resultSet.getString("name"));
        movie.setReleaseYear(resultSet.getInt("publication_year"));
        movie.setDuration(resultSet.getTime("duration"));
        movie.setTagline(resultSet.getString("tagline"));
        movie.setGenre(Genre.resolveRoleById(resultSet.getInt("genre_id")));
        return Optional.of(movie);
    }

    private void preparedAllMovieStatements(PreparedStatement preparedStatement, Movie movie) throws SQLException {
        preparedStatement.setInt(1, movie.getID());
        preparedStatement.setString(2, movie.getName());
        preparedStatement.setInt(3, movie.getReleaseYear());
        preparedStatement.setTime(4, movie.getDuration());
        preparedStatement.setString(5, movie.getTagline());
        preparedStatement.setInt(6, movie.getGenre().getId());
    }


    @Override
    protected String getUpdateSql() {
        return SQL_UPDATE_NAME;
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

    protected static String getFindByNameSql() {
        return SQL_FIND_BY_NAME;
    }

    protected static String getFindByGenreSql() {
        return SQL_FIND_BY_GENRE;
    }

    protected static String getFindByPublicationYearSql() {
        return SQL_FIND_BY_PUBLICATION_YEAR;
    }

    @Override
    public boolean update(Movie entity) {
        var updated = false;
        try (Connection connection = connectionPool.getConnection()) {
            try (var preparedStatement = connection.prepareStatement(getUpdateSql())) {
                preparedStatement.setString(1, entity.getName());
                preparedStatement.setInt(2, entity.getID());
                if (preparedStatement.executeUpdate() != 0) {
                    updated = true;
                }
            }
        } catch (SQLException | InterruptedException e) {
            LOGGER.error("Failed to update movie", new DAOException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return updated;
    }

    @Override
    public Optional<Movie> findByName(String name) throws DAOException {
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getFindByNameSql())) {
                statement.setString(1, name);
                var resultSet = statement.executeQuery();
                resultSet.next();
                return parseResultSet(resultSet);
            }
        } catch (SQLException | InterruptedException e) {
            LOGGER.error(new DAOException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return Optional.empty();
    }

    @Override
    public List<Movie> findByGenre(Genre genre) throws DAOException {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getFindByGenreSql())) {
                statement.setInt(1, genre.getId());
                var resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Optional<Movie> optionalUser = parseResultSet(resultSet);
                    optionalUser.ifPresent(movies::add);
                }
            }
        } catch (SQLException | InterruptedException e) {
            LOGGER.error(new DAOException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return movies;
    }

    @Override
    public List<Movie> findByPublicationYear(Integer year) throws DAOException {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getFindByPublicationYearSql())) {
                statement.setInt(1, year);
                var resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Optional<Movie> optionalUser = parseResultSet(resultSet);
                    optionalUser.ifPresent(movies::add);
                }
            }
        } catch (SQLException | InterruptedException e) {
            LOGGER.error(new DAOException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return movies;
    }
}
