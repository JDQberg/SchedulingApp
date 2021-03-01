
package DataAccess;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author TheQcrew
 */
public class DatabaseConnection {
    
    private static final String DATABASENAME = "U05MEa";
    private static final String DB_URL = "jdbc:mysql://wgudb.ucertify.com/" + DATABASENAME;
    private static final String USERNAME = "U05MEa";
    private static final String PASSWORD = "53688542347";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    public static Connection conn;
    
    public static Connection makeConnection() throws ClassNotFoundException, SQLException {
        
        Class.forName(DRIVER);
        conn = (Connection) DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        return conn;
    }
    
    public static void closeConnection()throws ClassNotFoundException, SQLException, Exception {
        
        conn.close();
    }
}
