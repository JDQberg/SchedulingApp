
package Model;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author TheQcrew
 */
public class SignIn {
    
    ResourceBundle rb;
    
    public static int userId;
    public static String userName;
    
    public void checkUser(String un, String pw) throws SQLException, ClassNotFoundException, Exception {
    
        ResultSet result = DataAccess.SignInDAO.checkUserDB(un, pw);

        /*
        Exception control to prevent incorrect username or password
        */

            if(result.next()){
                userId = result.getInt("userId");
                userName = result.getString("userName");
                
                User usr = new User(userId, userName);
                
            }else{
                
                rb = ResourceBundle.getBundle("LanguageFiles/rb", Locale.getDefault());
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("signInError"));
                alert.setContentText(rb.getString("invalid"));
                alert.showAndWait();
                
                loadSignIn();        
        }
    }
    
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
}
