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

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static ConnectionPool instance;
    private static final ReentrantLock lock = new ReentrantLock();
    private static final AtomicBoolean INSTANCE_INITIALIZE = new AtomicBoolean(false);
    private static final BlockingQueue<ConnectionProxy> availableConnections = new LinkedBlockingQueue<>(DatabaseConfiguration.getInstance().getMaxPoolSize());
    private static final Queue<Connection> usedConnections = new LinkedBlockingQueue<>();

    private ConnectionPool() {
    }

    public static ConnectionPool getInstance() {
        if (!INSTANCE_INITIALIZE.get()) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                    initialize();
                    INSTANCE_INITIALIZE.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private static void initialize() {
        for (var i = 0; i < DatabaseConfiguration.getInstance().getInitPoolSize(); i++) {
            createConnection();
        }
        LOGGER.info("Connection pool successful initialization");
    }

    private static void createConnection() {
        try (var connection = new ConnectionProxy(
                DriverManager.getConnection(DatabaseConfiguration.getInstance().getUrl()
                        , DatabaseConfiguration.getInstance().getUser(),
                        DatabaseConfiguration.getInstance().getPassword()))) {
            availableConnections.offer(connection);
        } catch (SQLException e) {
            LOGGER.error("SQL Exception during the connection creating: check database||properties settings", new RuntimeException(e));
        }
    }

    public ConnectionProxy getConnection() throws InterruptedException {
        ConnectionProxy connection = null;
        try {
            connection = availableConnections.take();
            usedConnections.offer(connection);
        } catch (InterruptedException e) {
            LOGGER.error("Unable to get connection from connection pool", e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }


    public void releaseConnection(ConnectionProxy proxy) {
        if (availableConnections.offer(proxy)) {
            usedConnections.remove(proxy);
        }
    }

    public void closePool() {
        for (var i = 0; i < availableConnections.size(); i++) {
            try {
                availableConnections.take().closeConnection();
            } catch (SQLException | InterruptedException e) {
                LOGGER.error("Connection pool close error", e);
                Thread.currentThread().interrupt();
            }
        }
    }
}
