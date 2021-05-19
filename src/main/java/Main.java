import com.epam.db.ConnectionPool;
import com.epam.db.ConnectionProxy;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.getConnection();
    }
}
