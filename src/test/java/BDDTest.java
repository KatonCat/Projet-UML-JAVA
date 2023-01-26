import DataBase.*;
import Clavardage.ListOfMessages;
import Clavardage.Message;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import static org.junit.Assert.assertEquals;

public class BDDTest {

    @Test
    public void TestBDD() {
        BDD.createNewDatabase("Test.db");
        BDD.createNewTable("Test","Test");
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
        BDD.insert("Test","Test",new Message("Test1","Bonjour ceci est un test",ts));
        ListOfMessages Sent = new ListOfMessages();
        Message msg = new Message("Test1","Bonjour ceci est un test",ts);
        Sent.addMsg(msg);
        ListOfMessages Test1 = BDD.select("Test","Test");
        assertEquals(Test1.getMessage().get(0).getMsg(),Sent.getMessage(0).getMsg());
        BDD.delete("Test","Test");

    }



}