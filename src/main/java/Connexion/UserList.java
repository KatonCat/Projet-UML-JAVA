package Connexion;
import ConnexionExceptions.UserNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class UserList {
    //private final List<RemoteUser> users = new ArrayList<>();
    private final ObservableList<RemoteUser> users = FXCollections.observableArrayList();

    public void addUser(RemoteUser user) { users.add(user); }

    public void delUser(RemoteUser user) {
        users.remove(user);
    }

    public int lengthListe(){
        return users.size();
    }

    public String getid (int i) { return (users.get(i)).getUserName(); }

    public RemoteUser getUserByAdd(InetAddress add) throws UserNotFoundException {
        int i;
        for (i = 0; i < users.size(); i++) {
            if ((users.get(i)).getAdd().equals(add))
                return users.get(i);
        }
        throw new UserNotFoundException("User not found");
    }

    public ObservableList<RemoteUser>  getUsers (){
        return this.users;
    }

    public boolean verifyUserNamePresent(String userName) {
        int i;
        for (i = 0; i < users.size(); i++) {
            if ((users.get(i)).getUserName().equals(userName))
                return true;
        }
         return false;
    }

    public void clear(){users.clear();}

    public List<String> getids() throws UserNotFoundException{
        List<String> userids = new ArrayList<>();
        if (!users.isEmpty() ){
            //System.out.println("hhhhh");
           for(int i = 0; i < users.size(); i++)
                userids.add(((users.get(i)).getUserName()));
           return userids;
        }
        else
        throw new UserNotFoundException("the list is empty");
    }

    @Override
    public String toString() {
        return "UserList{" +
                "users=" + users +
                '}';
    }




}







