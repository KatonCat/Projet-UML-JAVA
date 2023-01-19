package Clavardage;

import BDD.BDD;
import Connexion.Ecoute;
import ConnexionExceptions.UserNotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Date;


public class clientTCP {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private InetAddress ip;
    private Date date;

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
    }

    public void sendMessage(String msg) throws UserNotFoundException {
        out.println(msg);
        date = new Date();
        BDD.insert("CentralClass",ip.getHostAddress(),new Message(Ecoute.liste.getUserByAdd(ip).getUserName(),msg,new Timestamp(date.getTime())));
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