package BDD;


import java.sql.*;

/**
 *
 * @author sqlitetutorial.net
 */
public class BDD {

    public static void createNewDatabase(String fileName) {

        String url = "jdbc:sqlite:DataBase/" + fileName;
        try { Class.forName("org.sqlite.JDBC"); } catch(Exception e) {e.printStackTrace();};
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

    /**
     * Create a new table in the test database
     *
     */
    public static void createNewTable(String pseudo) {
        // SQLite connection string
        String url = "jdbc:sqlite:DataBase/CentralMessages.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS "+pseudo+"(\n"
                + "pseudo VARCHAR(255) NOT NULL,\n "
                + " message VARCHAR(255) NOT NULL,\n "
                + " date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,\n "
                + " PRIMARY KEY ( pseudo ))";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        createNewTable("Reven");
    }
}