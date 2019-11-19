package sample;

import ClientSide.ClientMainServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    private static ClientMainServer client;
    private static Controller controller;

    public static Controller getController() {
        return controller;
    }

    public static ClientMainServer getClient() {
        return client;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("sample.fxml").openStream());
        controller = (Controller) fxmlLoader.getController();
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        primaryStage.setTitle("Chat");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        client = new ClientMainServer("localhost", 3345);
        launch(args);

    }

}
