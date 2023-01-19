package Clavardage;

import java.util.ArrayList;
import java.util.List;

public class ThreadList {
    private List<ServeurTCP.ClientHandler> clientHandler = new ArrayList<>();
    public void addThread(ServeurTCP.ClientHandler t){clientHandler.add(t);}
    public void delThread(ServeurTCP.ClientHandler t){clientHandler.remove(t);}
    //public int findThread(String s){return  thread.indexOf(s);}
    public List<ServeurTCP.ClientHandler> getThread(){return clientHandler;}
}

