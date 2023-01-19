import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

import Clavardage.MultiServeurTCP;
import Clavardage.StartSession;
import Clavardage.clientTCP;
import Connexion.RemoteUser;
import ConnexionExceptions.UserNotFoundException;

import static Connexion.Ecoute.liste;

public class TestClient {
    public static void main(String args[]) throws IOException, UserNotFoundException, InterruptedException {
        //liste.addUser(new RemoteUser("moi",InetAddress.getByName("127.0.0.1")));
        StartSession Session = new StartSession(InetAddress.getByName("127.0.0.1"));
        Session.start();
        clientTCP client = Session.getClient();
        Scanner entreeClavier = new Scanner(System.in);
        while(true) {
            String Smsg = entreeClavier.nextLine();
            client.sendMessage(Smsg);
        }
    }

}
