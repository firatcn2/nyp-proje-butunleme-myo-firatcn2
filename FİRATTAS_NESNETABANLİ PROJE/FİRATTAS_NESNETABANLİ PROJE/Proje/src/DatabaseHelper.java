import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/cezaevi_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("Veritabanı bağlantı hatası: " + e.getMessage());
        }
        return connection;
    }
}