package com.epam.dao.impl;

import com.epam.dao.GenreDao;
import com.epam.db.ConnectionPool;
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

public class GenreDaoImpl implements GenreDao {
    private static final Logger LOGGER = LogManager.getLogger(GenreDaoImpl.class);
    private static final String SQL_FIND_ALL = "SELECT * FROM genre ";
    public static final GenreDao INSTANCE = new GenreDaoImpl(ConnectionPool.getInstance());
    protected final ConnectionPool connectionPool;

    protected GenreDaoImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    protected String getFindAllSql() {
        return SQL_FIND_ALL;
    }

    protected Optional<Genre> parseResultSet(ResultSet resultSet) throws SQLException {
        return Optional.of(Genre.resolveGenreById(resultSet.getInt("id")));
    }

    @Override
    public List<Genre> findAll() throws DAOException {
        List<Genre> entitiesList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(getFindAllSql())) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Optional<Genre> entityOptional = parseResultSet(resultSet);
                    entityOptional.ifPresent(entitiesList::add);
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | InterruptedException e) {
            LOGGER.error(new DAOException(e));
            Thread.currentThread().interrupt();
        }
        return entitiesList;
    }
}
