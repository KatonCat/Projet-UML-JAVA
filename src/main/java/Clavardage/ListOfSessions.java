package Clavardage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListOfSessions {
    private List<StartSession> sessions = new ArrayList<>();
    public void addSession(StartSession s){sessions.add(s);}
    public void delSession(StartSession s){sessions.remove(s);}
    public int findSession(String s){
        for(int i = 0; i < sessions.size();i++)
        {
            if(sessions.get(i).getAddress().getHostAddress().equals(s)){
                return i;
            }
        }
        return -1;
    }

    public int lengthListe(){
        return sessions.size();
    }
    public StartSession getSession(int i){return sessions.get(i);}

    public void stopAll() throws IOException {
        for(int i = 0; i < sessions.size();i++)
        {
            sessions.get(i).interrupt();
            sessions.get(i).getClient().sendMessage("end1");
            sessions.get(i).getClient().stopConnexion();
        }}
    public void clear(){sessions.clear();}
    public List<StartSession> getList(){return sessions;}

}
