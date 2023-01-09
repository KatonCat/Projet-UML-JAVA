package Clavardage;

import java.sql.Timestamp;

public class Message {

    private String userName;

    private String Texte ;

    private Timestamp Date;

    public Message (String userName , String Texte, Timestamp Date){
        this.userName = userName;
        this.Texte = Texte;
        this.Date = Date;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getMsg() {

        return this.Texte;
    }

    public Timestamp getDateTS(){
        return this.Date;
    }

    //faire setteurs


}