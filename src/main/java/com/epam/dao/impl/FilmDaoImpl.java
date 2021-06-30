package com.epam.dao.impl;

import com.epam.dao.FilmDao;
import com.epam.db.ConnectionPool;
import com.epam.entity.Country;
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

public class FilmDaoImpl extends AbstractDaoImpl<Movie> implements FilmDao {
    private static final Logger LOGGER = LogManager.getLogger(FilmDaoImpl.class);
    public static final FilmDaoImpl INSTANCE = new FilmDaoImpl(ConnectionPool.getInstance());

    private static final String SQL_CREATE = "INSERT INTO movie (name, publication_year, duration, country_id, tagline, genre_id, poster_path)"
            + "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM movie WHERE id = ? ";

    private static final String SQL_FIND_ALL = "SELECT * FROM movie join country c on c.id = movie.country_id ";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM movie join country c on c.id = movie.country_id WHERE movie.id = ?";
    private static final String SQL_FIND_BY_NAME = "SELECT * FROM movie join country c on c.id = movie.country_id  WHERE movie.name = ?";
    private static final String SQL_FIND_BY_COUNTRY = "SELECT * FROM movie join country c on c.id = movie.country_id  WHERE country_id = ?";
    private static final String SQL_FIND_BY_PUBLICATION_YEAR = "SELECT * FROM movie join country c on c.id = movie.country_id WHERE publication_year = ?";
    private static final String SQL_FIND_BY_GENRE = "SELECT * FROM movie join country c on c.id = movie.country_id  WHERE genre_id = ?";
    private static final String SQL_UPDATE_NAME = "UPDATE movie SET name = ? WHERE id = ?";


    protected FilmDaoImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    protected void prepareStatement(PreparedStatement preparedStatement, Movie entity) throws SQLException {
        preparedAllMovieStatements(preparedStatement, entity);
    }

    @Override
    protected Optional<Movie> parseResultSet(ResultSet resultSet) throws SQLException, DAOException {
        Country country = Country.builder().setName(resultSet.getString("c.name")).build();
        country.setId(resultSet.getInt("country_id"));
        Movie movie = Movie.builder()
                .setName(resultSet.getString("name"))
                .setDescription(resultSet.getString("description"))
                .setPoser(resultSet.getString("poster_path"))
                .setReleaseYear(resultSet.getInt("publication_year"))
                .setDuration(resultSet.getTime("duration"))
                .setReleaseCountry(country)
                .setTagline(resultSet.getString("tagline"))
                .setGenre(Genre.resolveGenreById(resultSet.getInt("genre_id"))).build();
        movie.setId(resultSet.getInt("id"));
        return Optional.of(movie);
    }

    private void preparedAllMovieStatements(PreparedStatement preparedStatement, Movie movie) throws SQLException {
        preparedStatement.setInt(1, movie.getId());
        preparedStatement.setString(2, movie.getName());
        preparedStatement.setInt(3, movie.getReleaseYear());
        preparedStatement.setTime(4, movie.getDuration());
        preparedStatement.setInt(5, movie.getCountry().getId());
        preparedStatement.setString(6, movie.getTagline());
        preparedStatement.setInt(7, movie.getGenre().getId());
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

    protected static String getFindByCountrySql() {
        return SQL_FIND_BY_COUNTRY;
    }

    @Override
    public boolean update(Movie entity) {
        boolean updated = false;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getUpdateSql())) {
                preparedStatement.setString(1, entity.getName());
                preparedStatement.setInt(2, entity.getId());
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
        Optional<Movie> entityOptional = Optional.empty();
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getFindByNameSql())) {
                statement.setString(1, name);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    entityOptional = parseResultSet(resultSet);
                }
            }
        } catch (SQLException | InterruptedException e) {
            LOGGER.error(new DAOException(e));
            Thread.currentThread().interrupt();
        }
        return entityOptional;
    }

    @Override
    public List<Movie> findAllByGenre(Genre genre) throws DAOException {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getFindByGenreSql())) {
                statement.setInt(1, genre.getId());
                ResultSet resultSet = statement.executeQuery();
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
    public List<Movie> findAllByPublicationYear(Integer year) throws DAOException {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getFindByPublicationYearSql())) {
                statement.setInt(1, year);
                ResultSet resultSet = statement.executeQuery();
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
    public List<Movie> findAllByCountry(Country country) throws DAOException {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getFindByCountrySql())) {
                statement.setInt(1, country.getId());
                ResultSet resultSet = statement.executeQuery();
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
