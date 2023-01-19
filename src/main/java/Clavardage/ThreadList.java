package Clavardage;

import java.util.ArrayList;
import java.util.List;

public class ThreadList {
    private List<ServeurTCP.ClientHandler> clientHandler = new ArrayList<>();
    public void addThread(ServeurTCP.ClientHandler t){clientHandler.add(t);}
    public void delThread(ServeurTCP.ClientHandler t){clientHandler.remove(t);}
    public int findThread(String s){return  clientHandler.indexOf(s);}
    public ServeurTCP.ClientHandler getThread(int i){return clientHandler.get(i);}
    public int lengthListe(){
        return clientHandler.size();
    }
    public List<ServeurTCP.ClientHandler> getList(){return clientHandler;}
}

