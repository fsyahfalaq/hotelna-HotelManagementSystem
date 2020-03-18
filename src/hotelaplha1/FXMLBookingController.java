package hotelaplha1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author fikri
 */
public class FXMLBookingController implements Initializable {

    @FXML
    private Button closeButton;

    @FXML
     private Label roomNumber;
    
    @FXML
    private TextField fieldName;

    @FXML
    private TextField fieldAddress;

    @FXML
    private TextField fieldPhone;

    @FXML
    private TextField fieldIdentityNumber;

    /**
     * Initializes the controller class.
     */
    @FXML
    public void closeForm() {
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    public void setRoomNo(String roomNo) {
       roomNumber.setText(roomNo);
    }
    
    public void submitForm(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDashboard.fxml"));
            Parent root = (Parent) loader.load();
            
            FXMLDashboardController DashboardController = loader.getController();
            
            //Get Room Number
            
            //Update status to booked
            String query = "UPDATE rooms SET Status='Booked' WHERE Room_no="+roomNumber.getText()+"";
            DashboardController.executeQuery(query);
            
            //Insert guest data
            query = "INSERT INTO `guests` (`name`, `address`, `phone`, `identity_number`, `room_no`) "
                    + "values('"+fieldName.getText()+"','"+fieldAddress.getText()+"',"+fieldPhone.getText()+","
                    +fieldIdentityNumber.getText()+","+roomNumber.getText()+")";
            DashboardController.executeQuery(query);
            
            DashboardController.reloadTable();
            
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
