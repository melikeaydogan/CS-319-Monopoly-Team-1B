package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MainMenuController {
    @FXML public ImageView imageView;
    @FXML public TextArea usernameField;

    @FXML protected void joinGame(ActionEvent event) {
        try {
            String username = usernameField.getText();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("game_screen.fxml"));
            Parent root = (Parent) loader.load();

            Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);

            GameScreenController gameScreenController = loader.getController();
            gameScreenController.setUsername(username);
            gameScreenController.setNumberOfPlayers(3);

            Stage stage = (Stage) imageView.getScene().getWindow();

            stage.setScene(scene);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML protected void howToPlayPressed(ActionEvent event) {
        try {
            Stage stage = (Stage) imageView.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("how_to_play.fxml"));
            stage.setScene(new Scene(root, Main.WIDTH, Main.HEIGHT));
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
