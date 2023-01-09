package Clavardage;
import BDD.BDD;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class StartSession {


    public static class ClientEcoute extends Thread {
        private final clientTCP client;
        public ClientEcoute(clientTCP client){
            this.client=client;
        }
        public void run() {
            String MsgRecu;
            try {
                MsgRecu = client.rcvMessage();

            while (!this.isInterrupted()) {
                if(MsgRecu.equals("end1")) {
                    this.interrupt();
                    System.out.println("La connexion est finie, veuillez appuyer sur entrée");
                };
                System.out.println(MsgRecu);
                MsgRecu = client.rcvMessage();
            }


            } catch (IOException e) {
                System.err.println("Could not read.");
                e.printStackTrace();
            }
        }
    }

    public static void StartSession(InetAddress addr) throws IOException{
        BDD.createNewTable("Juan");
        Scanner entreeClavier = new Scanner(System.in);
        clientTCP client = new clientTCP();
        client.startConnexion(addr, 1769);
        client.sendMessage("hello dude");
        String response = client.rcvMessage();
        System.out.println(response);
        ClientEcoute Oreille = new ClientEcoute(client);
        Oreille.start();
        String msg;
        while(!Oreille.isInterrupted() ^ !Oreille.isAlive()){
            msg = entreeClavier.nextLine();
            client.sendMessage(msg);
            if(msg.equals("end1")){
                Oreille.interrupt();
            }
        }
        System.out.println("La connexion est finie");
        client.stopConnexion();
    }
}
