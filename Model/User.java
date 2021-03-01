
package Model;

import static Model.Appointment.getAllAppointments;
import static Model.Appointment.getUserAppointments;
import static Model.Appointment.userAppointments;
import static Model.Customer.getAllCustomersList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


/**
 *
 * @author TheQcrew
 */
public class User {
    
    public static int userId;
    public static String userName;
    public static String user;
    public static Locale userLocale;
    public static TimeZone userTimeZone;
    private User usr;
    public static List<Integer> currentUserId = new ArrayList<Integer>(1);    
    public static SceneControl sc = new SceneControl();    
      
    public User(int userId, String userName) throws IOException, SQLException, ClassNotFoundException, Exception, ExceptionInInitializerError {
        
        this.userId = userId;
        this.userName = userName;
        
        String fileName = "src/files/userActivity.txt";  
        
        FileWriter fwriter = new FileWriter(fileName, true); 
        
        PrintWriter output = new PrintWriter(fwriter);
        
        user = userName;
        Timestamp date = Timestamp.valueOf(LocalDateTime.now());
        
        output.print(user + ", ");
        output.println(date);
        
        output.close();
        
        currentUserId.add(userId);
        int uid = currentUserId.get(0);
        
        userLocale = Locale.getDefault();
        userTimeZone = TimeZone.getDefault();

        getAllAppointments();
        getUserAppointments();
        
        
        LocalTime current = LocalDateTime.now().toLocalTime();
        LocalTime plus15Min = current.plusMinutes(15);
        FilteredList<Appointment> filteredStartTimes = new FilteredList<>(userAppointments);
        
        /*
            This lambda expression reduced the amount of code required to iterate
            through the list of userAppointments and get the customer name and
            appointment start time for the 15 minute reminder
        */
        filteredStartTimes.setPredicate(check -> {
            
            if (check.getStart().equals(LocalDate.now()) && check.getStartTime().isAfter(current)&& check.getStartTime().isBefore(plus15Min)) {
                System.out.println("Pause");
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setContentText("You have an appointment scheduled \n" + 
                                    "with " + check.getCustomerName() + " at " + check.getStartTime());
                alert.showAndWait();
            }

            return true;
        });
        
        getAllCustomersList();        

        try{
            sc.loadAppMain();
        }catch(Error e){
            String cause = e.getCause().toString();
            System.out.println(cause);
            
        }
    }
    
    public static SceneControl getSceneControlInstance() {
        return sc;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User getUsr() {
        return usr;
    }

    public void setUsr(User usr) {
        this.usr = usr;
    }

    public static List<Integer> getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserId(List<Integer> currentUserId) {
        User.currentUserId = currentUserId;
    }
}
