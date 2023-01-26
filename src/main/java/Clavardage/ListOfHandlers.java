package Clavardage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListOfHandlers {
    private ObservableList<ServeurTCP.ClientHandler> handlers = FXCollections.observableArrayList();
    public void addHandler(ServeurTCP.ClientHandler t){handlers.add(t);}
    public void delHandler(ServeurTCP.ClientHandler t){handlers.remove(t);}
    public int findHandler(String s){
        for(int i = 0; i < handlers.size();i++)
        {
            if(handlers.get(i).getAddress().getHostAddress().equals(s)){
                return i;
            }
        }
        return -1;
    }
    public ServeurTCP.ClientHandler getHandler(int i){return handlers.get(i);}
    public int lengthListe(){
        return handlers.size();
    }

    public void stopAll(){
        for(int i = 0; i < handlers.size();i++)
        {
            handlers.get(i).interrupt();
        }

    }

    public ServeurTCP.ClientHandler getLast(){return handlers.get(handlers.size()-1);}

    public ObservableList<ServeurTCP.ClientHandler> getList(){return handlers;}
}

