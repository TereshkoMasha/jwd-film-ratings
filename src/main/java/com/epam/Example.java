package com.epam;

import com.epam.db.DatabaseConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class Example {
    static final Logger logger = LogManager.getLogger(Example.class);

    static final String url = DatabaseConfiguration.getInstance().getUrl();
    static final String username = DatabaseConfiguration.getInstance().getUser();
    static final String password = DatabaseConfiguration.getInstance().getPassword();

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            try (Statement statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY)) {
                /**
                 *  executeQuery() for SELECT
                 *  executeUpdate()
                 *  execute() for both, when result is unknown
                 */
                String insertGenre = "INSERT INTO genre(genre)" + " VALUE ('comedy')";
                statement.execute(insertGenre);

                String selectSql = "SELECT FROM genre";
                ResultSet resultSet = statement.executeQuery(selectSql);
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("genre");
                    System.out.println(id + "\t" + name);
                }
                System.out.println(resultSet.getMetaData().getColumnCount());

            } catch (SQLException e) {
                logger.error(e.getMessage());
            }

            /**
             * PreparedStatement objects contain precompiled SQL sequences.
             * They can have one or more parameters denoted by a question mark
             *
             *  Один и тот же подготовленный запрос можно использовать несколько раз для разных данных, тем самым сокращая код
             *  Запросы со связываемыми переменными обладают готовой встроенной защитой от SQL-инъекций
             *  (данные передаются отдельно от запроса и никак не могут его модифицировать)
             */
            String updatePositionSql = "UPDATE genre SET genre =? WHERE id =?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updatePositionSql)) {
                preparedStatement.setString(1, "drama");
                preparedStatement.setInt(2, 1);
                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println(rowsAffected);
            }


        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
}
