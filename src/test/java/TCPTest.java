import Clavardage.MultiServeurTCP;
import Clavardage.clientTCP;

import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

import static org.junit.Assert.assertEquals;

public class TCPTest {

    @Test
    public void handshake() throws IOException {
        MultiServeurTCP serveur = new MultiServeurTCP(1768);
        serveur.start();
        clientTCP client = new clientTCP();
        client.startConnexion(InetAddress.getLocalHost(), 1768);
        client.sendMessage("hello dude");
        String response = client.rcvMessage();
        assertEquals("hi mate", response);
    }


}
