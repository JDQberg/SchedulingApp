
package DataAccess;

import static DataAccess.DatabaseConnection.conn;
import Model.HardwareAppointment;
import static Model.Customer.custIdToDelete;
import Model.SoftwareAppointment;
import Model.User;
import static Model.User.currentUserId;
import static View_Controller.EditAppointmentController.appointmentIdToEdit;
import static View_Controller.EditAppointmentController.revisedApptDateTime;
import static View_Controller.EditAppointmentController.revisedApptEndTime;
import static View_Controller.EditAppointmentController.revisedApptType;
import static View_Controller.EditAppointmentController.revisedApptDesc;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 *
 * @author TheQcrew
 */
public class AppointmentDAOImpl {

    public static ResultSet result;
    public static int customerId, userId, apptIdToDelete;
    public static String customerName, userName, description, type, computerModel, softwareTitle;
    public static LocalDateTime apptDateTimeUTC, apptEndTime;
    
    
    public static void deleteAppointmentDB(int apptIdToDelete) throws ClassNotFoundException, Exception {
        
        PreparedStatement ps = null;
        String query = "DELETE FROM appointment WHERE appointmentId=(?)";

            try {

                ps = conn.prepareStatement(query);
                ps.setInt(1, apptIdToDelete);                            
                ps.executeUpdate();
                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }            
    }
    
    public static void deleteRelatedAppointmentDB() throws ClassNotFoundException, Exception {
        
        PreparedStatement ps = null;
        String query = "DELETE FROM appointment WHERE customerId=(?)";

            try {

                ps = conn.prepareStatement(query);
                ps.setInt(1, custIdToDelete);                            
                ps.executeUpdate();
                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }            
    }
    
    public static void editAppointmentDB() {
        
        PreparedStatement ps = null;
        userName = User.getUserName();

        String query = "UPDATE appointment SET description = ?, type = ?, start = ?, "
                + "end = ?, lastUpdate = ?, lastUpdateBy = ? WHERE appointmentId = ?;";
        
        try {
            
                ps = conn.prepareStatement(query);
                ps.setString(1, revisedApptDesc);
                ps.setString(2, revisedApptType);
                ps.setObject(3, revisedApptDateTime);//appointment start time
                ps.setObject(4, revisedApptEndTime = revisedApptDateTime.plusMinutes(29));//end time
                ps.setString(5, ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime().toString());//lastUpdate
                ps.setString(6, userName);//lastUpdateBy
                ps.setInt(7, appointmentIdToEdit);
                ps.executeUpdate();
                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }        
    }
    
    public static void insertHardwareAppointmentDB(HardwareAppointment addHardwareAppt) throws ClassNotFoundException, Exception {
        
        customerId = addHardwareAppt.getCustomerId();
        userId = addHardwareAppt.getUserId();
        type = addHardwareAppt.getType();
        apptDateTimeUTC = addHardwareAppt.getApptDateTimeUTC();
        description = addHardwareAppt.getComputerModel();
        userName = User.getUserName();
        
        PreparedStatement ps = null;
        String query = "INSERT INTO appointment (customerId, userId, title," +
                       "description, location, contact, type, url, start, end," +
                       "createDate, createdBy, lastUpdate, lastUpdateBy)" +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
            try {
                ps = conn.prepareStatement(query);
                ps.setInt(1, customerId);
                ps.setInt(2, userId);
                ps.setString(3, "not needed"); 
                ps.setString(4, description);
                ps.setString(5, "not needed");
                ps.setString(6, "not needed");
                ps.setString(7, type);
                ps.setString(8, "not needed");
                ps.setObject(9, apptDateTimeUTC);//appointment start time in UTC
                ps.setObject(10, apptEndTime = apptDateTimeUTC.plusMinutes(29));//end time
                ps.setString(11, ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime().toString());//created date
                ps.setString(12, userName);//createdBy userName
                ps.setString(13, ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime().toString());//lastUpdate
                ps.setString(14, userName);//lastUpdateBy                
                ps.executeUpdate();                
            } catch (SQLException ex) {
                ex.printStackTrace();
                }            
    }
    
    public static void insertSoftwareAppointmentDB(SoftwareAppointment addSoftwareAppt) throws ClassNotFoundException, Exception {
        
        customerId = addSoftwareAppt.getCustomerId();
        userId = addSoftwareAppt.getUserId();
        type = addSoftwareAppt.getType();
        apptDateTimeUTC = addSoftwareAppt.getApptDateTimeUTC();
        description = addSoftwareAppt.getSoftwareTitle();
        userName = User.getUserName();
        
        PreparedStatement ps = null;
        String query = "INSERT INTO appointment (customerId, userId, title," +
                       "description, location, contact, type, url, start, end," +
                       "createDate, createdBy, lastUpdate, lastUpdateBy)" +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
            try {
                ps = conn.prepareStatement(query);
                ps.setInt(1, customerId);
                ps.setInt(2, userId);
                ps.setString(3, "not needed"); 
                ps.setString(4, description);
                ps.setString(5, "not needed");
                ps.setString(6, "not needed");
                ps.setString(7, type);
                ps.setString(8, "not needed");
                ps.setObject(9, apptDateTimeUTC);//appointment start time in UTC
                ps.setObject(10, apptEndTime = apptDateTimeUTC.plusMinutes(29));//end time
                ps.setString(11, ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime().toString());//created date
                ps.setString(12, userName);//createdBy userName
                ps.setString(13, ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime().toString());//lastUpdate
                ps.setString(14, userName);//lastUpdateBy                
                ps.executeUpdate();                
            } catch (SQLException ex) {
                ex.printStackTrace();
                }            
    }
    
    public static ResultSet retrieveAllAppointmentsDB() throws ClassNotFoundException, SQLException, Exception {
        
        Statement stmt = null;

        String query = "SELECT customer.customerId, user.userId, \n" +
                       "customer.customerName, address.phone, appointment.type, \n" +
                       "appointment.start, appointment.end, appointment.appointmentId, \n" + 
                       "appointment.createdBy, appointment.description FROM appointment \n" +
                       "INNER JOIN customer ON customer.customerId=appointment.customerId \n" +
                       "INNER JOIN address ON customer.addressId=address.addressId \n" +
                       "INNER JOIN user ON appointment.userId=user.userId \n" +
                       "ORDER BY appointment.start;";
                
        stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(query);
        return result;        
    }
    
    public static ResultSet retrieveUserSchedulesDB() throws SQLException {        
        Statement stmt;        
        String query = "SELECT createdBy, type, start FROM appointment ORDER BY createdBy;";
        stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(query);
        return result;
    }
    
    public static ResultSet retrieveUserAppointmentsDB() throws ClassNotFoundException, SQLException, Exception {        
        userId = currentUserId.get(0);        
        Statement stmt = null;
        String query = "SELECT customer.customerId, user.userId, \n" +
                       "customer.customerName, address.phone, appointment.type, \n" +
                       "appointment.start, appointment.end, appointment.appointmentId, \n" +
                       "appointment.createdBy, appointment.description FROM appointment \n" +
                       "INNER JOIN customer ON customer.customerId=appointment.customerId \n" +
                       "INNER JOIN address ON customer.addressId=address.addressId \n" +
                       "INNER JOIN user ON appointment.userId=user.userId \n" +
                       "WHERE appointment.userId=" + userId + ";";
        stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(query);
        return result;        
    }
}