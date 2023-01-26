package Interface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {
    static Scene scene ;
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {

        this.stage= primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("welcome.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 580, 340);
        primaryStage.setTitle("Clavardage");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public static Stage getStage() {
        return stage;
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getClassLoader().getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    public static void main(String[] args) {
        launch(args);
    }


}