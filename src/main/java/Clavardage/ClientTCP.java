package Clavardage;

import Connexion.Ecoute;
import ConnexionExceptions.UserNotFoundException;
import DataBase.BDD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Timestamp;


public class ClientTCP {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private InetAddress ip;

    public void startConnexion(InetAddress ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.ip=ip;
    }

    public void getConnexion(Socket client) throws IOException {
        this.clientSocket = client;
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.ip=client.getInetAddress();
    }

    public void sendMessage(String msg) {
        out.println(msg);
    }

    public String rcvMessage() throws IOException{return in.readLine();}

    public void stopConnexion() throws IOException {
        clientSocket.close();
        in.close();
        out.close();
    }
    public String getName(){return this.ip.getHostAddress();}
}