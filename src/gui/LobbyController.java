package gui;

import entity.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import network.MonopolyClient;

import java.awt.event.ActionEvent;
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
        // TODO: updates the labels from the players.
    }

}
