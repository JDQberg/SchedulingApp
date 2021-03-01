
package Model;

import static DataAccess.AppointmentDAOImpl.insertHardwareAppointmentDB;
import static Model.Appointment.checkOverlap;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 * @author TheQcrew
 */
public class HardwareAppointment extends Appointment {
    
    public String computerModel;
    
    public HardwareAppointment(int customerId, String customerName, int userId, String userName, 
            String type, LocalDateTime apptDateTimeZ, LocalDate apptDate, LocalTime apptStartTime, 
            LocalTime apptEndTime, String createdBy, int appointmentId, String computerModel) {        
        super(customerId, customerName, userId, userName, type, apptDateTimeZ, 
                apptDate, apptStartTime, apptEndTime, createdBy, appointmentId);
        this.computerModel = computerModel;
    }
    
    public HardwareAppointment(int customerId, String customerName, int userId, String userName, 
            String type, LocalDateTime apptDateTimeZ, LocalDate apptDate, LocalTime apptStartTime, 
            LocalTime apptEndTime, String createdBy, String computerModel) {        
        super(customerId, customerName, userId, userName, type, apptDateTimeZ,
                apptDate, apptStartTime, apptEndTime, createdBy);
        this.computerModel = computerModel;
    }
    
//    uses overloaded method in Appointment class
    public void addAppointment(HardwareAppointment addHardwareAppt)throws Exception {
        checkOverlap();
        insertHardwareAppointmentDB(addHardwareAppt);
    }

    public String getComputerModel() {
        return computerModel;
    }

    public void setComputerModel(String computerModel) {
        this.computerModel = computerModel;
    }    
}
