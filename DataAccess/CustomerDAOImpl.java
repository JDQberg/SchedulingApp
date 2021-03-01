
package DataAccess;

import static DataAccess.DatabaseConnection.conn;
import static DataAccess.AddressDAOImpl.newAddressId;
import Model.Customer;
import static Model.Customer.custIdToDelete;
import static Model.User.userName;
import static View_Controller.AddCustomerController.active;
import static View_Controller.EditCustomerController.editActive;
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
public class CustomerDAOImpl {
    
    public static String customerName;
    public static int editCustomerId;
    
    public static void deleteCustomerDB() throws ClassNotFoundException, Exception {
        
        PreparedStatement ps = null;
        String query = "DELETE FROM customer WHERE customerId=(?)";

            try {
                ps = conn.prepareStatement(query);
                ps.setInt(1, custIdToDelete);                            
                ps.executeUpdate();                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }            
    }
    
    public static void editCustomerDB(Customer editCust) throws ClassNotFoundException, Exception {
        
        editCustomerId = editCust.getCustomerId();
        
        PreparedStatement ps = null;
        String query = "UPDATE customer SET addressId=?, active=?," +
                "lastUpdate=?, lastUpdateBy=? WHERE customerId=?;";
        
            try {                
                ps = DatabaseConnection.makeConnection().prepareStatement(query);

                ps = conn.prepareStatement(query);
                ps.setInt(1, newAddressId);
                ps.setInt(2, editActive);                
                ps.setString(3, ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime().toString());
                ps.setString(4, userName);
                ps.setInt(5, editCustomerId);
                ps.executeUpdate();
                DatabaseConnection.closeConnection();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }            
    }
    
    public static void insertCustomerDB(Customer addCust) throws ClassNotFoundException, Exception {
        
        customerName = addCust.getCustomerName();
        
        PreparedStatement ps = null;
        String query = "INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy)"
                            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
            try {                
                ps = DatabaseConnection.makeConnection().prepareStatement(query);

                ps = conn.prepareStatement(query);
                ps.setString(1, customerName);
                ps.setInt(2, newAddressId);
                ps.setInt(3, active);                
                ps.setString(4, ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime().toString());
                ps.setString(5, userName);
                ps.setString(6, ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime().toString());
                ps.setString(7, userName);
                ps.executeUpdate();
                DatabaseConnection.closeConnection();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }            
    }
    
    public static ResultSet retrieveCustomerDB() throws ClassNotFoundException, SQLException, Exception {        
        
        Statement stmt = null;
        String query = "SELECT customer.customerId, customer.customerName, address.address, address.address2, city.city,\n" +
                        "address.postalCode, address.phone, country.country \n" +
                        "FROM customer\n" +
                        "INNER JOIN address ON customer.addressId=address.addressId\n" +
                        "INNER JOIN city ON address.cityId=city.cityId\n" +
                        "INNER JOIN country ON city.countryId=country.countryId;";        
       
            DatabaseConnection.makeConnection();
            stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

        return result;
    }
}
