package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConnectionPool {

    public static Queue<Connection> availableConnectionList = new ConcurrentLinkedQueue<>();
    public static List<Connection> notAvailableConnectionList = new ArrayList<>();

    public ConnectionPool() {
        init();
    }

    private ConnectionPool init() {

        String url = "jdbc:mysql://localhost:3306/final_jwd";
        String name = "Masha";
        String password = "root";
        Properties connectionProps = new Properties();
        connectionProps.put("user", name);
        connectionProps.put("password", password);

        try (Connection connection = new ConnectionProxy(DriverManager.getConnection(url, connectionProps))) {
            for (int i = 0; i < 10; i++) {
                this.availableConnectionList.add(connection);
            }
        } catch (SQLException e) {
            System.out.println("Aaaa");
        }
        return this;
    }

    public static Connection getConnection() {
        final Connection poll = availableConnectionList.poll();
        notAvailableConnectionList.add(poll);
        return poll;
    }

    public static void close(ConnectionProxy proxy) {
        notAvailableConnectionList.remove(proxy);
        availableConnectionList.add(proxy);
    }
}
