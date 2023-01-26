package DataBase;


import Clavardage.ListOfMessages;
import Clavardage.Message;

import java.sql.*;

public class BDD {

    public static void createNewDatabase(String fileName) {

        String url = "jdbc:sqlite:DataBase/" + fileName;
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void createNewTable(String db,String pseudo) {
        // SQLite connection string
        String url = "jdbc:sqlite:DataBase/"+db+".db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS "+pseudo+"(\n"
                + "pseudo VARCHAR(255) NOT NULL,\n "
                + " message VARCHAR(255) NOT NULL,\n "
                + " date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,\n "
                + " PRIMARY KEY ( pseudo,message,date ))";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            System.out.println("A new database table has been created : "+pseudo);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insert(String db,String table,Message msg) {
        String sql = "INSERT INTO "+table+"(pseudo,message,date) VALUES(?,?,?)";
        String url = "jdbc:sqlite:DataBase/"+db+".db";
        System.out.println("try inser in : "+table);
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, msg.getUserName());
            pstmt.setString(2, msg.getMsg());
            pstmt.setTimestamp(3,msg.getDateTS());
            //pstmt.setString(3, DateFormatUtils.format(Date,"yyyy-MM-dd HH:mm:SS"));
            pstmt.executeUpdate();
            System.out.println("A new message has been inserted in : "+table);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void delete(String db,String table) {
        String sql = "DELETE FROM "+table;
        String url = "jdbc:sqlite:DataBase/"+db+".db";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // delete all
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ListOfMessages select(String db,String Name){
        String sql = "SELECT * FROM "+ Name;
        String url = "jdbc:sqlite:DataBase/"+db+".db";
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ListOfMessages ListeMsg= new ListOfMessages();
            // loop through the result set
            while (rs.next()) {
                ListeMsg.addMsg(new Message(rs.getString("pseudo"),rs.getString("message"),rs.getTimestamp("Date")));
            }
            for (Message mess:ListeMsg.getMessage()) {
                System.out.println(mess.getUserName() + "\t" +
                        mess.getMsg() + "\t" +
                        mess.getDateTS());
            }
            return ListeMsg;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new ListOfMessages();


    }

}