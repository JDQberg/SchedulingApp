
package Model;

import static DataAccess.AppointmentDAOImpl.insertSoftwareAppointmentDB;
import static Model.Appointment.checkOverlap;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 * @author TheQcrew
 */
public class SoftwareAppointment extends Appointment {
    
    private String softwareTitle;

    public SoftwareAppointment(int customerId, String customerName, int userId, String userName, 
            String type, LocalDateTime apptDateTimeUTC, LocalDate apptDate, LocalTime apptStartTime, 
            LocalTime apptEndTime, String createdBy, int appointmentId, String softwareTitle) {        
        super(customerId, customerName, userId, userName, type, apptDateTimeUTC, 
                apptDate, apptStartTime, apptEndTime, createdBy, appointmentId);
        this.softwareTitle = softwareTitle;
    }
    
    public SoftwareAppointment(int customerId, String customerName, int userId, String userName, 
            String type, LocalDateTime apptDateTimeUTC, LocalDate apptDate, LocalTime apptStartTime, 
            LocalTime apptEndTime, String createdBy, String softwareTitle) {        
        super(customerId, customerName, userId, userName, type, apptDateTimeUTC,
                apptDate, apptStartTime, apptEndTime, createdBy);
        this.softwareTitle = softwareTitle;
    }
    
//    uses overloaded method in Appointment class
    public void addAppointment(SoftwareAppointment addSoftwareAppt) throws Exception { 
        checkOverlap();
        insertSoftwareAppointmentDB(addSoftwareAppt);
    }

    public String getSoftwareTitle() {
        return softwareTitle;
    }

    public void setSoftwareTitle(String softwareTitle) {
        this.softwareTitle = softwareTitle;
    }    
    
}
