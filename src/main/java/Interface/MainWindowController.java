package Interface;

import Clavardage.*;
import Connexion.Ecoute;
import Connexion.RemoteUser;
import Connexion.UDP;
import Connexion.UserList;
import ConnexionExceptions.UserNotFoundException;
import DataBase.BDD;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;


import javafx.scene.control.*;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class MainWindowController {
    public static ObservableList<RemoteUser> items;
    public static ObservableList<Message> msgs;
    private static RemoteUser selectedUser;
    private static MainWindowController instance;
    private StartSession session;

    private ServeurTCP.ClientHandler handler;

    @FXML
    private TextField messageUtil;

    @FXML
    private TableColumn<RemoteUser, String> onlineUsersTable;

    @FXML
    private TableView<RemoteUser> onlineUsersList;

    @FXML
    private Button sendButton;

    @FXML
    private TableView<Message> messagesTable;

    @FXML
    private TextField userNameFieled;

    @FXML
    private TextField newUserName;

    @FXML
    private TableColumn<Message, String> messages;

    private final SimpleDateFormat format = new SimpleDateFormat("HH:mm dd/MM/yy");

    @FXML
    private void initialize() {
        instance = this;
        items = FXCollections.observableArrayList();
        onlineUsersList.setItems(items);
        onlineUsersTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserName()));
        onlineUsersList.setItems(Ecoute.liste.getUsers());
        ServeurTCP.handlerList.getList().addListener((ListChangeListener<ServeurTCP.ClientHandler>) changeNew -> {
            while (changeNew.next()) {
                if (changeNew.wasAdded()) {
                    ServeurTCP.ClientHandler lastHandler = ServeurTCP.handlerList.getLast();
                    InetAddress addr=lastHandler.getAddress();
                    try {
                        String pseudo = Ecoute.liste.getUserByAdd(addr).getUserName();
                        String tableName =pseudo+addr.getHostAddress().replace(".","_");

                        BDD.createNewTable("CentralMessages", tableName );

                        lastHandler.listeMsg.getMessage().addListener((ListChangeListener<Message>) change -> {

                            while (change.next()) {
                                if (change.wasAdded()) {

                                    if(!lastHandler.listeMsg.getLast().getUserName().equals("Moi")) {
                                        BDD.insert("CentralMessages", tableName, lastHandler.listeMsg.getLast());
                                    }

                                }
                            }
                        });

                    } catch (UserNotFoundException e) {
                        e.printStackTrace();
                    }
                }

            }
        });


        onlineUsersList.setOnMouseClicked(event ->{
           selectedUser = onlineUsersList.getSelectionModel().getSelectedItem();
           String addr = selectedUser.getAdd().getHostAddress();
                if (selectedUser != null){
                    sendButton.setVisible(true);
                    messageUtil.setVisible(true);
                    String tableName=selectedUser.getUserName()+selectedUser.getAdd().getHostAddress().replace(".","_");
                    if (ServeurTCP.clientList.findClient(addr)==-1 && ServeurTCP.sessionList.findSession(addr)==-1) {
                        BDD.createNewTable("CentralMessages" , tableName);
                        session = new StartSession(selectedUser.getAdd());
                        session.start();
                        setTextVisible();
                        setListener(tableName, session.listeMsg);

                    }
                    else if(ServeurTCP.sessionList.findSession(addr)!=-1){
                        session = ServeurTCP.sessionList.getSession(ServeurTCP.sessionList.findSession(selectedUser.getAdd().getHostAddress()));
                        setTextVisible();

                    }
                    else{
                        handler = ServeurTCP.handlerList.getHandler(ServeurTCP.handlerList.findHandler(addr));
                        System.out.println("C'est bon");
                        handler.listeMsg = BDD.select("CentralMessages", selectedUser.getUserName()+selectedUser.getAdd().getHostAddress().replace(".","_"));
                        msgs = FXCollections.observableArrayList();
                        messagesTable.setItems(msgs);
                        messages.setCellValueFactory(cellData -> {
                            try {
                                return new SimpleStringProperty(
                                        !cellData.getValue().getUserName().equals("Moi")
                                                ? Ecoute.liste.getUserNameByAdd(cellData.getValue().getUserName())+ "   " + cellData.getValue().getMsg() + "   " + format.format(cellData.getValue().getDateTS())
                                                : cellData.getValue().getUserName() + "   " + cellData.getValue().getMsg() + "   " + format.format(cellData.getValue().getDateTS())
                                );
                            } catch (UserNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        });

                        messagesTable.setItems(handler.listeMsg.getMessage());
                        setListener(tableName, handler.listeMsg);


                    }


                }

        });

        }

    private void setListener(String tableName, ListOfMessages listeMsg) {
        listeMsg.getMessage().addListener((ListChangeListener<Message>) change -> {

            while (change.next()) {
                if (change.wasAdded()) {

                    if(!listeMsg.getLast().getUserName().equals("Moi")) {
                        BDD.insert("CentralMessages", tableName, listeMsg.getLast());
                    }

                }
            }
        });
    }

    private void closeAll() throws IOException {
        SceneData sd = (SceneData) App.getStage().getUserData();
        Ecoute ecoute = sd.getData1();
        ServeurTCP server = sd.getData2();
        UDP.broadcast("end" , InetAddress.getByName("255.255.255.255"));
        ecoute.interrupt();
        ecoute.stopSocket();
        Ecoute.liste.clear();
        server.close();
    }

    private void setTextVisible() {
        session.listeMsg = BDD.select("CentralMessages", selectedUser.getUserName()+selectedUser.getAdd().getHostAddress().replace(".","_"));
        msgs = FXCollections.observableArrayList();
        messagesTable.setItems(msgs);
        messages.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(
                        !cellData.getValue().getUserName().equals("Moi")
                                ? Ecoute.liste.getUserNameByAdd(cellData.getValue().getUserName()) + "   " + cellData.getValue().getMsg() + "   " + format.format(cellData.getValue().getDateTS())
                                : cellData.getValue().getUserName() + "   " + cellData.getValue().getMsg() + "   " + format.format(cellData.getValue().getDateTS())
                );
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        messagesTable.setItems(session.listeMsg.getMessage());
    }

    @FXML
    void setVisible() {
        userNameFieled.setVisible(true);
    }

    @FXML
    void changeUsername() throws IOException {
        String userName=newUserName.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invalid Username");
        alert.setHeaderText("Pseudo interdit.");
        if (!userName.matches("[a-zA-Z0-9_]*")) {
            alert.setContentText("Le nom d'utilisateur contient des carractères spéciaux");
            alert.showAndWait();
        }
        else if(!userName.matches("^[a-zA-Z].*")){
            alert.setContentText("Pour le bon fonctionnement de la BDD le nom d'utilisateur doit commencer par une lettre ");
            alert.showAndWait();
        }
        else if (userName.length() > 15) {
            alert.setContentText("Le nom d'utilisateur est trop long");
            alert.showAndWait();
        }
        else {
            SceneData sd = (SceneData) App.getStage().getUserData();
            Ecoute ecoute = sd.getData1();
            Ecoute.liste = new UserList();
            ecoute.getConnexion().changePseudo(userName);
            App.getStage().setTitle("home -" + userName);
            newUserName.clear();
        }
    }

    @FXML
    void logout() throws IOException {
        closeAll();
        App.getStage().close();

    }

    @FXML
    void SendMessage(){
        int  index =ServeurTCP.clientList.findClient(selectedUser.getAdd().getHostAddress());
        ClientTCP client;
        String username =selectedUser.getUserName();
        String message = messageUtil.getText();
        if(index==-1){
            client = session.getClient();
            session.listeMsg.addMsg(new Message("Moi", message,new Timestamp(System.currentTimeMillis())));
        }
        else{

            client = ServeurTCP.clientList.getClient(index);
            handler.listeMsg.addMsg(new Message("Moi", message,new Timestamp(System.currentTimeMillis())));
        }


        client.sendMessage(message);
        messageUtil.clear();
        BDD.insert("CentralMessages",username+selectedUser.getAdd().getHostAddress().replace(".","_"),new Message("Moi", message,new Timestamp(System.currentTimeMillis())));


    }


}