
package View_Controller;

import Model.Customer;
import Model.SceneControl;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author TheQcrew
 */
public class EditCustomerController implements Initializable {
    
    static AppMainScreenController myMainController;
    
    Customer selectCustEdit;
    
    public static String editCountry;
    public static String editCity;
    public static String editAddress;
    public static String editAddress2;
    public static String editPostalCode;
    public static String editPhone;
    public static String editCustomerName;
    public static int editCustomerId;
    public static int editActive;
    
    public static String country;
    public static String city;
    public static String address;
    public static String address2;
    public static String postalCode;
    public static String phone;
    public static String customerName;
    public static int customerId;
    public static int active;

    @FXML
    private Label editCustomerNameLbl;
    @FXML
    private TextField editCustomerAddressTxtFld;
    @FXML
    private TextField editCustomerAddress2TxtFld;
    @FXML
    private TextField editCustomerCityTxtFld;
    @FXML
    private TextField editCustomerPostalCodeTxtFld;
    @FXML
    private TextField editCustomerPhoneTxtFld;
    @FXML
    private TextField editCustomerCountryTxtFld;
    
    EditCustomerController(Customer selectCustEdit) {
        
        this.selectCustEdit = selectCustEdit;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void custToEdit() {
        
        editCustomerId = selectCustEdit.getCustomerId();
        editCustomerName = selectCustEdit.getCustomerName();
        editAddress = selectCustEdit.getAddress();
        editAddress2 = selectCustEdit.getAddress2();
        editCity = selectCustEdit.getCity();
        editPostalCode = selectCustEdit.getPostalCode();
        editPhone = selectCustEdit.getPhone(); 
        editCountry = selectCustEdit.getCountry();
        
        editCustomerNameLbl.setText(editCustomerName);
        editCustomerAddressTxtFld.setText(editAddress);
        editCustomerAddress2TxtFld.setText(editAddress2);
        editCustomerCityTxtFld.setText(editCity);
        editCustomerPostalCodeTxtFld.setText(editPostalCode);
        editCustomerPhoneTxtFld.setText(editPhone);
        editCustomerCountryTxtFld.setText(editCountry);
    }

    @FXML
    private void editCustomerSave(MouseEvent event) throws IOException, Exception {
        
        editCountry = editCustomerCountryTxtFld.getText();
        editCity = editCustomerCityTxtFld.getText();
        editAddress = editCustomerAddressTxtFld.getText();
        editAddress2 = editCustomerAddress2TxtFld.getText();
        editPostalCode = editCustomerPostalCodeTxtFld.getText();
        editPhone = editCustomerPhoneTxtFld.getText();
        editActive = 1;
        
        /*
        Exception control to prevent entering non existent or invalid customer data
        */
                
        if(!editAddress.matches("[a-zA-Z 0-9]+")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Address field can not contain special characters.");
            alert.showAndWait();
            throw new IllegalArgumentException("Address field can not contain special characters.");            
        }
                
        if(!editCity.matches("[a-zA-Z 0-9]+")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("City field can not contain special characters.");
            alert.showAndWait();
            throw new IllegalArgumentException("City field can not contain special characters.");            
        }
        
        if(!editCountry.matches("[a-zA-Z 0-9]+")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Country field can not contain special characters.");
            alert.showAndWait();
            throw new IllegalArgumentException("Country field can not contain special characters.");            
        }
        
        if(!editPostalCode.matches("[0-9]+")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Postal Code field can contain numbers only.");
            alert.showAndWait();
            throw new IllegalArgumentException("Postal Code field can contain numbers only.");            
        }
        
        if(!editPhone.matches("[0-9-]+")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Phone field can not contain special characters or letters.");
            alert.showAndWait();
            throw new IllegalArgumentException("Phone field can not contain special characters or letters.");            
        }
                
        if(editAddress.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Address field can not be blank.");
            alert.showAndWait();
            throw new IllegalArgumentException("Address field can not be blank.");            
        }
        
        if(editCity.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("City field can not be blank.");
            alert.showAndWait();
            throw new IllegalArgumentException("City field can not be blank.");            
        }
        
        if(editPostalCode.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Postal Code field can not be blank.");
            alert.showAndWait();
            throw new IllegalArgumentException("Postal Code field can not be blank.");            
        }
        
        if(editPhone.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Phone field can not be blank.");
            alert.showAndWait();
            throw new IllegalArgumentException("Phone field can not be blank.");            
        }
        
        if(editCountry.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Country field can not be blank.");
            alert.showAndWait();
            throw new IllegalArgumentException("Country field can not be blank.");            
        }
        
        /*
        * The original design prepended "edit" to each variable and did not create
        * a new Customer object.
        * Setting variables here to match Customer constructor
        */        
        country = editCountry;
        city = editCity;
        address = editAddress;
        address2 = editAddress2;
        postalCode = editPostalCode;
        phone = editPhone;
        customerName = editCustomerName;
        customerId = editCustomerId;
        
        Customer editCust = new Customer(customerId, customerName, address, address2, city,
                postalCode, country, phone);
        
        editCust.editCustomer(editCust);
        
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
    private void cancelButton(MouseEvent event) throws IOException, Exception {
        
        ((Node)event.getSource()).getScene().getWindow().hide();
        SceneControl sc = new SceneControl();
        sc.loadAppMain();
    }

    @FXML
    private void exitButton(MouseEvent event) {
        
        Platform.exit();
    }    
}
