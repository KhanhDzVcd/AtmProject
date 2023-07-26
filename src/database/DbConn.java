package database;
import java.sql.*;
public class DbConn {
    private static DbConn instance;
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/atm";
    private static final String user_name = "postgres";
    private static final String password = "admin@123";
    private Connection connection;

    private DbConn() {
        // Khởi tạo kết nối cơ sở dữ liệu ở đây
        try {
            connection = DriverManager.getConnection(DB_URL, user_name, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized DbConn getInstance() {
        if (instance == null) {
            instance = new DbConn();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeDB() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

