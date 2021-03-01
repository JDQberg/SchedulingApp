
package View_Controller;

import Model.Customer;
import Model.User;
import Model.SceneControl;
import static Model.Appointment.getAllAppointments;
import Model.HardwareAppointment;
import Model.SoftwareAppointment;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author TheQcrew
 */
public class AddAppointmentController implements Initializable {
    
    Customer cust;
    
    static AppMainScreenController myMainController;
    
    public static int customerId;
    public static int userId;
    public static String customerName, phone, userName, type, description, 
            createdBy, computerModel, softwareTitle;    
    
    public LocalDate apptDate;
    public LocalTime apptStartTime, apptEndTime;
    public static LocalDateTime newApptDateTime, apptDateTimeUTC;
    
    private AddAppointmentController myAddApptController;

    @FXML
    private TableView<Customer> customerListTableView;
    @FXML
    private TableColumn<Customer, String> addAppointmentCustomerName;
    @FXML
    private TableColumn<Customer, String> addAppointmentCustomerPhone;
    @FXML
    private DatePicker apptDatePicker;
    @FXML
    private ComboBox<String> apptTimeHrComboBox;
    @FXML
    private ComboBox<String> apptTimeMinComboBox;
    @FXML
    private ComboBox<String> apptTypeComboBox;
    @FXML
    private Label apptDescriptionLabel;
    @FXML
    private TextField apptDescriptionTxtFld;
    
    public AddAppointmentController() throws Exception {

    }
    
    public String getApptType() {
        type = this.apptTypeComboBox.getValue();
        apptDescriptionLabel.setText(type);
            if(type.equals("Hardware Repair")){
                apptDescriptionLabel.setText("Computer Model");
            }else{
                apptDescriptionLabel.setText("Software Title");
            }        
        return type;
    }
    
    public void refreshTableView() {
        customerListTableView.refresh();
    }    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        customerListTableView.setItems(Customer.getAllCustomers());
        addAppointmentCustomerName.setCellValueFactory(new PropertyValueFactory("customerName"));
        addAppointmentCustomerPhone.setCellValueFactory(new PropertyValueFactory("phone"));
        
        //Limiting appointment times to business hours with combo box
        apptTimeHrComboBox.getItems().addAll(
                    "09", "10", "11", "12", "13", "14", "15",
                    "16", "17");
        //each appointment limited to 30 minutes
        apptTimeMinComboBox.getItems().addAll("00", "30");
        apptTypeComboBox.getItems().addAll("Hardware Repair", "Software Update");
    }    

    @FXML
    private void addAppointmentExitButton(MouseEvent event) {        
        Platform.exit();       
    }

    @FXML
    private void addAppointmentCancelButton(MouseEvent event) throws IOException, Exception {
        
        ((Node)event.getSource()).getScene().getWindow().hide();
        SceneControl sc = new SceneControl();
        sc.loadAppMain();
    }

    @FXML
    public void addAppointmentSave(MouseEvent event) throws Exception {
        
        try {
            cust = customerListTableView.getSelectionModel().getSelectedItem();
            int customerId = cust.getCustomerId();
            String customerName = cust.getCustomerName();
            List<Integer> currentUserId = User.getCurrentUserId();
            int userId = currentUserId.get(0);
            String userName = User.getUserName();
            String createdBy = userName;
            String type = apptTypeComboBox.getValue();
            String description = apptDescriptionTxtFld.getText();
            LocalDate date = apptDatePicker.getValue();
            date.toString();
            String hour = apptTimeHrComboBox.getValue();
            String minute = apptTimeMinComboBox.getValue();            
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
            String txtStartTime = date + " " + hour + ":" + minute + ":00";
            
            newApptDateTime = LocalDateTime.parse(txtStartTime, format);
            // change to apptDateTime
            ZonedDateTime zdt = newApptDateTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
            ZonedDateTime apptDateTimeZ = zdt.withZoneSameInstant(ZoneId.of("UTC"));
            apptDateTimeUTC = apptDateTimeZ.toLocalDateTime();
            
//            LocalDateTime apptDateTimeUTC  = result.getTimestamp("start").toLocalDateTime();
            ZonedDateTime zdtOut = apptDateTimeUTC.atZone(ZoneId.of("UTC"));
            ZonedDateTime zdtOutToLocalTZ = zdtOut.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
            LocalDate apptDate = zdtOutToLocalTZ.toLocalDateTime().toLocalDate();
            //convert from GMT to default Time Zone 
            LocalTime apptStartTime = zdtOutToLocalTZ.toLocalTime();
            LocalTime apptEndTime = zdtOutToLocalTZ.plusMinutes(29).toLocalTime();
            
            // appointment object by type
            
            if(type.equals("Hardware Repair")){
                    String computerModel = description;
                    HardwareAppointment addHardwareAppt = new HardwareAppointment(customerId, customerName, userId, 
                            userName, type, apptDateTimeUTC, apptDate, apptStartTime, 
                            apptEndTime, createdBy, computerModel);
                    addHardwareAppt.addAppointment(addHardwareAppt);
                }else{
                    String softwareTitle = description;                        
                    SoftwareAppointment addSoftwareAppt = new SoftwareAppointment(customerId, customerName, userId, 
                            userName, type, apptDateTimeUTC, apptDate, apptStartTime, 
                            apptEndTime, createdBy, softwareTitle);
                    addSoftwareAppt.addAppointment(addSoftwareAppt);
                }            

            getAllAppointments();

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
        
        } catch(NullPointerException ex) {
        
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You must select a customer.");
            alert.showAndWait();
            
            ((Node)event.getSource()).getScene().getWindow().hide();        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/addAppointment.fxml"));
            View_Controller.AddAppointmentController controller = new View_Controller.AddAppointmentController();
            loader.setController(controller);
            Parent root = loader.load();

            myAddApptController = (AddAppointmentController)loader.getController();
            myAddApptController.refreshTableView();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }        
    }

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        AddAppointmentController.phone = phone;
    }

    public static LocalDateTime getApptDateTime() {
        return newApptDateTime;
    }

    public static void setApptDateTime(LocalDateTime apptDateTime) {
        AddAppointmentController.newApptDateTime = apptDateTime;
    }

    public LocalTime getApptEndTime() {
        return apptEndTime;
    }

    public void setApptEndTime(LocalTime apptEndTime) {
        this.apptEndTime = apptEndTime;
    }       
}
