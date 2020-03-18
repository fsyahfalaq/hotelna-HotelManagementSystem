/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Koneksi {
    Connection koneksi;
    
    public static Connection Koneksi() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection koneksi = DriverManager.getConnection("jdbc:mysql://localhost/tutorialjava", "root", "");
            
            return koneksi;
        } catch(ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            
            return null;
        }
    }
}