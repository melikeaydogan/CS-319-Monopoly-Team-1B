package gui;

import entity.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import network.MonopolyClient;

import java.util.ArrayList;

public class LobbyController {
    @FXML Label pinLabel;
    @FXML CheckBox allianceBox, speedDieBox, privateBox;
    @FXML Button startButton;
    @FXML GridPane playersPane;

    private final int CAPACITY = 6;

    private MonopolyClient monopolyClient;

    // To get the users from the client:
    // players = monopolyClient.getPlayers()

    // To check if the user is host:
    // monopolyClient.getId() == 0

    @FXML
    protected void startButtonPressed(ActionEvent e) {
        // monopolyClient.startGame()
    }

    public void startGame() {
        // switch to game screen
    }

    public void setUpLobby(MonopolyClient monopolyClient) {
        this.monopolyClient = monopolyClient;
        updateLobbyState();
    }

    public boolean isFull() {
        return !(monopolyClient.getPlayers().size() < CAPACITY);
    }

    @FXML
    protected void leaveButtonPressed(String name) {
        // TODO: Terminate the client,
        //  if the player is host stop the server
        //  else, the server is still on.
    }

    public void updateLobbyState() {
        // updateCheckBoxes
        boolean alliance = monopolyClient.getAlliance();
        boolean speedDie = monopolyClient.getSpeedDie();
        boolean privateLobby = monopolyClient.getPrivateLobby();

        boolean isHost = monopolyClient.getId() == 0;
        speedDieBox.setDisable(!isHost);
        allianceBox.setDisable(!isHost);
        privateBox.setDisable(!isHost);

        speedDieBox.setSelected(speedDie);
        allianceBox.setSelected(alliance);
        privateBox.setSelected(privateLobby);

        // TODO: Update Labels and others
    }

    @FXML
    protected void checkBoxUsed(ActionEvent e) {
        monopolyClient.setAlliance(allianceBox.isSelected());
        monopolyClient.setPrivateLobby(privateBox.isSelected());
        monopolyClient.setSpeedDie(speedDieBox.isSelected());
    }
}


// TODO: replace house with classroom, hotel with lecture hall in the whole code
