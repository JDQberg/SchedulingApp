
package View_Controller;

import Model.Customer;
import Model.SceneControl;
import static View_Controller.AddAppointmentController.myMainController;
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
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author TheQcrew
 */
public class AddCustomerController implements Initializable {

    public static String country;
    public static String city;
    public static String address;
    public static String address2;
    public static String postalCode;
    public static String phone;
    public static String customerName;
    public static int active;

    @FXML
    private TextField customerNameTxtFld;
    @FXML
    private TextField customerAddressTxtFld;
    @FXML
    private TextField customerAddress2TxtFld;
    @FXML
    private TextField customerCityTxtFld;
    @FXML
    private TextField customerCountryTxtFld;
    @FXML
    private TextField customerPostalCodeTxtFld;
    @FXML
    private TextField customerPhoneTxtFld;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void exitButton(MouseEvent event) {
        
        Platform.exit();
    }

    @FXML
    private void cancelButton(MouseEvent event) throws IOException, Exception {

        ((Node)event.getSource()).getScene().getWindow().hide();
        SceneControl sc = new SceneControl();
        sc.loadAppMain();
    }

    @FXML
    public void addCustomerSave(MouseEvent event) throws Exception {
        
        country = customerCountryTxtFld.getText();
        city = customerCityTxtFld.getText();
        address = customerAddressTxtFld.getText();
        address2 = customerAddress2TxtFld.getText();
        postalCode = customerPostalCodeTxtFld.getText();
        phone = customerPhoneTxtFld.getText();
        customerName = customerNameTxtFld.getText();
        active = 1;
        
        /*
        Exception control to prevent entering non existent or invalid customer data
        */
        
        if(!customerName.matches("[a-zA-Z 0-9]+")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Name field can not contain special characters.");
            alert.showAndWait();
            throw new IllegalArgumentException("Name field can not contain special characters.");            
        }
        
        if(!address.matches("[a-zA-Z 0-9]+")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Address field can not contain special characters.");
            alert.showAndWait();
            throw new IllegalArgumentException("Address field can not contain special characters.");            
        }
                
        if(!city.matches("[a-zA-Z 0-9]+")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("City field can not contain special characters.");
            alert.showAndWait();
            throw new IllegalArgumentException("City field can not contain special characters.");            
        }
        
        if(!country.matches("[a-zA-Z 0-9]+")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Country field can not contain special characters.");
            alert.showAndWait();
            throw new IllegalArgumentException("Country field can not contain special characters.");            
        }
        
        if(!postalCode.matches("[0-9]+")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Postal Code field can contain numbers only.");
            alert.showAndWait();
            throw new IllegalArgumentException("Postal Code field can contain numbers only.");            
        }
        
        if(!phone.matches("[0-9-]+")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Phone field can not contain special characters or letters.");
            alert.showAndWait();
            throw new IllegalArgumentException("Phone field can not contain special characters or letters.");            
        }
        
        if(customerName.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Customer Name field can not be blank.");
            alert.showAndWait();
            throw new IllegalArgumentException("Customer Name field can not be blank.");            
        }
        
        if(address.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Address field can not be blank.");
            alert.showAndWait();
            throw new IllegalArgumentException("Address field can not be blank.");            
        }
        
        if(city.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("City field can not be blank.");
            alert.showAndWait();
            throw new IllegalArgumentException("City field can not be blank.");            
        }
        
        if(postalCode.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Postal Code field can not be blank.");
            alert.showAndWait();
            throw new IllegalArgumentException("Postal Code field can not be blank.");            
        }
        
        if(phone.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Phone field can not be blank.");
            alert.showAndWait();
            throw new IllegalArgumentException("Phone field can not be blank.");            
        }
        
        if(country.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Country field can not be blank.");
            alert.showAndWait();
            throw new IllegalArgumentException("Country field can not be blank.");            
        }
        
        Customer addCust = new Customer(customerName, address, address2, city,
                postalCode, country, phone);
        
        addCust.addCustomer(addCust);
        
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
