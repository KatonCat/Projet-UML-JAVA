package Connexion;

import java.io.IOException;
import java.net.InetAddress;

public class Connexion{



    private String pseudo ;

    public void verifyId(String str) throws IOException {
        //Scanner sc = new Scanner(System.in);
        //System.out.println("choose id");
        //String str = sc.next();
        UDP.broadcast(str , InetAddress.getByName("255.255.255.255"));
        this.pseudo = str;
        //System.out.println("changing the pseudo");
        //UDP.broadcast("toto", InetAddress.getByName("255.255.255.255"));
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
