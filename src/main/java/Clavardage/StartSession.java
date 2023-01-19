package Clavardage;
import BDD.BDD;
import BDD.Insert;
import Connexion.Ecoute;
import ConnexionExceptions.UserNotFoundException;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;



public class StartSession extends Thread{
    private InetAddress addr;
    private String pseudo;
    private clientTCP client = new clientTCP();
    public StartSession(InetAddress addr) throws UserNotFoundException {
        this.addr = addr;
        this.pseudo= Ecoute.liste.getUserByAdd(addr).getUserName();
    }
    public clientTCP getClient(){
        return this.client;
    }


    public  void run(){

        BDD.createNewTable("CentralMessages", addr.getHostAddress());
        ListOfMessages ListeMsg= new ListOfMessages();
        Scanner entreeClavier = new Scanner(System.in);
        try {
            client.startConnexion(addr, 1769);

            client.sendMessage("hello dude");
            String response = client.rcvMessage();
            System.out.println(addr.getHostAddress() + " : " + response);
            String MsgRecu;
            MsgRecu = client.rcvMessage();
            Date date;
            while (!this.isInterrupted()) {
                if(MsgRecu.equals("end1")) {
                    this.interrupt();
                    System.out.println("La connexion est finie, veuillez appuyer sur entrée");
                }
                System.out.println(MsgRecu);
                MsgRecu = client.rcvMessage();
                date= new Date();
                BDD.insert("CentralClass",addr.getHostAddress(),new Message(pseudo,MsgRecu,new Timestamp(date.getTime())));
                //ListeMsg.addMsg(new Message("Juan",msg, new Timestamp(date.getTime())));
            }

            System.out.println("La connexion est finie");
            client.stopConnexion();

        }catch (IOException | UserNotFoundException e) {
            e.printStackTrace();
            System.err.println("Could not connect.");
        }
    }
}
