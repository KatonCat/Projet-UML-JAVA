package Clavardage;

import Connexion.Ecoute;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.Timestamp;

public class StartSession extends Thread{
    private final InetAddress addr;
    private final ClientTCP client = new ClientTCP();
    public StartSession(InetAddress addr) {
        this.addr = addr;
        ServeurTCP.sessionList.addSession(this);
    }
    public ClientTCP getClient(){
        return this.client;
    }
    public InetAddress getAddress(){return this.addr;}

    public ListOfMessages listeMsg= new ListOfMessages();

    public  void run(){



        try {
            client.startConnexion(addr, 1769);
            client.sendMessage("hello dude");
            String response = client.rcvMessage();
            System.out.println(Ecoute.liste.toString());
            System.out.println(addr.getHostAddress() + " : " + response);
            String MsgRecu;
            MsgRecu = client.rcvMessage();

            while (!this.isInterrupted()) {

                System.out.println(MsgRecu);
                listeMsg.addMsg(new Message(addr.getHostAddress(), MsgRecu, new Timestamp(System.currentTimeMillis())));
                MsgRecu = client.rcvMessage();
                if (MsgRecu.equals("end1")) {
                    this.interrupt();
                    System.out.println("La connexion est finie, veuillez appuyer sur entrée");
                }
            }

            System.out.println("La connexion est finie");
            client.stopConnexion();

        }catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not connect.");
        }
        listeMsg.clear();
        ServeurTCP.sessionList.delSession(this);
    }
}
