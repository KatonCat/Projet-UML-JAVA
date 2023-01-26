package Clavardage;

import Connexion.Ecoute;
import ConnexionExceptions.UserNotFoundException;
import java.io.IOException;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;

public class ServeurTCP extends Thread{
    private ServerSocket serverSocket;
    private final int port;
    public ServeurTCP(int port){this.port = port;}
    public static ListOfHandlers handlerList= new ListOfHandlers();
    public static ListOfClients clientList = new ListOfClients();
    public static ListOfSessions sessionList = new ListOfSessions();
    public void run(){
        ClientHandler handler;

        try {
            serverSocket = new ServerSocket(port);
            while(!this.isInterrupted()) {
                handler = new ClientHandler(serverSocket.accept());
                handler.start();
                //System.out.println("setname reussi");
                handlerList.addHandler(handler);
                //System.out.println("add reussi");

            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not create the server n/or accept.");
        }
    }

    public void close() throws IOException {
        handlerList.stopAll();
        System.out.println("handler ok");
        clientList.stopAll();
        System.out.println("client ok");
        sessionList.stopAll();
        System.out.println("session ok");
        serverSocket.close();
        System.out.println("server ok");
    }

    public static class ClientHandler extends Thread{
        private final Socket clientSocket;
        private final ClientTCP client = new ClientTCP();
        private final InetAddress addr;

        public ListOfMessages listeMsg= new ListOfMessages();


        public ClientHandler(Socket socket) throws IOException {
            this.clientSocket = socket;
            this.client.getConnexion(this.clientSocket);
            clientList.addClient(this.client);
            this.addr=clientSocket.getInetAddress();
        }
        public ClientTCP getClient(){
            return this.client;
        }

        public InetAddress getAddress(){return this.addr;}

        public void init() throws IOException, UserNotFoundException {

            String MsgRecu;
            MsgRecu = client.rcvMessage();
            String pseudo = Ecoute.liste.getUserByAdd(addr).getUserName();
            if ("hello dude".equals(MsgRecu)) {
                client.sendMessage("hi mate");
            }
            MsgRecu = client.rcvMessage();
            while (!this.isInterrupted()) {

                System.out.println(MsgRecu);
                MsgRecu = client.rcvMessage();
                if(MsgRecu.equals("end1")) {
                    this.interrupt();
                    System.out.println("La connexion est finie, veuillez appuyer sur entrée");
                    break;
                }
                listeMsg.addMsg(new Message(pseudo,MsgRecu,new Timestamp(System.currentTimeMillis())));

            }

            System.out.println("La connexion est finie");

        }



        public void run() {
            try {
                init();
                client.stopConnexion();

            } catch (IOException | UserNotFoundException e) {
                System.err.println("Could not initialize.");
                e.printStackTrace();
            }
            System.out.println("yep");
            if(clientList.findClient(client.getName())!=-1)
                clientList.delClient(client);
        }

    }




}
