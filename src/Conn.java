import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn{

    // Database URL, Username, and Password
    private static final String URL = "jdbc:mysql://localhost:3306/vsicsmca"; // Replace with your DB name
    private static final String USER = "root"; // Replace with your MySQL username
    private static final String PASSWORD = "987654321"; // Replace with your MySQL password

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Load the MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver"); // This step can be omitted with newer JDBC versions
            
            // Create the connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // If connection is successful
            System.out.println("Connected to the database successfully.");
        } catch (ClassNotFoundException e) {
            // If the MySQL JDBC driver is not found
            System.err.println("MySQL JDBC Driver not found. Include it in your library path.");
            e.printStackTrace();
        } catch (SQLException e) {
            // If there is an error with SQL or the connection fails
            System.err.println("Connection failed. Check output console for more details.");
            e.printStackTrace();
        }

        return connection;
    }

    public static void main(String[] args) {
        // Test the connection
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.close(); // Close connection when done
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
