package BDD;


import Clavardage.Message;

import java.sql.*;
import java.util.Date;

/**
 *
 * @author sqlitetutorial.net
 */
public class Insert {

    /**
     * Connect to the test.db database
     *
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:DataBase/CentralMessages.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Insert a new row into the warehouses table
     *
     *
     * @param msg
     */
    public void insert(String into,Message msg) {
        String sql = "INSERT INTO "+into+"(pseudo,message,date) VALUES(?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, msg.getUserName());
            pstmt.setString(2, msg.getMsg());
            pstmt.setTimestamp(3,msg.getDateTS());
            //pstmt.setString(3, DateFormatUtils.format(Date,"yyyy-MM-dd HH:mm:SS"));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    /*public static void main(String[] args) {

        Insert app = new Insert();
        // insert three new rows
        Date date = new Date();
        app.insert("Reven",new Message("Reven","Nah de locos",new Timestamp(date.getTime())));
        app.insert("Reven",new Message("Moi","JIJI",new Timestamp(date.getTime())));
        app.insert("Reven",new Message("Reven","Nah de revens",new Timestamp(date.getTime())));
        app.insert("Reven",new Message("Des","Que dius",new Timestamp(date.getTime())));
    }
*/
}