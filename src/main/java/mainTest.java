import Clavardage.ServeurTCP;
import Clavardage.ClientTCP;
import ConnexionExceptions.UserNotFoundException;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class mainTest {
    public static void main(String args[]) throws IOException, ClassNotFoundException, UnknownHostException, InterruptedException, UserNotFoundException {
       // new Ecoute().start();
       // new Connexion().verifyId();
        ServeurTCP Server = new ServeurTCP(1769);
        Server.start();
        //int index = ServeurTCP.threadList.findThread("127.0.0.1");
        Scanner entreeClavier = new Scanner(System.in);

        System.out.println(ServeurTCP.clientList.lengthListe());
        ClientTCP client;

        while(true){
            String msg = entreeClavier.nextLine();
            client = ServeurTCP.clientList.getClient(0);
            client.sendMessage(msg);
        }

        };
       // StartSession.StartSession(InetAddress.getByName("127.0.0.1"));
}

