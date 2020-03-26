package hotelaplha1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
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
    private Label labelPrice;
    
    @FXML
    private TextField fieldName;

    @FXML
    private TextField fieldAddress;

    @FXML
    private TextField fieldPhone;

    @FXML
    private TextField fieldIdentityNumber;
    
    @FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;

    @FXML
    private CheckBox CheckBoxExtraBed;
    
    @FXML
    private TextField fieldRoomNumber;

    @FXML
    private TextField fieldRoomType;

    @FXML
    private TextField fieldCost;
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

    public void setRoomInfo(int roomNo, String roomType, int roomPrice) {
       
        
       fieldRoomNumber.setText(String.valueOf(roomNo));
       fieldRoomType.setText(roomType);
       labelPrice.setText(String.valueOf(roomPrice));
    }
    
    public void calculateCost() {
        LocalDate from = dateFrom.getValue();
        LocalDate end = dateTo.getValue();
        int days = (int) ChronoUnit.DAYS.between(from, end);
        
        int cost = days * Integer.valueOf(labelPrice.getText());
        
        if(CheckBoxExtraBed.isSelected()){
            cost = cost + 80000;
        }
        
        /*
         * Convert cost integer to rupiah currency
         * And Dispay cost in rupiah to cost field
         */
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
 
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
 
        kursIndonesia.setDecimalFormatSymbols(formatRp);
//        System.out.printf("Harga Rupiah: %s %n", kursIndonesia.format(harga));
 
        NumberFormat kurensiIndonesia = NumberFormat.getCurrencyInstance(new Locale("in","ID"));
        fieldCost.setText(String.valueOf(kurensiIndonesia.format(cost)));
    }
    
    public void submitForm(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDashboard.fxml"));
            Parent root = (Parent) loader.load();
            
            FXMLDashboardController DashboardController = loader.getController();
            
            //Get Room Number
            
            //Update status to booked
            String query = "UPDATE rooms SET Status='Booked' WHERE Room_no="+fieldRoomNumber.getText()+"";
            DashboardController.executeQuery(query);
            
            //Insert guest data
            query = "INSERT INTO `guests` (`name`, `phone`, `identity_number`, `room_no`) "
                    + "values('"+fieldName.getText()+"','"+fieldPhone.getText()+"','"
                    +fieldIdentityNumber.getText()+"','"+fieldRoomNumber.getText()+"')";
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
