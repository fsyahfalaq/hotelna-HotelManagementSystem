/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelaplha1;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import library.Users;

/**
 *
 * @author fikri
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private Button button;
    
    @FXML
    private TextField fieldUsername;

    @FXML
    private PasswordField fieldPassword;

    @FXML
    private Label labelError;
    
    /*
     *  loginVerification will return boolean true or false as a sign if the user is found in database 
     */
    private boolean loginVerification(String username, String password) throws IOException, SQLException {
        
        /*
         * Get DashboardController using FXML Loader
         */
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDashboard.fxml"));
        Parent root = (Parent) loader.load();
        FXMLDashboardController DashboardController = loader.getController();

        /* Initialize loginStatus
         * Default login Status is falsse
         */
        boolean loginStatus = false;
        
        String query = "SELECT * from users WHERE username = ? AND password = ?";
        
        /*
         *  Verification process
         */
        try {
            PreparedStatement preparedStatement = DashboardController.getConnection().prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            
            //Get result from query
            ResultSet rs = preparedStatement.executeQuery();
            
            /* If email or password are not found, labelError will contains error message
             * If else the loginStatus will contains true 
             */
            if (!rs.next()) {
                labelError.setText("Enter Correct Email/Password");
            } else {
                loginStatus = true;
            }
        } catch(SQLException ex)  {
                System.err.println(ex.getMessage());
        }
        return loginStatus;
    }
    
    private Users getUserData(String username, String password) throws IOException, SQLException {
        
        /*
         * Get DashboardController using FXML Loader
         */
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDashboard.fxml"));
        Parent root = (Parent) loader.load();
        FXMLDashboardController DashboardController = loader.getController();
        
        
        /*
         *  Process to get user data from db
         */
        String query = "SELECT id, name, username, role FROM users WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement = DashboardController.getConnection().prepareStatement(query);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        
        ResultSet rs = preparedStatement.executeQuery();
        
        Users user = null;
        if(rs.next()) {
            user = new Users(rs.getInt("id"), rs.getString("name"), rs.getString("username"), rs.getInt("role"));
        }
        
        return user;
    }

    
    private String getFxmlName(int role) {
        String fxmlName = "";
        
        if (role == 1) {
            fxmlName = "FXMLDashboardAdmin.fxml";
        } else if (role == 2) {
            fxmlName = "FXMLDashboard.fxml";
        }
        
        return fxmlName;
    }
    
    
    @FXML
    private void loginButtonAction(ActionEvent event) throws IOException, SQLException {
        
    String username = fieldUsername.getText();
    String password = fieldPassword.getText();
    
    if(loginVerification(username, password) == true) {
        Users user = getUserData(username, password);
        
        String fxmlName = getFxmlName(user.getRole());
        Parent dashboardViewParent = FXMLLoader.load(getClass().getResource(fxmlName));
        Scene dashboardViewScene = new Scene(dashboardViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(dashboardViewScene);
        window.show();
    }
        
        
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
      
    }

}
