package Clavardage;

import Connexion.RemoteUser;

import java.util.ArrayList;
import java.util.List;

public class ListOfMessages {

    private List<Message> message = new ArrayList<>();

    public void addMsg(Message msg) {
        message.add(msg);
    }

    public void delMsg(Message msg) {
        message.remove(msg);
    }
}
