package Clavardage;
import BDD.BDD;
import BDD.Insert;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Date;
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
                //ListeMsg.addMsg(new Message("Juan",msg, new Timestamp(date.getTime())));
            }


            } catch (IOException e) {
                System.err.println("Could not read.");
                e.printStackTrace();
            }
        }
    }

    public static void StartSession(InetAddress addr) throws IOException{

        BDD.createNewTable("Adama");
        ListOfMessages ListeMsg= new ListOfMessages();
        Scanner entreeClavier = new Scanner(System.in);
        clientTCP client = new clientTCP();
        client.startConnexion(addr, 1769);

        client.sendMessage("hello dude");
        String response = client.rcvMessage();
        System.out.println(addr.toString()+" : "+response);

        Insert app = new Insert();

        ClientEcoute Oreille = new ClientEcoute(client);
        Oreille.start();
        String msg;
        while(!Oreille.isInterrupted() ^ !Oreille.isAlive()){
            msg = entreeClavier.nextLine();
            client.sendMessage(msg);
            Date date = new Date();
            app.insert("ADAMA",new Message(addr.getHostAddress(), msg,new Timestamp(date.getTime())) );
            //ListeMsg.addMsg(new Message("Juan",msg, new Timestamp(date.getTime())));
            if(msg.equals("end1")){
                Oreille.interrupt();
            }
        }
        System.out.println("La connexion est finie");
        client.stopConnexion();
    }
}
