package Clavardage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListOfClients {
    private List<ClientTCP> clients = new ArrayList<>();
    public void addClient(ClientTCP c){clients.add(c);}
    public void delClient(ClientTCP c){clients.remove(c);}
    public int findClient(String s){
        for(int i = 0; i < clients.size();i++)
        {
            if(clients.get(i).getName().equals(s)){
                return i;
            }
        }
        return -1;
    }

    public int lengthListe(){
        return clients.size();
    }
    public ClientTCP getClient(int i){return clients.get(i);}

    public void stopAll() throws IOException {
        for(int i = 0; i < clients.size();i++)
        {
            clients.get(i).sendMessage("end1");
            clients.get(i).stopConnexion();
        }
    }
    public void clear(){clients.clear();}
    public List<ClientTCP> getList(){return clients;}

}
