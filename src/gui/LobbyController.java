package gui;

import entity.Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import network.MonopolyClient;
import network.MonopolyNetwork;

import java.util.ArrayList;

public class LobbyController {
    @FXML Label pinLabel;
    @FXML CheckBox allianceBox, speedDieBox, privateBox;
    @FXML Button startButton;
    @FXML GridPane playersPane;
    @FXML Label player1Label, player2Label, player3Label, player4Label, player5Label, player6Label;
    Label[] playerLabels;

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

        //updateLobbyState();
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

    public void updateLobbyState(MonopolyClient monopolyClient) {
        this.monopolyClient = monopolyClient;
        this.playerLabels = new Label[]{player1Label, player2Label, player3Label, player4Label, player5Label, player6Label};
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                // Update CheckBoxes
                boolean alliance = monopolyClient.getAlliance();
                boolean speedDie = monopolyClient.getSpeedDie();
                boolean privateLobby = monopolyClient.getPrivateLobby();

                boolean isHost = monopolyClient.getId() == 1;
                speedDieBox.setDisable(!isHost);
                allianceBox.setDisable(!isHost);
                privateBox.setDisable(!isHost);
                startButton.setDisable(!isHost);

                speedDieBox.setSelected(speedDie);
                allianceBox.setSelected(alliance);
                privateBox.setSelected(privateLobby);

                // Update pin label
                pinLabel.setText(MonopolyNetwork.ipAddress); // TODO: get this ip address from the server.

                // TODO: Update Other Labels
                ArrayList<Player> players = monopolyClient.getPlayers();
                for (int i = 0; i < 6; i++) {
                    if (i < players.size() && players.get(i) != null) {
                        playerLabels[i].setText(players.get(i).getName());
                    }
                }
            }
// ...
        });


    }

    @FXML
    protected void checkBoxUsed(ActionEvent e) {
        monopolyClient.setAlliance(allianceBox.isSelected());
        monopolyClient.setPrivateLobby(privateBox.isSelected());
        monopolyClient.setSpeedDie(speedDieBox.isSelected());

        monopolyClient.updateLobbyControllers();
    }
}


// TODO: replace house with classroom, hotel with lecture hall in the whole code
