import Clavardage.ServeurTCP;
import Clavardage.ClientTCP;
import ConnexionExceptions.UserNotFoundException;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

import static org.junit.Assert.assertEquals;

public class TCPTest {

    @Test
    public void handshake() throws IOException, UserNotFoundException {
        ServeurTCP serv = new ServeurTCP(1769);
        serv.start();
        ClientTCP client = new ClientTCP();
        ClientTCP client2 = new ClientTCP();
        ClientTCP client3 = new ClientTCP();
        client.startConnexion(InetAddress.getLocalHost(), 1769);
        client2.startConnexion(InetAddress.getLocalHost(), 1769);
        client3.startConnexion(InetAddress.getLocalHost(), 1769);
        client.sendMessage("hello dude");
        client2.sendMessage("hello dude");
        client3.sendMessage("hello dude");
        String response = client.rcvMessage();
        String response2 = client2.rcvMessage();
        String response3 = client3.rcvMessage();
        assertEquals("hi mate", response);
        assertEquals("hi mate", response2);
        assertEquals("hi mate", response3);
    }


}
