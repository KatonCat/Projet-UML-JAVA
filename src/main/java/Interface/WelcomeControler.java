package Interface;

import Clavardage.ServeurTCP;
import Connexion.ConnectionListener;
import Connexion.Connexion;
import Connexion.Ecoute;
import ConnexionExceptions.UserNotFoundException;
import DataBase.BDD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.SocketException;

public class WelcomeControler {
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private TextField userNameText;

    @FXML
    private Button connexionBotton;

    @FXML
    private Text messageToUser;

    private String userName ;
    public static boolean isValid =false;



    public void connexion(ActionEvent actionEvent) throws IOException, InterruptedException , UserNotFoundException {
        Connexion connexion = new Connexion();

        ConnectionListener listener = new ConnectionListener() {

            public void invalidID() {
                isValid = false;

            }

            @Override
            public void validID() {
                isValid = true;

            }
        };
        Ecoute ecoute;

        {
            try {
                ecoute = new Ecoute(connexion , listener);
            } catch (SocketException e) {
                throw new RuntimeException(e);
            }
        }
        ecoute.start();

        userName = userNameText.getText();
        connexion.verifyId(userName)  ;



        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (isValid) {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("mainWindow.fxml"));
            stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Home - "+connexion.getPseudo());
            scene = new Scene(fxmlLoader.load(),580, 340);
            stage.setScene(scene);
            stage.show();
            ServeurTCP server = new ServeurTCP(1769);
            server.start();

            stage.setUserData(new SceneData(ecoute ,server));
            BDD.createNewDatabase("CentralMessages.db");



        }
        else  {
            messageToUser.setText("Le nom d'utilisateur est deja utilisé veillez rééssayer");
            ecoute.interrupt();
            ecoute.stopSocket();


        }



    }

}