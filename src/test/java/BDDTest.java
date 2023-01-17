import Clavardage.ListOfMessages;
import Clavardage.Message;

import BDD.*;
import org.junit.Test;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import static org.junit.Assert.assertEquals;

public class BDDTest {

    @Test
    public void TestBDD() {
        BDD.createNewDatabase("Test.db");
        BDD.createNewTable("Test");
        Insert app = new Insert();
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
        app.insert("Test",new Message("Test1","Bonjour ceci est un test",ts));
        Select App = new Select();
        ListOfMessages Sent = new ListOfMessages();
        Message msg = new Message("Test1","Bonjour ceci est un test",ts);
        Sent.addMsg(msg);
        ListOfMessages Test1 = App.selectAll("Test");
        assertEquals(Sent.getMessage().get(0).getMsg(),msg.getMsg());

    }



}