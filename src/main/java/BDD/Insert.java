package BDD;


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
     * @param name
     * @param msg
     */
    public void insert(String name, String msg, Timestamp Date) {
        String sql = "INSERT INTO warehouses(name,capacity) VALUES(?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, msg);
            pstmt.setTimestamp(3,Date);
            //pstmt.setString(3, DateFormatUtils.format(Date,"yyyy-MM-dd HH:mm:SS"));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Insert app = new Insert();
        // insert three new rows
        Date date = new Date();
        app.insert("Reven", "buenos dias hermano que pasa",new Timestamp(date.getTime()));
        app.insert("Moi", "No tengo ni idea",new Timestamp(date.getTime()));
        app.insert("Moi", "no pasa res",new Timestamp(date.getTime()));
    }

}