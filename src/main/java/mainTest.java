import Clavardage.MultiServeurTCP;
import Connexion.Connexion;
import Connexion.Ecoute;
import Clavardage.StartSession;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class mainTest {
    public static void main(String args[]) throws IOException, ClassNotFoundException, UnknownHostException, InterruptedException {
       // new Ecoute().start();
       // new Connexion().verifyId();
        MultiServeurTCP Server = new MultiServeurTCP(1769);
        Server.start();
       // StartSession.StartSession(InetAddress.getByName("127.0.0.1"));
    }
}
