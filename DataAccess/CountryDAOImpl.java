
package DataAccess;

import static DataAccess.DatabaseConnection.conn;
import Model.Customer;
import static Model.User.userName;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 *
 * @author TheQcrew
 */
public class CountryDAOImpl {
    
    public static int newCountryId;
    public static String country, editCountry;
    
    public static int editCountryDB(Customer editCust) throws ClassNotFoundException, Exception {
        
        editCountry = editCust.getCountry();
        
        ResultSet result;
        Statement stmt = null;
        PreparedStatement ps = null;
        String query = "INSERT INTO country (country, createDate, createdBy, lastUpdate, lastUpdateBy) "
                            + "VALUES (?, ?, ?, ?, ?)";
            try {
                ps =DatabaseConnection.makeConnection().prepareStatement(query);

                ps = conn.prepareStatement(query);
                ps.setString(1, editCountry);
                ps.setString(2, ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime().toString());
                ps.setString(3, userName);
                ps.setString(4, ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime().toString());
                ps.setString(5, userName);
                ps.executeUpdate();
                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
            DatabaseConnection.makeConnection();
            stmt = conn.createStatement();
            result = stmt.executeQuery("SELECT * FROM country ORDER BY countryId DESC LIMIT 1;");
            result.next();
            newCountryId = result.getInt(1);
            return newCountryId;
        }
    
        public static int insertCountryDB(Customer addCust) throws ClassNotFoundException, Exception {
        
        country = addCust.getCountry();
            
        ResultSet result;
        Statement stmt = null;
        PreparedStatement ps = null;
        String query = "INSERT INTO country (country, createDate, createdBy, lastUpdate, lastUpdateBy) "
                            + "VALUES (?, ?, ?, ?, ?)";
            try {
                ps =DatabaseConnection.makeConnection().prepareStatement(query);

                ps = conn.prepareStatement(query);
                ps.setString(1, country);
                ps.setString(2, ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime().toString());
                ps.setString(3, userName);
                ps.setString(4, ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime().toString());
                ps.setString(5, userName);
                ps.executeUpdate();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
            DatabaseConnection.makeConnection();
            stmt = conn.createStatement();
            result = stmt.executeQuery("SELECT * FROM country ORDER BY countryId DESC LIMIT 1;");
            result.next();
            newCountryId = result.getInt(1);
            return newCountryId;
        }
}
