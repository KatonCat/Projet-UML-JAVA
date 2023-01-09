import Connexion.Connexion;
import Connexion.Ecoute;


import ConnexionExceptions.UserNotFoundException;
import org.junit.Test;

import Connexion.Connexion;
import Connexion.Ecoute;
import Connexion.RemoteUser;

import static Connexion.Ecoute.liste;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class MaClasseDeTest {


    @Test
     public void addUserTest() throws UnknownHostException {
        InetAddress address = InetAddress.getByName("25.25.78.168");
        RemoteUser u1 = new RemoteUser("toto" ,  address);

        assert !liste.verifyUserNamePresent("toto");
        try {
            liste.getUserByAdd(address);
            assert false;
        } catch (UserNotFoundException e) {
            System.out.println("OK: user not in list");
        }

        liste.addUser(u1);
        try {
            RemoteUser u2 = liste.getUserByAdd(address);
            assert u1.equals(u2);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("la liste est "+liste.toString());

        assert liste.verifyUserNamePresent("toto");
        try {
        assert liste.getUserByAdd(address).equals(u1);
        }
        catch(UserNotFoundException e)
        {
            e.printStackTrace();

        }

    }
}
