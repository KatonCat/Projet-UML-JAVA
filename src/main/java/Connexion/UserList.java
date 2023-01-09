package Connexion;
import ConnexionExceptions.UserNotFoundException;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class UserList {

    private final List<RemoteUser> users = new ArrayList<>();

    public void addUser(RemoteUser user) {
        users.add(user);
    }

    public void delUser(RemoteUser user) {
        users.remove(user);
    }

    public RemoteUser getUserByAdd(InetAddress add) throws UserNotFoundException {
        int i;
        for (i = 0; i < users.size(); i++) {
            if ((users.get(i)).getAdd().equals(add))
                return users.get(i);
        }
        throw new UserNotFoundException("User not found");
    }

    public boolean verifyUserNamePresent(String userName) {
        int i;
        for (i = 0; i < users.size(); i++) {
            if ((users.get(i)).getUserName().equals(userName))
                return true;
        }
         return false;
    }
    public int lengthListe(){
        return users.size();
    }

    @Override
    public String toString() {
        return "UserList{" +
                "users=" + users +
                '}';
    }
}







