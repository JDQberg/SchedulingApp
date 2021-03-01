
package View_Controller;

import Model.SignIn;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author TheQcrew
 */
public class SignInScreenController implements Initializable {
    
    SignInScreenController mySignInScreenController;
    
    @FXML
    private Label signInTitleLbl;
    @FXML
    private TextField userNameTxtField;
    @FXML
    private TextField passwordTxtField;
    @FXML
    private Button signInBtn;
    @FXML
    private Label userNameLbl;
    @FXML
    private Label passwordLbl;    
        
    public static String un, pw;
    
    private ResourceBundle rb;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.rb = rb;
        
        signInTitleLbl.setText(rb.getString("title"));
        signInBtn.setText(rb.getString("signIn"));
        userNameLbl.setText(rb.getString("userName"));
        passwordLbl.setText(rb.getString("password"));
    }    

    @FXML
    public void signInButtonClicked(MouseEvent event) throws ClassNotFoundException, Exception {

        un = userNameTxtField.getText();
        pw = passwordTxtField.getText();
        userNameTxtField.clear();
        passwordTxtField.clear();
        
        ((Node)event.getSource()).getScene().getWindow().hide();
        
        SignIn si = new SignIn();
        si.checkUser(un, pw);
    }
}
