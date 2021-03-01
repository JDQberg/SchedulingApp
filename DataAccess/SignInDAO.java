
package DataAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 *
 * @author TheQcrew
 */
public class SignInDAO {
    
    static ResourceBundle rb;
    static ResultSet result;
    
    public static ResultSet checkUserDB(String un, String pw){
        
        PreparedStatement ps;
        String query = "SELECT * FROM user WHERE userName =? && password =?";
        
        DatabaseConnection conn = new DatabaseConnection();

        try {
            ps = conn.makeConnection().prepareStatement(query);
            
            ps.setString(1, un);
            ps.setString(2, pw);
            
            result = ps.executeQuery();
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex.toString());
        }
        
        return result;  
    }
}
