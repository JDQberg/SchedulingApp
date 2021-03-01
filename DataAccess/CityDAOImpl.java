
package DataAccess;

import static DataAccess.DatabaseConnection.conn;
import static DataAccess.CountryDAOImpl.newCountryId;
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
public class CityDAOImpl {
    
    public static int newCityId;
    public static String city, editCity;
    
    public static int editCityDB(Customer editCust) throws ClassNotFoundException, Exception {
        
        editCity = editCust.getCity();
        
        ResultSet result;
        Statement stmt = null;
        PreparedStatement ps = null;
        String query = "INSERT INTO city (city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy) "
                            + "VALUES (?, ?, ?, ?, ?, ?)";       
        try {
            ps =DatabaseConnection.makeConnection().prepareStatement(query);

            ps = conn.prepareStatement(query);
            ps.setString(1, editCity);
            ps.setInt(2, newCountryId);
            ps.setString(3, ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime().toString());
            ps.setString(4, userName);
            ps.setString(5, ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime().toString());
            ps.setString(6, userName);
            ps.executeUpdate();
            DatabaseConnection.closeConnection();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        DatabaseConnection.makeConnection();
        stmt = conn.createStatement();
        result = stmt.executeQuery("SELECT * FROM city ORDER BY cityId DESC LIMIT 1;");
        result.next();
        newCityId = result.getInt(1);
        return newCityId;            
    }
    
    public static int insertCityDB(Customer addCust) throws ClassNotFoundException, Exception {
        
        city = addCust.getCity();
        
        ResultSet result;
        Statement stmt = null;
        PreparedStatement ps = null;
        String query = "INSERT INTO city (city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy) "
                            + "VALUES (?, ?, ?, ?, ?, ?)";        
        try {
            ps =DatabaseConnection.makeConnection().prepareStatement(query);

            ps = conn.prepareStatement(query);
            ps.setString(1, city);
            ps.setInt(2, newCountryId);
            ps.setString(3, ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime().toString());
            ps.setString(4, userName);
            ps.setString(5, ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime().toString());
            ps.setString(6, userName);
            ps.executeUpdate();
            DatabaseConnection.closeConnection();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        DatabaseConnection.makeConnection();
        stmt = conn.createStatement();
        result = stmt.executeQuery("SELECT * FROM city ORDER BY cityId DESC LIMIT 1;");
        result.next();
        newCityId = result.getInt(1);
        return newCityId;            
    }    
}