package com.epam.dao.impl;

import com.epam.dao.ReviewDao;
import com.epam.db.ConnectionPool;
import com.epam.entity.Review;
import com.epam.entity.enums.Appraisal;
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

public class ReviewDaoImpl extends AbstractDaoImpl<Review> implements ReviewDao {
    private static final Logger LOGGER = LogManager.getLogger(ReviewDaoImpl.class);
    public static final ReviewDaoImpl INSTANCE = new ReviewDaoImpl(ConnectionPool.getInstance());

    private static final String SQL_CREATE = "INSERT INTO movie_review ( movie_id, user_id, rating, text)" + "VALUES (?, ?, ?, ?)";
    private static final String SQL_FIND_ALL = "SELECT * FROM movie_review";
    private static final String SQL_FIND_ALL_BY_MOVIE_ID = "SELECT * FROM movie_review WHERE movie_id = ?";
    private static final String SQL_FIND_ALL_BY_USER_ID = "SELECT * FROM movie_review WHERE user_id = ?";
    private static final String SQL_FIND_BY_MOVIE_ID_USER_ID = "SELECT * FROM movie_review WHERE movie_id = ? AND user_id = ?";
    private static final String SQL_DELETE = "DELETE FROM movie_review WHERE movie_id = ? AND user_id = ? ";
    private static final String SQL_UPDATE = "UPDATE movie_review SET text = ? WHERE movie_id = ? user_id = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM movie_review WHERE user_id = ? ";
    private static final String SQL_FIND_AVERAGE = "SELECT AVG(rating) FROM movie_review WHERE movie_id = ?";


    protected ReviewDaoImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    protected void prepareStatement(PreparedStatement preparedStatement, Review entity) throws SQLException {
        preparedStatement.setInt(1, entity.getMovieID());
        preparedStatement.setInt(2, entity.getUserID());
        preparedStatement.setInt(3, entity.getRating().getId());
        preparedStatement.setString(4, entity.getText());
    }

    @Override
    protected Optional<Review> parseResultSet(ResultSet resultSet) throws SQLException, DAOException {
        Review review = Review.builder().setMovieID(resultSet.getInt("movie_id"))
                .setUserID(resultSet.getInt("user_id"))
                .setRating(Appraisal.resolveGenreById(resultSet.getInt("rating")))
                .setText(resultSet.getString("text"))
                .build();
        return Optional.of(review);
    }

    @Override
    protected String getUpdateSql() {
        return SQL_UPDATE;
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

    protected String getFindAllByMovieIdSql() {
        return SQL_FIND_ALL_BY_MOVIE_ID;
    }

    protected String getFindAllByUserIdSql() {
        return SQL_FIND_ALL_BY_USER_ID;
    }

    protected String getFindByMovieIdUserIdSql() {
        return SQL_FIND_BY_MOVIE_ID_USER_ID;
    }

    public static String getFindAverageSql() {
        return SQL_FIND_AVERAGE;
    }

    @Override
    public boolean update(Review entity) throws DAOException {
        boolean updated = false;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getUpdateSql())) {
                connection.setAutoCommit(false);
                preparedStatement.setString(1, entity.getText());
                preparedStatement.setInt(2, entity.getMovieID());
                preparedStatement.setInt(3, entity.getUserID());
                if (preparedStatement.executeUpdate() != 0) {
                    updated = true;
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | InterruptedException e) {
            LOGGER.error("Failed to update entity", new DAOException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return updated;
    }


    @Override
    public List<Review> findAllByMovieId(Integer movieId) throws DAOException {
        List<Review> reviewList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getFindAllByMovieIdSql())) {
                connection.setAutoCommit(false);
                statement.setInt(1, movieId);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Optional<Review> optionalReview = parseResultSet(resultSet);
                    optionalReview.ifPresent(reviewList::add);
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | InterruptedException e) {
            LOGGER.error(new DAOException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return reviewList;
    }

    @Override
    public List<Review> findAllByUserId(Integer userId) throws DAOException {
        List<Review> reviewList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getFindAllByUserIdSql())) {
                connection.setAutoCommit(false);
                statement.setInt(1, userId);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Optional<Review> optionalReview = parseResultSet(resultSet);
                    optionalReview.ifPresent(reviewList::add);
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | InterruptedException e) {
            LOGGER.error(new DAOException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return reviewList;
    }

    @Override
    public Optional<Review> findByMovieIdUserId(Integer movieId, Integer userId) throws DAOException {
        Optional<Review> optionalReview = Optional.empty();
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(getFindByMovieIdUserIdSql())) {
                statement.setInt(1, movieId);
                statement.setInt(2, userId);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    optionalReview = parseResultSet(resultSet);
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | InterruptedException e) {
            LOGGER.error(new DAOException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return optionalReview;
    }

    @Override
    public Double getAverageRating(Integer movieId) throws DAOException {
        double averageAppraisal = 0.0;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getFindAverageSql())) {
                connection.setAutoCommit(false);
                statement.setInt(1, movieId);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    averageAppraisal = resultSet.getDouble("AVG(rating)");
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | InterruptedException e) {
            LOGGER.error(new DAOException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return averageAppraisal;
    }

    @Override
    public boolean deleteByMovieIdUserId(Integer movieId, Integer userID) throws DAOException {
        boolean state = false;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getDeleteSql())) {
                connection.setAutoCommit(false);
                preparedStatement.setInt(1, movieId);
                preparedStatement.setInt(2, userID);
                if (!preparedStatement.execute()) {
                    state = true;
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | InterruptedException e) {
            LOGGER.error("Failed to delete entity", new DAOException(e));
            Thread.currentThread().interrupt();
        }
        return state;
    }

}
