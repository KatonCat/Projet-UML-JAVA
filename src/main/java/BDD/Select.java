package BDD;
import Clavardage.ListOfMessages;
import Clavardage.Message;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Select {

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


     public ListOfMessages selectAll(String Name){
        String sql = "SELECT * FROM "+ Name;

         try {
         Connection conn = this.connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql);
         ListOfMessages ListeMsg= new ListOfMessages();
         // loop through the result set
         while (rs.next()) {
             /*System.out.println(rs.getString("pseudo") + "\t" +
             rs.getString("message") + "\t" +
             rs.getTimestamp("Date"));*/
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


     /**
              * @param args the command line arguments
              */
     public static void main(String[] args) {
        Select app = new Select();
        app.selectAll("Pascal");
     }

}


