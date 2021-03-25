import db.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        final ConnectionPool connectionPool = new ConnectionPool();
        final Connection connection = ConnectionPool.getConnection();
    }

}
