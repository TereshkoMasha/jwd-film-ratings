package com.epam;

import com.epam.dao.impl.UserDaoImpl;
import com.epam.db.ConnectionPool;
import com.epam.entity.User;
import com.epam.entity.enums.Appraisal;
import com.epam.exception.DAOException;
import com.epam.service.impl.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        UserDaoImpl userDao = UserDaoImpl.INSTANCE;
        UserServiceImpl service = new UserServiceImpl();
        List<User> userList = service.findAll();
        userList.forEach(System.out::println);
        System.out.println(service.updateRating(Appraisal.HIGH, 2));
    }
}
