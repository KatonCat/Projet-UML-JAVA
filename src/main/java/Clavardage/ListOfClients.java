package Clavardage;

import java.util.ArrayList;
import java.util.List;

public class ListOfClients {
    private List<clientTCP> clients = new ArrayList<>();
    public void addClient(clientTCP c){clients.add(c);}
    public void delClient(clientTCP c){clients.remove(c);}
    public int findClient(String s){return  clients.indexOf(s);}
    public int lengthListe(){
        return clients.size();
    }
    public clientTCP getClient(int i){return clients.get(i);}
    public List<clientTCP> getList(){return clients;}

}
