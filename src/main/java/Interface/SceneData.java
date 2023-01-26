package Interface;


import Clavardage.ServeurTCP;
import Connexion.Ecoute;

public class SceneData {
    private Ecoute data1;
    private ServeurTCP data2;

    public SceneData(Ecoute data1, ServeurTCP data2) {
        this.data1 = data1;
        this.data2 = data2;
    }


    public Ecoute getData1() {
        return data1;
    }

    public ServeurTCP getData2() {
        return data2;
    }
}