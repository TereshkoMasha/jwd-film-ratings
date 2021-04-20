package com.epam.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1.Открытие соединения к БД и отправка запроса серверу.
 * 2.Сервер парсит SQL запрос.
 * 3.Сервер оптимизирует запрос исходя из правил, а так же из статистики по таблицам. В результате строится план выполнения запроса.
 * 4.Сервер выполняет запрос в соответствии с ранее построенном планом и отправляет результаты пользователю.
 */

public class ConnectionPool {

    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static final ReentrantLock lock = new ReentrantLock();

    private final static AtomicBoolean instanceInitialize = new AtomicBoolean(false);
    private static ConnectionPool instance;
    private static final int maxPoolSize = DatabaseConfiguration.getInstance().getMaxPoolSize();


    private static final BlockingQueue<ConnectionProxy> availableConnections = new LinkedBlockingQueue<>(maxPoolSize);
    private static final Queue<Connection> usedConnections = new LinkedBlockingQueue<>(maxPoolSize);

    public static ConnectionPool getInstance() {
        if (!instanceInitialize.get()) {
            lock.lock();
            try {
                if (instanceInitialize.compareAndSet(false, true)) {
                    instance = new ConnectionPool();
                    initialize();
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private ConnectionPool() {
    }

    private static void initialize() {

        for (int i = 0; i < DatabaseConfiguration.getInstance().getInitPoolSize(); i++) {
            availableConnections.add(createConnection());
        }
        logger.info("Connection pool successful initialization");
    }

    private static ConnectionProxy createConnection() {

        try (Connection connection = new ConnectionProxy(
                DriverManager.getConnection(DatabaseConfiguration.getInstance().getUrl()
                        , DatabaseConfiguration.getInstance().getUser(),
                        DatabaseConfiguration.getInstance().getPassword()))) {
            logger.info("New connection successfully added to database");
            return (ConnectionProxy) connection;
        } catch (SQLException e) {
            logger.error("SQL Exception during the connection creating: check database||properties settings", e);
            throw new RuntimeException("SQL Exception during the connection creating: check database||properties settings", e);
        }
    }

    public Connection getConnection() throws InterruptedException {
        ConnectionProxy connection = null;
        try {
            if (availableConnections.isEmpty()) {
                if (usedConnections.size() != DatabaseConfiguration.getInstance().getMaxPoolSize()) {
                    connection = createConnection();
                    return new ConnectionProxy(connection);
                }
            }
            connection = availableConnections.take();
            usedConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.error("Unable to get connection", e);
        }
        return connection;
    }


    public void releaseConnection(Connection proxy) {
        if (proxy instanceof ConnectionProxy) {
            availableConnections.offer((ConnectionProxy) proxy);
            usedConnections.remove(proxy);
        }
    }

    public void closePool() {
        for (int i = 0; i < availableConnections.size(); i++) {
            try {
                availableConnections.take().closeConnection();
            } catch (SQLException | InterruptedException e) {
                logger.error("Connection pool close error", e);
            }
        }
    }
}
