
package View_Controller;

import static Model.Appointment.allAppointments;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author TheQcrew
 */
public class TotalApptReportScreenController implements Initializable {

    String apptTotal;
    
    @FXML
    private Label totalApptLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        appointmentTotal();
        totalApptLabel.setText(apptTotal);
    }    

    public void appointmentTotal() {
        
        int count = allAppointments.size();
        apptTotal = String.valueOf(count);
    }
    
    @FXML
    private void returnToMain(MouseEvent event) throws IOException {
        
        ((Node)event.getSource()).getScene().getWindow().hide();        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/appMainScreen.fxml"));
        View_Controller.AppMainScreenController controller = new View_Controller.AppMainScreenController();
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
}
