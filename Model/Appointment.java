
package Model;

import static DataAccess.AppointmentDAOImpl.deleteAppointmentDB;
import static DataAccess.AppointmentDAOImpl.editAppointmentDB;
import View_Controller.AppMainScreenController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;


/**
 *
 * @author TheQcrew
 */
public class Appointment {    
    
    private static Appointment appt;
    
    public static int apptIdToDelete;
    public static int apptIdToUpdate;
    private int appointmentId, customerId, userId;
    
    private String customerName, userName, description, type;
    private String computerModel, softwareTitle;
    private String createdBy;
    
    public LocalDate apptDate;
    public LocalTime apptStartTime;
    public LocalTime apptEndTime;    
    
    static AppMainScreenController myMainController;
    
    public static LocalDateTime apptAlert;
    public static LocalDateTime apptDateRev;
    public static String apptAlertName;
    public static LocalDateTime apptDateTimeUTC;
    public static String apptAlertTime;
    
    public static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    public static ObservableList<String> apptAlertList = FXCollections.observableArrayList();
    public static ObservableList<Appointment> userAppointments = FXCollections.observableArrayList();
    
    public Appointment(int customerId, String customerName, int userId, String userName, String type, 
            LocalDateTime apptDateTimeUTC, LocalDate apptDate, LocalTime apptStartTime, LocalTime apptEndTime,
            String createdBy, int appointmentId) {

        this.customerId = customerId;
        this.customerName = customerName;
        this.userId = userId;
        this.userName = userName;
        this.type = type;
        this.apptDateTimeUTC = apptDateTimeUTC;
        this.apptDate = apptDate;
        this.apptStartTime = apptStartTime;
        this.apptEndTime = apptEndTime;
        this.createdBy = createdBy;
        this.appointmentId = appointmentId;
    }
    
     public Appointment(int customerId, String customerName, int userId, String userName, String type, 
             LocalDateTime apptDateTimeUTC, LocalDate apptDate, LocalTime apptStartTime, LocalTime apptEndTime,
             String createdBy) {

        this.customerId = customerId;
        this.customerName = customerName;
        this.userId = userId;
        this.userName = userName;
        this.type = type;
        this.apptDateTimeUTC = apptDateTimeUTC;
        this.apptDate = apptDate;
        this.apptStartTime = apptStartTime;
        this.apptEndTime = apptEndTime;
        this.createdBy = createdBy;
    }
     
     /*
        addAppointment overloaded method
     */
     
    public void addAppointment(HardwareAppointment addHardwareAppt) throws Exception {
        
    }
    
    public void addAppointment(SoftwareAppointment addSoftwareAppt) throws Exception {
        
    }
    
    public static void checkOverlap() throws SQLException, Exception {        
        /*
        Exception control to prevent overlapping appointments
        */
        ResultSet result = DataAccess.AppointmentDAOImpl.retrieveAllAppointmentsDB();
        
        while(result.next()) {
            LocalDateTime startFromDb  = result.getTimestamp("start").toLocalDateTime();
            if(startFromDb.equals(apptDateTimeUTC)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("An appointment is already scheduled for this time period.");
                alert.showAndWait();
                throw new IllegalArgumentException("Duplicate appt time.");
            }
        }
    }
    
    public static void deleteAppointment(Appointment selectAppt) throws Exception {
        
        apptIdToDelete = selectAppt.getAppointmentId();
        deleteAppointmentDB(apptIdToDelete);
        getAllAppointments();
    }
    
    public static void editAppointment() throws Exception {
        editAppointmentDB();
        getAllAppointments();
    }
    
    public static ObservableList<Appointment> getAllAppointments() throws SQLException, Exception {
        
        allAppointments.clear();
        ResultSet result = DataAccess.AppointmentDAOImpl.retrieveAllAppointmentsDB();
        
        while(result.next()) {               
                String customerName = result.getString("customerName");
                int customerId = result.getInt("customerId");
                int userId = result.getInt("userId");
                String type = result.getString("type");
                LocalDateTime startFromDb  = result.getTimestamp("start").toLocalDateTime();
                LocalDateTime apptDateTimeUTC  = result.getTimestamp("start").toLocalDateTime();
                ZonedDateTime zdtOut = startFromDb.atZone(ZoneId.of("UTC"));
                ZonedDateTime zdtOutToLocalTZ = zdtOut.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
                LocalDate apptDate = zdtOutToLocalTZ.toLocalDateTime().toLocalDate();
                //convert from GMT to default Time Zone 
                LocalTime apptStartTime = zdtOutToLocalTZ.toLocalTime();
                LocalTime apptEndTime = zdtOutToLocalTZ.plusMinutes(29).toLocalTime();
                String userName = User.getUserName();
                int appointmentId = result.getInt("appointmentId");
                String createdBy = result.getString("createdBy");
                String description = result.getString("description");
                
                if(type.equals("Hardware Repair")){
                    String computerModel = description;                 
                    allAppointments.add(new HardwareAppointment(customerId, customerName, userId, 
                            userName, type, apptDateTimeUTC, apptDate, apptStartTime, apptEndTime, 
                            createdBy, appointmentId, computerModel));
                }else{
                    String softwareTitle = description;                  
                    allAppointments.add(new SoftwareAppointment(customerId, customerName, userId, 
                            userName, type, apptDateTimeUTC, apptDate, apptStartTime, apptEndTime, 
                            createdBy, appointmentId, softwareTitle));
                }
        }        
        return allAppointments;
    }
    
    public static ObservableList<Appointment> getUserAppointments() throws SQLException, Exception {
        
        userAppointments.clear();
        ResultSet result = DataAccess.AppointmentDAOImpl.retrieveUserAppointmentsDB();
        
        while(result.next()) {                
                String customerName = result.getString("customerName");
                int customerId = result.getInt("customerId");
                int userId = result.getInt("userId");
                String type = result.getString("type");
                LocalDateTime startFromDb  = result.getTimestamp("start").toLocalDateTime();
                LocalDateTime apptDateTimeUTC  = result.getTimestamp("start").toLocalDateTime();
                ZonedDateTime zdtOut = startFromDb.atZone(ZoneId.of("UTC"));
                ZonedDateTime zdtOutToLocalTZ = zdtOut.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
                LocalDate apptDate = zdtOutToLocalTZ.toLocalDateTime().toLocalDate();
                //convert from GMT to default Time Zone 
                LocalTime apptStartTime = zdtOutToLocalTZ.toLocalTime();
                LocalTime apptEndTime = zdtOutToLocalTZ.plusMinutes(29).toLocalTime();
                String userName = User.getUserName();
                int appointmentId = result.getInt("appointmentId");
                String createdBy = result.getString("createdBy");
                String description = result.getString("description");
                
                if(type.equals("Hardware Repair")){
                    String computerModel = description;               
                    userAppointments.add(new HardwareAppointment(customerId, customerName, userId, 
                            userName, type, apptDateTimeUTC, apptDate, apptStartTime, apptEndTime, 
                            createdBy, appointmentId, computerModel));

                }else{
                    String softwareTitle = description;         
                    userAppointments.add(new SoftwareAppointment(customerId, customerName, userId, 
                            userName, type, apptDateTimeUTC, apptDate, apptStartTime, apptEndTime, 
                            createdBy, appointmentId, softwareTitle));
                }
        }        
        return userAppointments;
    }

    public static void setAllAppointments(ObservableList<Appointment> allAppointments) {
        Appointment.allAppointments = allAppointments;
    }
    
    public static void setUserAppointments(ObservableList<Appointment> allAppointments) {
        Appointment.userAppointments = allAppointments;
    }      
        
    public static ObservableList<Appointment> getAllAppointmentsList() {                
        return allAppointments;
    }
    
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalTime getStartTime() {
        return apptStartTime;
    }

    public void setStartTime(LocalTime apptStartTime) {
        this.apptStartTime = apptStartTime;
    }   

    public LocalTime getEndTime() {
        return apptEndTime;
    }

    public void setEndTime(LocalTime apptEndTime) {
        this.apptEndTime = apptEndTime;
    }
    
    public LocalDate getStart() {
        return apptDate;
    }

    public void setStart(LocalDate apptDate) {
        this.apptDate = apptDate;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDateTime getApptDateTimeUTC() {
        return apptDateTimeUTC;
    }

    public void setApptDateTimeUTC(LocalDateTime apptDateTimeUTC) {
        Appointment.apptDateTimeUTC = apptDateTimeUTC;
    }
}
