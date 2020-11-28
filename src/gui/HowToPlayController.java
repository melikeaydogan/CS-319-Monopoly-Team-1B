package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class HowToPlayController {
    @FXML public TextArea textArea;

    @FXML
    protected void backButtonPressed(ActionEvent event) {
        try {
            Stage stage = (Stage) textArea.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("main_menu.fxml"));
            stage.setScene(new Scene(root, Main.WIDTH, Main.HEIGHT));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
