
package View_Controller;

import Model.Appointment;
import static Model.Appointment.allAppointments;
import static View_Controller.EditCustomerController.myMainController;
import java.io.IOException;
import java.net.URL;
import static java.time.Month.*;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author TheQcrew
 */
public class ApptByMonthReportScreenController implements Initializable {
    
    FilteredList<Appointment> filteredData = new FilteredList<>(allAppointments);
    
    public static long selectMonth;
    private int hardwareCount;
    private int softwareCount;
//    private int scrumCount;
       
    @FXML
    private Label hardwareTotalLabel;
    @FXML
    private Label softwareTotalLabel;
    @FXML
    private ToggleGroup apptByMonth;
    @FXML
    private RadioButton janRBtn;
    @FXML
    private RadioButton febRBtn;
    @FXML
    private RadioButton marRBtn;
    @FXML
    private RadioButton aprRBtn;
    @FXML
    private RadioButton mayRBtn;
    @FXML
    private RadioButton junRBtn;
    @FXML
    private RadioButton julRBtn;
    @FXML
    private RadioButton augRBtn;
    @FXML
    private RadioButton sepRBtn;
    @FXML
    private RadioButton octRBtn;
    @FXML
    private RadioButton novRBtn;
    @FXML
    private RadioButton decRBtn;

    public void countTypes() {
        
        /*
        This lambda expression efficiently iterates the allAppointments list to 
        get the number of each type of appointment
        */
        
        filteredData.forEach(type -> {
            if(type.getType().equals("Hardware Repair")) {
                hardwareCount += 1;
            }else if(type.getType().equals("Software Update")) {
                softwareCount += 1;
            }});

    }
    
    public void setTotalsLabel() {
        hardwareTotalLabel.setText(" ");
        hardwareTotalLabel.setText("Total Hardware Repair Appointments: " + hardwareCount);
        softwareTotalLabel.setText(" ");
        softwareTotalLabel.setText("Total Software Update Appointments: " + softwareCount);     
        hardwareCount = 0;
        softwareCount = 0;
    }

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

    }    

    @FXML
    private void returnToMain(MouseEvent event) throws IOException {
        
        ((Node)event.getSource()).getScene().getWindow().hide();        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/appMainScreen.fxml"));
        View_Controller.AppMainScreenController controller = new View_Controller.AppMainScreenController();
        loader.setController(controller);
        Parent root = loader.load();

        myMainController = (AppMainScreenController)loader.getController();
        myMainController.refreshTableView();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    @FXML
    private void selectReportByMonth(MouseEvent event) {
        
        /*
        These lambda expressions greatly reduced the amount of code required
        to iterate through the allAppointments list to limit the report to the 
        selected month.
        */
        
        if(janRBtn.isSelected()){           
            filteredData.setPredicate(month -> {
                return month.getStart().getMonth().equals(JANUARY);
            });
            
        countTypes();
        setTotalsLabel();
        }
        
        if(febRBtn.isSelected()){           
            filteredData.setPredicate(month -> {
                return month.getStart().getMonth().equals(FEBRUARY);
            });
            
        countTypes();
        setTotalsLabel();
        }
        
        if(marRBtn.isSelected()){           
            filteredData.setPredicate(month -> {
                return month.getStart().getMonth().equals(MARCH);
            });
            
        countTypes();
        setTotalsLabel();
        }
        
        if(aprRBtn.isSelected()){           
            filteredData.setPredicate(month -> {
                return month.getStart().getMonth().equals(APRIL);
            });
            
        countTypes();
        setTotalsLabel();
        }
        
        if(mayRBtn.isSelected()){           
            filteredData.setPredicate(month -> {
                return month.getStart().getMonth().equals(MAY);
            });
            
        countTypes();
        setTotalsLabel();
        }
        
        if(junRBtn.isSelected()){           
            filteredData.setPredicate(month -> {
                return month.getStart().getMonth().equals(JUNE);
            });
            
        countTypes();
        setTotalsLabel();
        }
        
        if(julRBtn.isSelected()){           
            filteredData.setPredicate(month -> {
                return month.getStart().getMonth().equals(JULY);
            });
            
        countTypes();
        setTotalsLabel();
        }
        
        if(augRBtn.isSelected()){           
            filteredData.setPredicate(month -> {
                return month.getStart().getMonth().equals(AUGUST);
            });
            
        countTypes();
        setTotalsLabel();
        }
        
        if(sepRBtn.isSelected()){           
            filteredData.setPredicate(month -> {
                return month.getStart().getMonth().equals(SEPTEMBER);
            });

        countTypes();
        setTotalsLabel();
        }
        
        if(octRBtn.isSelected()){           
            filteredData.setPredicate(month -> {
                return month.getStart().getMonth().equals(OCTOBER);
            });
            
        countTypes();
        setTotalsLabel();
        }
        
        if(novRBtn.isSelected()){            
            filteredData.setPredicate(month -> {
                return month.getStart().getMonth().equals(NOVEMBER);
            });
            
        countTypes();
        setTotalsLabel();
        }
        
        if(decRBtn.isSelected()){           
            filteredData.setPredicate(month -> {
                return month.getStart().getMonth().equals(DECEMBER);
            });
        
        countTypes();
        setTotalsLabel();
        }
    }

    @FXML
    private void exitButton(MouseEvent event) {        
        Platform.exit();
    }    
}
