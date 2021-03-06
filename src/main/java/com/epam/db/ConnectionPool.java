package com.epam.db;

import com.epam.util.DatabaseProperties;
import com.epam.util.PropertyReaderUtil;
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
 * {@link ConnectionPool} A thread safe class for connecting to a database.
 * Contains a  {@link BlockingQueue} for storing available connections.
 * Initially, {@link BlockingQueue} contains the initial number of connections, does not exceed the maximum.
 * Contains a {@link Queue} for storing used connections
 * The maximum number of connections does not exceed the value declared in the properties file
 */
public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static ConnectionPool instance;
    private static final DatabaseProperties properties = PropertyReaderUtil.getInstance().getDatabaseProperties();

    private static final ReentrantLock lock = new ReentrantLock();
    private static final AtomicBoolean instance_initialize = new AtomicBoolean(false);

    private static final BlockingQueue<Connection> availableConnections = new LinkedBlockingQueue<>(properties.getMaxPoolSize());
    private static final Queue<Connection> usedConnections = new LinkedBlockingQueue<>();

    private ConnectionPool() throws SQLException {
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
    }


    public static ConnectionPool getInstance() {
        if (!instance_initialize.get()) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                    initialize();
                    instance_initialize.set(true);
                }
            } catch (SQLException e) {
                logger.error(e.getStackTrace());
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private static void initialize() {
        for (int i = 0; i < properties.getInitPoolSize(); i++) {
            createConnection();
        }
        logger.info("Connection pool successful initialization");
    }

    /**
     * Create {@link ConnectionProxy}
     */
    private static void createConnection() {
        try {
            Connection connection = new ConnectionProxy(DriverManager.getConnection(properties.getUrl(), properties.getUser(), properties.getPassword()));
            availableConnections.add(connection);
        } catch (SQLException e) {
            logger.error("SQL Exception during the connection creating: check database||properties settings", e);
        }
    }

    /**
     * If the {@link BlockingQueue} is empty and
     * the number of created connections does not exceed the maximum, a new connection is created.
     * Takes a {@link Connection}  from the queue if it is not empty.
     * If necessary, waiting for a connection to appear in the queue
     *
     * @return {@link Connection} from the {@link BlockingQueue}
     */
    public Connection getConnection() throws InterruptedException {
        Connection connection = null;
        try {
            if (availableConnections.isEmpty() && usedConnections.size() < properties.getMaxPoolSize()) {
                createConnection();
            }
            connection = availableConnections.take();
            usedConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.error("Unable to get connection from connection pool", e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    /**
     * Returns the connection to {@link ConnectionPool}
     */
    public void releaseConnection(Connection proxy) {
        if (availableConnections.offer(proxy)) {
            usedConnections.remove(proxy);
        }
    }

    public void closePool() {
        for (int i = 0; i < availableConnections.size(); i++) {
            try {
                availableConnections.take().close();
            } catch (SQLException | InterruptedException e) {
                logger.error("Unable to close connection pool", e);
                Thread.currentThread().interrupt();
            }
        }
    }

}
