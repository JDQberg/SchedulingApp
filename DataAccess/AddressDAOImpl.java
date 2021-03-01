
package DataAccess;

import static DataAccess.DatabaseConnection.conn;
import static DataAccess.CityDAOImpl.newCityId;
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
public class AddressDAOImpl {
    
    public static int newAddressId;
    public static String address, address2, postalCode, phone;
    public static String editAddress, editAddress2, editPostalCode, editPhone;
    
    public static int editAddressDB(Customer editCust) throws ClassNotFoundException, Exception {
        
        editAddress = editCust.getAddress();
        editAddress2 = editCust.getAddress2();
        editPostalCode = editCust.getPostalCode();
        editPhone = editCust.getPhone();
        
        ResultSet result;
        Statement stmt = null;
        PreparedStatement ps = null;
        String query = "INSERT INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";       
            try {                
                ps =DatabaseConnection.makeConnection().prepareStatement(query);

                ps = conn.prepareStatement(query);
                ps.setString(1, editAddress);
                ps.setString(2, editAddress2);
                ps.setInt(3, newCityId); //statement for retrieving cityId
                ps.setString(4, editPostalCode);
                ps.setString(5, editPhone);
                ps.setString(6, ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime().toString());
                ps.setString(7, userName);
                ps.setString(8, ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime().toString());
                ps.setString(9, userName);
                ps.executeUpdate();
                DatabaseConnection.closeConnection();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
            DatabaseConnection.makeConnection();
            stmt = conn.createStatement();
            result = stmt.executeQuery("SELECT * FROM address ORDER BY addressId DESC LIMIT 1;");
            result.next();
            newAddressId = result.getInt(1);
            return newAddressId;
            
    }
    
    public static int insertAddressDB(Customer addCust) throws ClassNotFoundException, Exception {
        
        address = addCust.getAddress();
        address2 = addCust.getAddress2();
        postalCode = addCust.getPostalCode();
        phone = addCust.getPhone();
        
        ResultSet result;
        Statement stmt = null;
        PreparedStatement ps = null;
        String query = "INSERT INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";        
            try {                
                ps =DatabaseConnection.makeConnection().prepareStatement(query);

                ps = conn.prepareStatement(query);
                ps.setString(1, address);
                ps.setString(2, address2);
                ps.setInt(3, newCityId); //statement for retrieving cityId
                ps.setString(4, postalCode);
                ps.setString(5, phone);
                ps.setString(6, ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime().toString());
                ps.setString(7, userName);
                ps.setString(8, ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime().toString());
                ps.setString(9, userName);
                ps.executeUpdate();
                DatabaseConnection.closeConnection();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
            DatabaseConnection.makeConnection();
            stmt = conn.createStatement();
            result = stmt.executeQuery("SELECT * FROM address ORDER BY addressId DESC LIMIT 1;");
            result.next();
            newAddressId = result.getInt(1);
            return newAddressId;
    }    
}
