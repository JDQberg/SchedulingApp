
package View_Controller;

import Model.Appointment;
import static Model.Appointment.allAppointments;
import static View_Controller.EditCustomerController.myMainController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author TheQcrew
 */
public class ScheduleReportScreenController implements Initializable {
    
    FilteredList<Appointment> filteredData = new FilteredList<>(allAppointments);

    @FXML
    private TextArea scheduleTextArea;
    
    public void userSchedules() {
        filteredData.forEach(user -> {
            scheduleTextArea.appendText(user.getCreatedBy() + " has a " +
                    user.getType() + " appointment with " + user.getCustomerName()
                    + " on " + user.getStart()
                    + " at " + user.getStartTime() + "\n");     
        });
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        userSchedules();        
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
    private void exitButton(MouseEvent event) {
        
        Platform.exit();
    }    
}
