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

    @FXML
    private boolean loginVerification(String username, String password) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDashboard.fxml"));
        Parent root = (Parent) loader.load();

        FXMLDashboardController DashboardController = loader.getController();

        //
        boolean loginStatus = false;
        String query = "SELECT * from users WHERE username = ? AND password = ?";
        try {
            PreparedStatement preparedStatement = DashboardController.getConnection().prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                labelError.setText("Enter Correct Email/Password");
            } else {
                loginStatus = true;
            }
        } catch(SQLException ex)  {
                System.err.println(ex.getMessage());
//                status = "Exception";
        }

        return loginStatus;
    }

    @FXML
    private void loginButtonAction(ActionEvent event) throws IOException {
        
        try {
            if(loginVerification(fieldUsername.getText(), fieldPassword.getText())) {
                Parent dashboardViewParent = FXMLLoader.load(getClass().getResource("FXMLDashboard.fxml"));
                Scene dashboardViewScene = new Scene(dashboardViewParent);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(dashboardViewScene);
                window.show();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
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
