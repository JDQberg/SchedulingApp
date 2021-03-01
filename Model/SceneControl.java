
package Model;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author TheQcrew
 */
public class SceneControl {
    
    ResourceBundle rb;
    
    public void loadSignIn() throws IOException {
        
        rb = ResourceBundle.getBundle("LanguageFiles/rb", Locale.getDefault());
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/signInScreen.fxml"));
        View_Controller.SignInScreenController controller = new View_Controller.SignInScreenController();
        loader.setController(controller);
        loader.setResources(rb);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    
    public void loadAppMain() throws IOException, Exception {

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
    
    public void loadAddCustomer() throws IOException{
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/addCustomer.fxml"));        
        View_Controller.AddCustomerController controller = new View_Controller.AddCustomerController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    public void loadAddAppointment() throws IOException, Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/addAppointment.fxml"));
        View_Controller.AddAppointmentController controller = new View_Controller.AddAppointmentController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
