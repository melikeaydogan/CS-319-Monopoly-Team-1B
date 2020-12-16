package gui;

import com.esotericsoftware.kryonet.Connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import network.MonopolyClient;
import network.MonopolyNetwork;
import network.MonopolyServer;

public class MainMenuController {
    @FXML public ImageView imageView;
    @FXML public TextArea usernameField;


    // Creates a new lobby as a host
    @FXML
    protected void createGame(ActionEvent event) {
        try {
            String username = usernameField.getText();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("lobby.fxml"));
            Parent root = (Parent) loader.load();

            //Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
            Scene scene = new Scene(root, 800, 600);

            Stage stage = (Stage) imageView.getScene().getWindow();

            // Initialize lobby
            LobbyController lobbyController = loader.getController();

            // Create a new server and client
            MonopolyServer monopolyServer = MonopolyServer.getInstance();
            System.out.println(monopolyServer.toString());

            MonopolyClient monopolyClient = new MonopolyClient(lobbyController);
            monopolyClient.connect(MonopolyNetwork.ipAddress, username);

            // Setup the lobby
            lobbyController.setUpLobby(monopolyClient);

            // Switch scene to lobby
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void joinGame(ActionEvent event) {
        String username = usernameField.getText();

        // TODO: ask for an ip address
        String ip = "139.179.200.153";

        if (true) { // TODO: Check if the pin exists
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("lobby.fxml"));
                Parent root = (Parent) loader.load();
                //Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
                Scene scene = new Scene(root, 1920, 1080);

                Stage stage = (Stage) imageView.getScene().getWindow();

                // Initialize lobby
                LobbyController lobbyController = loader.getController();

                MonopolyClient monopolyClient = new MonopolyClient(lobbyController);
                monopolyClient.connect(ip, username);

                lobbyController.setUpLobby(monopolyClient);

                // Switch scene to lobby
                stage.setX(0);
                stage.setY(0);
                stage.setMaximized(true);
                stage.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // TODO: Create an error dialog saying that server is not found
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
