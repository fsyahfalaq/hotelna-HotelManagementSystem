/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelaplha1;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import library.Rooms;

/**
 * FXML Controller class
 *
 * @author fikri
 */
public class FXMLDashboardAdminController implements Initializable {

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnRoom;

    @FXML
    private Button btnGuest;

    @FXML
    private Label labelTitle;

    @FXML
    private Pane paneAddNewRoom;

    @FXML
    private TextField fieldRoomNumber;

    @FXML
    private TextField fieldRoomType;

    @FXML
    private TextField fieldRoomCapacity;

    @FXML
    private Button btnSaveNewRoom;

    @FXML
    private Button btnCancelAddNewRoom;
    
    @FXML
    private Label labelErrorRoom;

    @FXML
    private GridPane paneRoom;

    @FXML
    private Button btnAddNewRoom;

    @FXML
    private TableView<Rooms> tableView;

    @FXML
    private TableColumn<Rooms, Integer> columnRoom_no;

    @FXML
    private TableColumn<Rooms, String> columnType;

    @FXML
    private TableColumn<Rooms, Integer> columnCapacity;

    @FXML
    private Button closeButton;
    
    /**
     * Initializes the controller class.
     */
    
    public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/pbo_hotel", "root", "");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ObservableList<Rooms> getRoomsList() {
        ObservableList<Rooms> roomsList = FXCollections.observableArrayList();
        Connection connection = getConnection();
        String query = "SELECT * FROM rooms";
        Statement st;
        ResultSet rs;

        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            Rooms rooms;
            while (rs.next()) {
                rooms = new Rooms(rs.getInt("Room_no"), rs.getString("Type"), rs.getInt("Capacity"), rs.getString("Status"));

                roomsList.add(rooms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roomsList;
    }
    
    public void showRooms() {
        ObservableList<Rooms> list = getRoomsList();

        columnRoom_no.setCellValueFactory(new PropertyValueFactory<Rooms, Integer>("room_no"));
        columnType.setCellValueFactory(new PropertyValueFactory<Rooms, String>("type"));
        columnCapacity.setCellValueFactory(new PropertyValueFactory<Rooms, Integer>("capacity"));

        tableView.refresh();
        tableView.setItems(list);
    }
    
    @FXML
    private void btnAddNewRoomHandle(ActionEvent event) {
        paneAddNewRoom.toFront();
        labelTitle.setText("Add New Room");
    }

    @FXML
    private void btnCancelHandle(ActionEvent event) {
        paneRoom.toFront();
        labelTitle.setText("Room");
    }

    @FXML
    public void btnSaveNewRoom(ActionEvent event) throws IOException, SQLException {
        /*
         * Checking the new room number 
         * if that number already exist labelError will contains error message 
         */
        String query = "SELECT * from rooms WHERE Room_no = ?"; 
        
        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setString(1, fieldRoomNumber.getText());
        
        ResultSet rs = preparedStatement.executeQuery();
        
        if(!rs.next()) {
            query = "INSERT into `rooms` (`Room_no`, `Type`, `Capacity`, `Status`) " + 
            "VALUES('"+fieldRoomNumber.getText()+"','"+fieldRoomType.getText()+"','"+
            fieldRoomCapacity.getText()+"','Available')";
        
            executeQuery(query);
            labelErrorRoom.setText("");
            paneRoom.toFront();
        } else {
            labelErrorRoom.setText("The room number already exists");
        }
      
    }

    @FXML
    private void closeProgram(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleNavigation(ActionEvent event) {
        if(event.getSource() == btnRoom) {
            paneRoom.toFront();
            labelTitle.setText("Room");
        } else if(event.getSource() == btnDashboard) {
            labelTitle.setText("Admin Dashboard");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showRooms();
    }    
    
}
