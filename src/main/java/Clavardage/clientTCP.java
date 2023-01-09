package Clavardage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


public class clientTCP {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnexion(InetAddress ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void sendMessage(String msg) {
        out.println(msg);
    }
    public String rcvMessage() throws IOException{
        return in.readLine();
    }

    public void stopConnexion() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}