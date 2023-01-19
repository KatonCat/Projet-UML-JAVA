package Clavardage;
import BDD.BDD;
import BDD.Insert;
import ConnexionExceptions.UserNotFoundException;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;

import static Connexion.Ecoute.liste;

public class StartSession extends Thread{
    private InetAddress addr;
    private String pseudo;
    private clientTCP client = new clientTCP();
    public StartSession(InetAddress addr) throws UserNotFoundException {
        this.addr = addr;
        //this.pseudo= liste.getUserByAdd(addr).getUserName();
    }
    public clientTCP getClient(){
        return this.client;
    }




    /*public static class ClientEcoute extends Thread {
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
                }
                System.out.println(MsgRecu);
                MsgRecu = client.rcvMessage();
                //ListeMsg.addMsg(new Message("Juan",msg, new Timestamp(date.getTime())));
            }


            } catch (IOException e) {
                System.err.println("Could not read.");
                e.printStackTrace();
            }
        }
    }*/

    public  void run(){

        //BDD.createNewTable("CentralMessages", addr.getHostAddress()+pseudo);
        ListOfMessages ListeMsg= new ListOfMessages();
        Scanner entreeClavier = new Scanner(System.in);
        try {
            client.startConnexion(addr, 1769);

            client.sendMessage("hello dude");
            String response = client.rcvMessage();
            System.out.println(addr.getHostAddress() + " : " + response);
            String MsgRecu;
            MsgRecu = client.rcvMessage();

            while (!this.isInterrupted()) {
                if(MsgRecu.equals("end1")) {
                    this.interrupt();
                    System.out.println("La connexion est finie, veuillez appuyer sur entrée");
                }
                System.out.println(MsgRecu);
                MsgRecu = client.rcvMessage();
                //ListeMsg.addMsg(new Message("Juan",msg, new Timestamp(date.getTime())));
            }

            /*ClientEcoute Oreille = new ClientEcoute(client);
            Oreille.start();
            String msg;
            while (!Oreille.isInterrupted() ^ !Oreille.isAlive()) {
                msg = entreeClavier.nextLine();
                client.sendMessage(msg);
                Date date = new Date();
                //BDD.insert("CentralMessages", addr.getHostAddress()+pseudo, new Message(addr.getHostAddress(), msg, new Timestamp(date.getTime())));
                //ListeMsg.addMsg(new Message("Juan",msg, new Timestamp(date.getTime())));
                if (msg.equals("end1")) {
                    Oreille.interrupt();
                }
            }*/
            System.out.println("La connexion est finie");
            client.stopConnexion();

        }catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not connect.");
        }
    }
}
