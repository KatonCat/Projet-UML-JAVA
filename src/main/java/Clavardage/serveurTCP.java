package Clavardage;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class serveurTCP {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private static boolean Etat;

    public static class ServEcoute extends Thread {
        final private Socket clientSocket;
        private BufferedReader in;
        final private PrintWriter out;
        int port;
        public ServEcoute(int port,Socket clientSocket, BufferedReader in ,PrintWriter out){
            this.port = port;
            this.clientSocket = clientSocket;
            this.in = in;
            this.out = out;
        }
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String msgRecu;

                while (Etat) {
                    try {
                        if ((msgRecu = in.readLine()) == null) break;

                        System.out.println(msgRecu);
                        if ("hello dude".equals(msgRecu)) {
                            out.println("hi mate");
                        }
                        if (msgRecu.equals("end1")) {
                            Etat=false;
                        }
                    } catch (IOException e) {
                        System.err.println("Could not read.");
                        e.printStackTrace();
                    }


                }

            }catch (IOException e) {
                System.err.println("Could not initialized socket reader.");
                e.printStackTrace();
            }
        }
    }



    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        Etat=true;
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ServEcoute Oreille = new ServEcoute(port,clientSocket,in,out);
        Oreille.start();
        Scanner entreeClavier = new Scanner(System.in);
        String Smsg;
        while (Etat) {
            Smsg = entreeClavier.nextLine();
            out.println(Smsg);
            if (Smsg.equals("end1")){
                Etat=false;
            }
        }
        System.out.println("La connexion est finie");
        Oreille.interrupt();

    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }




    public static void main(String[] args) throws IOException {
        serveurTCP server = new serveurTCP();
        server.start(1769);
        server.stop();
    }
}