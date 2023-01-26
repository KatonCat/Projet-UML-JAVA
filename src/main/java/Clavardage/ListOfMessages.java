package Clavardage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class ListOfMessages {

    private ObservableList<Message> message =  FXCollections.observableArrayList();

    public void addMsg(Message msg) {
        synchronized (message) {message.add(msg);
        }}

    public void delMsg(Message msg) {
        synchronized (message) {message.remove(msg);
        }}

    public void clear(){message.clear();}

    public Message getMessage(int i){return message.get(i);}

    public Message getLast(){return message.get(message.size()-1);}

    public ObservableList<Message> getMessage() {
        return message;
    }


}
