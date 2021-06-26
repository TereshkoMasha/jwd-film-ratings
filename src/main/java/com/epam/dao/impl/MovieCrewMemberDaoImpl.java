package com.epam.dao.impl;

import com.epam.dao.MovieCrewMemberDao;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieCrewMemberDaoImpl extends AbstractDaoImpl<MovieCrewMember> implements MovieCrewMemberDao {
    private static final Logger LOGGER = LogManager.getLogger(MovieCrewMemberDaoImpl.class);
    public static final MovieCrewMemberDaoImpl INSTANCE = new MovieCrewMemberDaoImpl(ConnectionPool.getInstance());
    private static final String SQL_UPDATE_CHARACTER_NAME = "UPDATE movie_character SET name = ? WHERE id = ? ";
    private static final String SQL_CREATE = "INSERT INTO movie_character (role, name)" + "VALUES (?, ?)";
    private static final String SQL_FIND_ALL = "SELECT * FROM movie_cast join movie_character mc on mc.id = movie_cast.character_id";
    private static final String SQL_FIND_ALL_ACTORS = "SELECT * FROM movie_cast join movie_character mc on mc.id = movie_cast.character_id WHERE mov_id = ? AND  role = 2";
    private static final String SQL_FIND_MOVIE_DIRECTOR = "SELECT * FROM movie_cast join movie_character mc on mc.id = movie_cast.character_id WHERE mov_id = ? AND role = 1";
    private static final String SQL_FIND_BY_MOVIE_CREW_ID = "SELECT * FROM movie_cast join movie_character mc on mc.id = movie_cast.character_id WHERE mc.id = ?";
    private static final String SQL_DELETE = "DELETE FROM movie_cast WHERE movie_id = ? AND character_id = ? ";


    protected MovieCrewMemberDaoImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    protected String getUpdateSql() {
        return SQL_UPDATE_CHARACTER_NAME;
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
        return SQL_FIND_BY_MOVIE_CREW_ID;
    }

    @Override
    protected String getLastInsertIdSql() {
        return null;
    }

    public static String getFindAllMovieActorsSql() {
        return SQL_FIND_ALL_ACTORS;
    }

    public static String getFindMovieDirectorSql() {
        return SQL_FIND_MOVIE_DIRECTOR;
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
                preparedStatement.setInt(2, entity.getId());
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


    @Override
    public List<MovieCrewMember> findAllActorsByMovieId(Integer id) throws DAOException {
        List<MovieCrewMember> entitiesList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getFindAllMovieActorsSql())) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Optional<MovieCrewMember> entityOptional = parseResultSet(resultSet);
                    entityOptional.ifPresent(entitiesList::add);
                }
            }
        } catch (SQLException | InterruptedException e) {
            LOGGER.error(new DAOException(e));
            Thread.currentThread().interrupt();
        }
        return entitiesList;
    }

    @Override
    public MovieCrewMember findDirectorByMovieId(Integer id) throws DAOException {
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getFindMovieDirectorSql())) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Optional<MovieCrewMember> entityOptional = parseResultSet(resultSet);
                    if (entityOptional.isPresent()) {
                        return entityOptional.get();
                    }
                }
            }
        } catch (SQLException | InterruptedException e) {
            LOGGER.error(new DAOException(e));
            Thread.currentThread().interrupt();
        }
        return MovieCrewMember.builder().build();
    }


}
