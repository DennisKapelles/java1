package dbproject;

import java.sql.*;
import javax.swing.*;

public class CheckConnection {
    Connection conn=null;   
    public static Connection ConnectDb() { 
        try{
            // Διμιουργεία σύνδεσης   
            Connection conn=DriverManager.getConnection("jdbc:mariadb://localhost:3306/staffevaluation", "root", ""); 
            // Μήνυμα επιτυχούς σύνδεσης
            //JOptionPane.showMessageDialog(null, "Connection to MariaDB Established Successfully!"); 
            return conn;
        }
        catch(Exception e){ 
            JOptionPane.showMessageDialog(null,e); // default μήνυμα λάθους.
        return null;
        }
    }
}
