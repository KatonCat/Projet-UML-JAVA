package Clavardage;

import BDD.BDD;
import BDD.CreateBDD;
import BDD.Insert;
import ConnexionExceptions.UserNotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;
import static Connexion.Ecoute.liste;

public class MultiServeurTCP extends Thread{
    private ServerSocket serverSocket;
    private final int port;
    public MultiServeurTCP(int port){this.port = port;}
    public void run(){
        try {
            //BDD.createNewDatabase("CentralMessages.db");
            serverSocket = new ServerSocket(port);

            while(true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not create the server n/or accept.");
        }
    }
    public void close() throws IOException {
        serverSocket.close();
    }

    private static class ClientHandler extends Thread{
        private final Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public static class ServEcoute extends Thread {
            final private Socket clientSocket;
            final private PrintWriter out;
            final private InetAddress addr;
            //final private String pseudo;
            public ServEcoute(Socket clientSocket, PrintWriter out) throws UserNotFoundException {
                this.clientSocket = clientSocket;
                this.out = out;
                this.addr=clientSocket.getInetAddress();
                //this.pseudo= liste.getUserByAdd(addr).getUserName() ;

            }

            public void run() {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String msgRecu;


                    while (!this.isInterrupted()) {

                        if ((msgRecu = in.readLine()) == null) break;
                        Date date = new Date();
                        //BDD.insert("CentralMessages", addr.getHostAddress()+pseudo, new Message(pseudo, msgRecu,new Timestamp(date.getTime())) );

                        System.out.println(msgRecu);
                        if ("hello dude".equals(msgRecu)) {
                            out.println("hi mate");
                        }
                        if (msgRecu.equals("end1")) {
                            this.interrupt();

                        }
                    }
                    System.out.println("La connexion est finie, veuillez appuyer sur entrée");

                } catch (IOException e) {
                    System.err.println("Could not read.");
                    this.interrupt();
                    e.printStackTrace();

                }
            }
        }



        public void init() throws IOException, UserNotFoundException {
            //BDD.createNewTable("CentralMessages","Pascal");
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ServEcoute Oreille = new ServEcoute(clientSocket,out);
            Oreille.start();
            Scanner entreeClavier = new Scanner(System.in);

            Insert app = new Insert();
            Date date = new Date();

            String Smsg;


            while (!Oreille.isInterrupted()) {
                Smsg = entreeClavier.nextLine();
                out.println(Smsg);
                //app.insert("Pascal",new Message("Moi", Smsg,new Timestamp(date.getTime())) );
                if (Smsg.equals("end1")){
                    Oreille.interrupt();
                    break;
                }
            }
            System.out.println("La connexion est finie");

        }



        public void run() {
            try {
                init();
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException | UserNotFoundException e) {
                System.err.println("Could not initialize.");
                e.printStackTrace();
            }
        }

    }




}
