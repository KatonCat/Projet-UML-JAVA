package Clavardage;

import BDD.CreateBDD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MultiServeurTCP {
    private ServerSocket serverSocket;

    public void start(int port) throws IOException {
        CreateBDD.createNewDatabase("CentralMessages.db");
        serverSocket = new ServerSocket(port);
        while(true) {
            new ClientHandler(serverSocket.accept()).start();
        }

    }
    public void stop() throws IOException {
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

            public ServEcoute(Socket clientSocket, PrintWriter out) {
                this.clientSocket = clientSocket;
                this.out = out;
            }

            public void run() {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String msgRecu;

                    while (!this.isInterrupted()) {

                        if ((msgRecu = in.readLine()) == null) break;


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



        public void init() throws IOException {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ServEcoute Oreille = new ServEcoute(clientSocket,out);
            Oreille.start();
            Scanner entreeClavier = new Scanner(System.in);
            String Smsg;
            while (!Oreille.isInterrupted()) {
                Smsg = entreeClavier.nextLine();
                 out.println(Smsg);
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
            } catch (IOException e) {
                System.err.println("Could not initialize.");
                e.printStackTrace();
            }
        }

    }


}
