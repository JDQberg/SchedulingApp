
package View_Controller;

import Model.Appointment;
import Model.Customer;
import Model.SceneControl;
import Model.User;
import static Model.Customer.getAllCustomersList;
import static Model.Appointment.deleteAppointment;
import static Model.Appointment.getUserAppointments;
import static Model.Appointment.userAppointments;
import static Model.Customer.deleteCustomer;
import static View_Controller.EditCustomerController.myMainController;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author TheQcrew
 */
public class AppMainScreenController implements Initializable {
    
    Appointment appt;
    Customer cust;
    FilteredList alertList;
    
    AddAppointmentController myAddApptController;
    EditAppointmentController myEditApptController;
    EditCustomerController myEditCustController;
    Appointment selectApptEdit;
    Customer selectCustEdit;
    
    SceneControl sc;
    
   
    @FXML
    private TableView<Appointment> mainScreenAppointmentTableView;
    @FXML
    private TableColumn<Appointment, String> appointmentViewName;   
    @FXML
    private TableColumn<Appointment, String> appointmentViewType;
    @FXML
    private TableColumn<Appointment, LocalDateTime> appointmentViewDateStart;
    @FXML
    private TableColumn<Appointment, Time> appointmentViewStartTime;
    @FXML
    private TableColumn<Appointment, Time> appointmentViewEndTime;
    @FXML
    private RadioButton appointmentViewAllRBtn;
    @FXML
    private RadioButton appointmentViewMonthRBtn;
    @FXML
    private ToggleGroup calendarView;
    @FXML
    private RadioButton appointmentViewWeekRBtn;
    @FXML
    private TableView<Customer> mainCustomerListTableView;
    @FXML
    private TableColumn<Customer, String> mainCustomerNameCol;
    @FXML
    private TableColumn<Customer, String> mainCustomerPhoneCol;
    
    
    public void refreshTableView() {
        mainScreenAppointmentTableView.refresh();
        mainCustomerListTableView.refresh();
    }
    
    public Appointment selectAppointment() {
        
        selectApptEdit = mainScreenAppointmentTableView.getSelectionModel().getSelectedItem();
        return selectApptEdit;
    }   
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
            mainScreenAppointmentTableView.setItems(Appointment.getAllAppointmentsList());
            appointmentViewName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            appointmentViewType.setCellValueFactory(new PropertyValueFactory<>("type"));
            appointmentViewDateStart.setCellValueFactory(new PropertyValueFactory<>("start"));
            appointmentViewStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            appointmentViewEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            
            mainCustomerListTableView.setItems(Customer.getAllCustomers());
            mainCustomerNameCol.setCellValueFactory(new PropertyValueFactory("customerName"));
            mainCustomerPhoneCol.setCellValueFactory(new PropertyValueFactory("phone"));
            
    }
    
    @FXML
    private void selectViewAll(MouseEvent event) throws Exception {
        
        if(appointmentViewAllRBtn.isSelected()) {
            
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
    
    @FXML
    private void selectViewByWeek(MouseEvent event) throws Exception {
        
        if(appointmentViewWeekRBtn.isSelected()){
            
            getUserAppointments();
            LocalDate now = LocalDate.now();
            LocalDate oneWeek = now.plusDays(7);
            FilteredList<Appointment> filteredData = new FilteredList<>(userAppointments);
            filteredData.setPredicate(week -> {
                LocalDate displayWeek = week.apptDate;
                return (displayWeek.isEqual(now) || displayWeek.isAfter(now)) && displayWeek.isBefore(oneWeek);
            });
        mainScreenAppointmentTableView.setItems(filteredData);
            
        }
    }
    
    @FXML
    private void selectViewByMonth(MouseEvent event) throws Exception {
        
        if(appointmentViewMonthRBtn.isSelected()){
            
            getUserAppointments();
            LocalDate now = LocalDate.now();
            LocalDate oneMonth = now.plusMonths(1);
            FilteredList<Appointment> filteredData = new FilteredList<>(userAppointments);
            filteredData.setPredicate(month -> {
                LocalDate displayMonth = month.apptDate;
                return (displayMonth.isEqual(now) || displayMonth.isAfter(now)) && displayMonth.isBefore(oneMonth);
            });
        mainScreenAppointmentTableView.setItems(filteredData);
            
        }
    }

    @FXML
    private void addAppointmentButton(MouseEvent event) throws Exception {
        
        getAllCustomersList();
        
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
    
    @FXML
    private void addCustomerButton(MouseEvent event) throws IOException {
        
        ((Node)event.getSource()).getScene().getWindow().hide();
        sc = User.getSceneControlInstance();
        sc.loadAddCustomer();
    }

        
    @FXML
    void deleteAppointmentButton(MouseEvent event) throws Exception {
        
        Appointment selectAppt = mainScreenAppointmentTableView.getSelectionModel().getSelectedItem();
        deleteAppointment(selectAppt);
        
        refreshTableView();
    }

    @FXML
    void deleteCustomerButton(MouseEvent event) throws Exception {
        
        Customer selectCust = mainCustomerListTableView.getSelectionModel().getSelectedItem();
        deleteCustomer(selectCust);
        
        refreshTableView();
    }
    
    @FXML
    private void editAppointmentButton(MouseEvent event) throws Exception {
        
        selectApptEdit = mainScreenAppointmentTableView.getSelectionModel().getSelectedItem();
        
        if(selectApptEdit == null) {
            
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please select an appointment from the table to edit.");
            alert.showAndWait();
            
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
        
        ((Node)event.getSource()).getScene().getWindow().hide();        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/editAppointment.fxml"));
        View_Controller.EditAppointmentController controller = new View_Controller.EditAppointmentController(selectApptEdit);
        loader.setController(controller);
        Parent root = loader.load();
        
        myEditApptController = (EditAppointmentController)loader.getController();
        myEditApptController.apptToEdit();
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();        
    }
    
    @FXML
    private void editCustomerButton(MouseEvent event) throws IOException {
        
        selectCustEdit = mainCustomerListTableView.getSelectionModel().getSelectedItem();
        
        if(selectCustEdit == null) {
            
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please select a customer from the table to edit.");
            alert.showAndWait();
            
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
        
        ((Node)event.getSource()).getScene().getWindow().hide();        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/editCustomer.fxml"));
        View_Controller.EditCustomerController controller = new View_Controller.EditCustomerController(selectCustEdit);
        loader.setController(controller);
        Parent root = loader.load();
        
        myEditCustController = (EditCustomerController)loader.getController();
        myEditCustController.custToEdit();
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show(); 
    }
    
    @FXML
    void apptByMonthReport(MouseEvent event) throws IOException {
        
        ((Node)event.getSource()).getScene().getWindow().hide();        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/apptByMonthReportScreen.fxml"));
        View_Controller.ApptByMonthReportScreenController controller = new View_Controller.ApptByMonthReportScreenController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    @FXML
    private void exitButton(MouseEvent event) {
        
        Platform.exit();
    }
    
    @FXML
    void scheduleReport(MouseEvent event) throws IOException {
        
        ((Node)event.getSource()).getScene().getWindow().hide();        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/scheduleReportScreen.fxml"));
        View_Controller.ScheduleReportScreenController controller = new View_Controller.ScheduleReportScreenController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    @FXML
    void totalAppointmentsReport(MouseEvent event) throws IOException {
        
        ((Node)event.getSource()).getScene().getWindow().hide();        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/totalApptReportScreen.fxml"));
        View_Controller.TotalApptReportScreenController controller = new View_Controller.TotalApptReportScreenController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }    
}
