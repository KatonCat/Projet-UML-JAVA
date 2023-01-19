import Clavardage.MultiServeurTCP;
import Clavardage.ServeurTCP;
import Clavardage.clientTCP;
import Connexion.Connexion;
import Connexion.Ecoute;
import Clavardage.StartSession;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class mainTest {
    public static void main(String args[]) throws IOException, ClassNotFoundException, UnknownHostException, InterruptedException {
       // new Ecoute().start();
       // new Connexion().verifyId();
        ServeurTCP Server = new ServeurTCP(1769);
        Server.start();
        //int index = ServeurTCP.threadList.findThread("127.0.0.1");
        Scanner entreeClavier = new Scanner(System.in);
        while(ServeurTCP.threadList.lengthListe()==0)
        {}
        ServeurTCP.ClientHandler thread = ServeurTCP.threadList.getThread(0);
        clientTCP client = thread.getClient();

        while(true){
            String msg = entreeClavier.nextLine();
            client.sendMessage(msg);
        }

        };
       // StartSession.StartSession(InetAddress.getByName("127.0.0.1"));
}

