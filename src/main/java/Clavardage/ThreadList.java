package Clavardage;

import java.util.ArrayList;
import java.util.List;

public class ThreadList {
    private List<Thread> threads = new ArrayList<>();
    public void addThread(ServeurTCP.ClientHandler t){threads.add(t);}
    public void delThread(ServeurTCP.ClientHandler t){threads.remove(t);}
    public int findThread(String s){return  threads.indexOf(s);}
    public Thread getThread(int i){return threads.get(i);}
    public int lengthListe(){
        return threads.size();
    }
    public List<Thread> getList(){return threads;}
}

