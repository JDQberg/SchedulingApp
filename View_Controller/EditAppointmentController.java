
package View_Controller;

import Model.Appointment;
import static Model.Appointment.editAppointment;
import Model.HardwareAppointment;
import Model.SoftwareAppointment;
import Model.SceneControl;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author TheQcrew
 */
public class EditAppointmentController implements Initializable {
    
    static AppMainScreenController myMainController;
    
    Appointment selectApptToEdit;
    
    int appointmentId;
    String customerName, type, computerModel, softwareTitle;
    LocalDate apptDate;
    LocalTime startTime, endTime;    
    
    public static LocalDateTime revisedApptDateTime, revisedApptEndTime;
    public static String revisedApptType, revisedApptDesc;
    public static int appointmentIdToEdit;

    @FXML
    private Label apptCutomerNameLabel;
    @FXML
    private Label apptDateLabel;
    @FXML
    private Label apptStartTimeLabel;
    @FXML
    private Label apptEndTimeLabel;
    @FXML
    private Label apptTypeLabel;
    @FXML
    private Label apptDescriptionLabel1;
    @FXML
    private Label apptDescriptionLabel2;
    @FXML
    private Label editApptDescriptionLabel;
    @FXML
    private TextField editApptDescriptionTxtFld;
    @FXML
    private DatePicker editApptDatePicker;
    @FXML
    private ComboBox<String> editApptTimeHrComboBox;
    @FXML
    private ComboBox<String> editApptTimeMinComboBox;
    @FXML
    private ComboBox<String> editApptTypeComboBox;

    EditAppointmentController(Appointment selectApptToEdit) throws InterruptedException, IOException {
        
        this.selectApptToEdit = selectApptToEdit;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Limiting appointment times to business hours with combo box
        editApptTimeHrComboBox.getItems().addAll(
                    "09", "10", "11", "12", "13", "14", "15", "16", "17");
        //each appointment limited to 30 minutes
        editApptTimeMinComboBox.getItems().addAll("00", "30");
        editApptTypeComboBox.getItems().addAll("Hardware Repair", "Software Update");
    }
    
    public void apptToEdit() throws InterruptedException, IOException {

        if(selectApptToEdit instanceof HardwareAppointment) {

            HardwareAppointment appt1 = (HardwareAppointment) selectApptToEdit;
            appointmentIdToEdit = selectApptToEdit.getAppointmentId();
            customerName = selectApptToEdit.getCustomerName();
            apptDate = selectApptToEdit.getStart();
            startTime = selectApptToEdit.getStartTime();
            endTime = selectApptToEdit.getEndTime();
            type = selectApptToEdit.getType();
            // hardware appointment specific parameter
            computerModel = appt1.getComputerModel();

            apptCutomerNameLabel.setText(customerName);
            apptDateLabel.setText(apptDate.toString());
            apptStartTimeLabel.setText(startTime.toString());
            apptEndTimeLabel.setText(endTime.toString());
            apptTypeLabel.setText(type);
            apptDescriptionLabel1.setText("Computer Model");
            apptDescriptionLabel2.setText(computerModel);            
        }
        
        if(selectApptToEdit instanceof SoftwareAppointment) {
            
            SoftwareAppointment appt2 = (SoftwareAppointment) selectApptToEdit;
            appointmentIdToEdit = selectApptToEdit.getAppointmentId();
            customerName = selectApptToEdit.getCustomerName();
            apptDate = selectApptToEdit.getStart();
            startTime = selectApptToEdit.getStartTime();
            endTime = selectApptToEdit.getEndTime();
            type = selectApptToEdit.getType();
            // software appointment specific parameter
            softwareTitle = appt2.getSoftwareTitle();

            apptCutomerNameLabel.setText(customerName);
            apptDateLabel.setText(apptDate.toString());
            apptStartTimeLabel.setText(startTime.toString());
            apptEndTimeLabel.setText(endTime.toString());
            apptTypeLabel.setText(type);
            apptDescriptionLabel1.setText("Software Title");
            apptDescriptionLabel2.setText(softwareTitle);
            
        }
    }

    @FXML
    private void editApptType(ActionEvent event) {
        if(editApptTypeComboBox.getValue().equals("Hardware Repair")){
            editApptDescriptionLabel.setText("Computer Model");
        }else{
            editApptDescriptionLabel.setText("Software Title");
        }
    }

    @FXML
    private void editAppointmentExitButton(MouseEvent event) {        
        Platform.exit();
    }

    @FXML
    private void editAppointmentCancelButton(MouseEvent event) throws Exception {
        
        ((Node)event.getSource()).getScene().getWindow().hide();
        SceneControl sc = new SceneControl();
        sc.loadAppMain();
    }

    @FXML
    private void editAppointmentSave(MouseEvent event) throws Exception {
         
        LocalDate date = editApptDatePicker.getValue();
        date.toString();
        String hour = editApptTimeHrComboBox.getValue();
        String minute = editApptTimeMinComboBox.getValue();                
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
        String txtStartTime = date + " " + hour + ":" + minute + ":00";
	revisedApptDateTime = LocalDateTime.parse(txtStartTime, format);
        revisedApptEndTime = revisedApptDateTime.plusMinutes(29);
        revisedApptType = editApptTypeComboBox.getValue();
        revisedApptDesc = editApptDescriptionTxtFld.getText();
        
        ZonedDateTime zdt = revisedApptDateTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime apptDateTimeZ = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        revisedApptDateTime = apptDateTimeZ.toLocalDateTime();
        
//        check for overlapping appointment
        ResultSet result = DataAccess.AppointmentDAOImpl.retrieveAllAppointmentsDB();
        
        while(result.next()) {
            LocalDateTime startFromDb  = result.getTimestamp("start").toLocalDateTime();
            if(startFromDb.equals(revisedApptDateTime)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("An appointment is already scheduled for this time period.");
                alert.showAndWait();
                throw new IllegalArgumentException("Duplicate appt time.");
            }
        }
        
        editAppointment();

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
}
