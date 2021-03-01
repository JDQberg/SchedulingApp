
package Model;

import static DataAccess.AddressDAOImpl.editAddressDB;
import static DataAccess.AddressDAOImpl.insertAddressDB;
import static DataAccess.AppointmentDAOImpl.deleteRelatedAppointmentDB;
import static DataAccess.CityDAOImpl.editCityDB;
import static DataAccess.CityDAOImpl.insertCityDB;
import static DataAccess.CountryDAOImpl.editCountryDB;
import static DataAccess.CountryDAOImpl.insertCountryDB;
import static DataAccess.CustomerDAOImpl.deleteCustomerDB;
import static DataAccess.CustomerDAOImpl.editCustomerDB;
import static DataAccess.CustomerDAOImpl.insertCustomerDB;
import static Model.Appointment.getAllAppointments;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author TheQcrew
 */
public class Customer {
    
    private int customerId;
    private String customerName, address, address2, city, postalCode, country, phone;
    public static int custIdToDelete;
    public static Customer newestCust = null;
    
    public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    
    public Customer(int customerId, String customerName, String address,
            String address2, String city, String postalCode, String country, 
            String phone){        
                
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.phone = phone;
    }
    
    public Customer(String customerName, String address,
            String address2, String city, String postalCode, String country, 
            String phone){        
                
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.phone = phone;
    }
    
    public void addCustomer(Customer addCust) throws Exception {        

        insertCountryDB(addCust);
        insertCityDB(addCust);
        insertAddressDB(addCust);
        insertCustomerDB(addCust);      
        getAllCustomersList();
    }
    
    public static void deleteCustomer(Customer selectCust) throws Exception {
        
        custIdToDelete = selectCust.getCustomerId();
        deleteRelatedAppointmentDB();
        deleteCustomerDB();
        getAllAppointments();
        getAllCustomersList();
    }
    
    public static void editCustomer(Customer editCust) throws Exception {
        
        editCountryDB(editCust);
        editCityDB(editCust);
        editAddressDB(editCust);
        editCustomerDB(editCust);        
        getAllCustomersList();
    }
    
    public static ObservableList<Customer> getAllCustomersList() throws SQLException, Exception {
        
        allCustomers.clear();
        
        ResultSet result = DataAccess.CustomerDAOImpl.retrieveCustomerDB();
        
            while(result.next()) {
                int customerId = result.getInt("customerId");
                String customerName = result.getString("customerName");
                String address = result.getString("address");
                String address2 = result.getString("address2");
                String city = result.getString("city");
                String postalCode = result.getString("postalCode");
                String phone = result.getString("phone");
                String country = result.getString("country");
                
                Customer cust = new Customer(customerId, customerName, address, address2,
                                city, postalCode, country, phone);
                allCustomers.add(cust);
                
            }
            newestCust = allCustomers.get(allCustomers.size()-1);

        return allCustomers;
    }
    
    public static ObservableList<Customer> getAllCustomers() {                
        return allCustomers;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        if(address.isEmpty()){
            throw new IllegalArgumentException("Address field can not be blank.");
        }
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if(city.isEmpty()){
            throw new IllegalArgumentException("City field can not be blank.");
        }
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }  
}
