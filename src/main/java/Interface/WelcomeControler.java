package Interface;

import Clavardage.ServeurTCP;
import Connexion.ConnectionListener;
import Connexion.Connexion;
import Connexion.Ecoute;
import Connexion.UDP;
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
import java.net.InetAddress;
import java.net.SocketException;

public class WelcomeControler {
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private TextField userNameText;


    @FXML
    private Text messageToUser;

    private String userName ;
    public static boolean isValid =false;



    public void connexion(ActionEvent actionEvent) throws IOException, InterruptedException , UserNotFoundException {
        userName = userNameText.getText();
        if (!userName.matches("[a-zA-Z0-9_]*")) {
            messageToUser.setText("Le nom d'utilisateur contient des carractères spéciaux");
        }
        else if(!userName.matches("^[a-zA-Z].*")){
            messageToUser.setText("Pour le bon fonctionnement de la BDD le nom d'utilisateur doit commencer par une lettre ");
        }
        else if (userName.length() > 15) {
            messageToUser.setText("Le nom d'utilisateur est trop long");
        }
        else {


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
                    ecoute = new Ecoute(connexion, listener);
                } catch (SocketException e) {
                    throw new RuntimeException(e);
                }
            }
            ecoute.start();


            connexion.verifyId(userName);


            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (isValid) {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("mainWindow.fxml"));
                stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setTitle("Home - " + connexion.getPseudo());
                scene = new Scene(fxmlLoader.load(), 580, 340);
                stage.setScene(scene);
                stage.show();
                ServeurTCP server = new ServeurTCP(1769);
                server.start();

                stage.setUserData(new SceneData(ecoute, server));
                BDD.createNewDatabase("CentralMessages.db");

                stage.setOnCloseRequest(event -> {
                    try {
                        SceneData sd = (SceneData) App.getStage().getUserData();
                        UDP.broadcast("end", InetAddress.getByName("255.255.255.255"));
                        ecoute.interrupt();
                        ecoute.stopSocket();
                        Ecoute.liste.clear();
                        server.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });


            } else {
                messageToUser.setText("Le nom d'utilisateur est deja utilisé veillez rééssayer");
                ecoute.interrupt();
                ecoute.stopSocket();


            }


        }
    }

}