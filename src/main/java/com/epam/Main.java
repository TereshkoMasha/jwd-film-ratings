package com.epam;

import com.epam.dao.impl.UserDaoImpl;
import com.epam.db.ConnectionPool;
import com.epam.entity.User;
import com.epam.exception.DAOException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {


    public static void main(String[] args) throws SQLException, InterruptedException, DAOException {
        // Genre genre = Genre.SPORT;
        // System.out.println(Genre.resolveRoleById(24));
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ArrayList<User> users = (ArrayList<User>) UserDaoImpl.INSTANCE.findAll();

        final Connection connection = connectionPool.getConnection();
        //connectionPool.setAutoCommit(false);
        //хранимая процедура
        String sql = "SELECT * from movie where movie.publication_year > ?";
        String sqlInsert = "INSERT into movie (id, name, publication_year, duration, language_fk, country_fk) VALUES (?,?,?)";
//        try (PreparedStatement statement = connectionPool.prepareStatement(sql)) {
//
////            statement.setInt(1, 15);
////            statement.setString(2, "sokfskf");
////
////            System.out.println(statement.executeUpdate());
////            PreparedStatement statement1 = connectionPool.prepareStatement(sqlInsert);
////            statement.setInt(1, 35000);
////            int i = statement.executeUpdate();
////
////            final ResultSet resultSet = statement.executeQuery();
//            final ResultSet resultSet = statement.executeQuery("SELECT * from movie ");
//            //get<Type>(int pos);
//            //resultSet.getMetaData().getColumnCount();
//            while (resultSet.next()) {
//                System.out.println(resultSet.getInt(1));
//            }
    }

    //ConnectionPool pool = new ConnectionPool();
    //final ConnectionPool connectionPool = new ConnectionPool();
    //final Connection connection = ConnectionPool.getConnection();


    public static void executeQuery(String query) {

    }
}
