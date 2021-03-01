
package jeffquarnbergschedulingproject;

import Model.SceneControl;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author TheQcrew
 */
public class JeffQuarnbergSchedulingProject extends Application {

    static Stage stage;
    static ResourceBundle rb;
             
    @Override
    public void start(Stage stage) throws Exception {
        
        this.stage = stage;
        
        rb = ResourceBundle.getBundle("LanguageFiles/rb", Locale.getDefault());
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/signInScreen.fxml"));
        View_Controller.SignInScreenController controller = new View_Controller.SignInScreenController();
        loader.setController(controller);
        loader.setResources(rb);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        launch(args);
    }    
}
