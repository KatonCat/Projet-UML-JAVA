import java.io.IOException;
import java.net.InetAddress;

import Clavardage.MultiServeurTCP;
import Clavardage.StartSession;

public class TestClient {
    public static void main(String args[]) throws IOException{
        StartSession.StartSession(InetAddress.getByName("127.0.0.1"));
    }
}
