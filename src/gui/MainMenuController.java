package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import network.MonopolyClient;
import network.MonopolyNetwork;
import network.MonopolyServer;
import java.util.Optional;



public class MainMenuController {
    @FXML public ImageView imageView;
    @FXML public TextArea usernameField;


    // Creates a new lobby as a host
    @FXML
    protected void createGame(ActionEvent event) {
        String username = usernameField.getText();
        if (!username.isEmpty() && !username.contains("\n")) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("lobby.fxml"));
                Parent root = (Parent) loader.load();

                //Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
                Scene scene = new Scene(root, 800, 600);

                Stage stage = (Stage) imageView.getScene().getWindow();

                // Initialize lobby
                LobbyController lobbyController = loader.getController();

                // Create a new server and client
                MonopolyServer monopolyServer = MonopolyServer.getInstance();
                //System.out.println(monopolyServer.toString());

                MonopolyClient monopolyClient = new MonopolyClient(lobbyController);
                monopolyClient.connect(MonopolyNetwork.ipAddress, username);

                // Switch scene to lobby
                stage.setScene(scene);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cannot Create New Game");
            alert.setHeaderText("Please enter a valid username");
            alert.showAndWait();
        }
    }

    @FXML
    protected void joinGame(ActionEvent event) {
        String username = usernameField.getText();
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Game Pin");
        dialog.setContentText("Please enter your pin:");
        Optional<String> ip = dialog.showAndWait();

        if (ip.isPresent() && username != null && !username.isEmpty() && !username.contains("\n")) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("lobby.fxml"));
                Parent root = (Parent) loader.load();
                //Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
                //Scene scene = new Scene(root, 1920, 1080);
                Scene scene = new Scene(root, 800, 600);

                Stage stage = (Stage) imageView.getScene().getWindow();

                // Initialize lobby
                LobbyController lobbyController = loader.getController();

                MonopolyClient monopolyClient = new MonopolyClient(lobbyController);
                monopolyClient.connect(ip.get(), username);

                // Check if pin exists
                if (monopolyClient.isConnected()) {
                    stage.setScene(scene);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Cannot Join Game");
                    alert.setHeaderText("Server is not found");
                    alert.showAndWait();
                }

            } catch (Exception e) {
                    e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cannot Join Game");
            alert.setHeaderText("Please enter a valid username and ip");
            alert.showAndWait();
        }
    }



    @FXML
    protected void optionsPressed(ActionEvent e) {
        try {
            Stage stage = (Stage) imageView.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("options.fxml"));
            stage.setScene(new Scene(root, Main.WIDTH, Main.HEIGHT));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    protected void howToPlayPressed(ActionEvent event) {
        try {
            Stage stage = (Stage) imageView.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("how_to_play.fxml"));
            stage.setScene(new Scene(root, Main.WIDTH, Main.HEIGHT));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
