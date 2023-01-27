package Connexion;

import java.io.IOException;
import java.net.InetAddress;

public class Connexion{



    private String pseudo ;

    public void verifyId(String str) throws IOException {

        UDP.broadcast(str , InetAddress.getByName("255.255.255.255"));
        this.pseudo = str;

    }

    public void changePseudo(String str) throws IOException {
        UDP.broadcast("end" , InetAddress.getByName("255.255.255.255"));
        UDP.broadcast(str , InetAddress.getByName("255.255.255.255"));
        this.pseudo = str;
    }

    public String getPseudo() {
        return this.pseudo;
    }
}
