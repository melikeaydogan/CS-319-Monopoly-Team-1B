package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main_menu.fxml"));
        primaryStage.setTitle("Monopoly Bilkent Edition™");
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> Platform.exit());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
