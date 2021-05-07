package com.epam.dao.impl;

import com.epam.dao.MovieCharacterDao;
import com.epam.db.ConnectionPool;
import com.epam.entity.MovieCrewMember;
import com.epam.entity.enums.MovieCrewRole;
import com.epam.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MovieCharacterDaoImpl extends AbstractDaoImpl<MovieCrewMember> implements MovieCharacterDao {
    private static final Logger LOGGER = LogManager.getLogger(MovieCharacterDaoImpl.class);
    public static final MovieCharacterDaoImpl INSTANCE = new MovieCharacterDaoImpl(ConnectionPool.getInstance());
    private static final String SQL_UPDATE_CHARACTER_NAME = "UPDATE movie_character SET name = ? WHERE id = ? ";
    private static final String SQL_UPDATE_CHARACTER_ROLE = "UPDATE movie_character SET role = ? WHERE id = ? ";
    private static final String SQL_CREATE = "INSERT INTO movie_character (role, name)" +
            "VALUES (?, ?)";
    private static final String SQL_FIND_ALL_BY_MOVIE = "SELECT * FROM movie_cast WHERE mov_id = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM movie_character WHERE character_id = ?";
    private static final String SQL_DELETE = "DELETE FROM movie_cast WHERE movie_id = ? AND character_id = ? ";


    protected MovieCharacterDaoImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    protected String getUpdateSql() {
        return SQL_UPDATE_CHARACTER_NAME;
    }

    @Override
    protected String getFindAllSql() {
        return SQL_FIND_ALL_BY_MOVIE;
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

    protected static String getUpdateCharacterRoleSql() {
        return SQL_UPDATE_CHARACTER_ROLE;
    }

    @Override
    protected void prepareStatement(PreparedStatement preparedStatement, MovieCrewMember entity) throws SQLException {
        prepareAllMovieCharacterStatements(preparedStatement, entity);
    }

    @Override
    protected Optional<MovieCrewMember> parseResultSet(ResultSet resultSet) throws SQLException, DAOException {
        MovieCrewMember movieCrewMember = MovieCrewMember.builder()
                .setName(resultSet.getString("name"))
                .setRole(MovieCrewRole.resolveRoleById(resultSet.getInt("role")))
                .build();
        movieCrewMember.setId(resultSet.getInt("id"));
        return Optional.of(movieCrewMember);
    }

    private void prepareAllMovieCharacterStatements(PreparedStatement preparedStatement, MovieCrewMember movieCrewMember) throws SQLException {
        preparedStatement.setString(1, movieCrewMember.getName());
        preparedStatement.setInt(2, movieCrewMember.getRole().getId());
    }


    @Override
    public boolean update(MovieCrewMember entity) {
        boolean updated = false;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getUpdateSql())) {
                preparedStatement.setString(1, entity.getName());
                preparedStatement.setInt(2, entity.getID());
                if (preparedStatement.executeUpdate() != 0) {
                    updated = true;
                }
            }
        } catch (SQLException | InterruptedException e) {
            LOGGER.error("Failed to update entity", new DAOException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return updated;
    }

}
