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
import java.util.List;

public class LobbyController {
    @FXML Label pinLabel;
    @FXML CheckBox allianceBox, speedDieBox, privateBox;
    @FXML Button startButton;
    @FXML GridPane playersPane;
    @FXML Label player0Label, player1Label, player2Label, player3Label, player4Label, player5Label;
    Label[] playerLabels;
    @FXML Button tokenButton0, tokenButton1, tokenButton2, tokenButton3, tokenButton4, tokenButton5,
        teamButton0, teamButton1, teamButton2, teamButton3, teamButton4, teamButton5;
    Button[] tokenButtons, teamButtons;

    private final int CAPACITY = 6;

    private MonopolyClient monopolyClient;

    static final Player.Token[] tokenList = {Player.Token.CAR, Player.Token.BATTLESHIP, Player.Token.TOP_HAT,
            Player.Token.SHOE, Player.Token.IRON, Player.Token.SCOTTISH_TERRIER, Player.Token.THIMBLE,
            Player.Token.WHEELBARROW};
    int tokenNow;
    int teamNow;

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
        //updateLobbyState(monopolyClient);
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
        this.tokenButtons = new Button[]{tokenButton0, tokenButton1, tokenButton2, tokenButton3, tokenButton4, tokenButton5};
        this.teamButtons = new Button[]{teamButton0, teamButton1, teamButton2, teamButton3, teamButton4, teamButton5};
        this.playerLabels = new Label[]{player0Label, player1Label, player2Label, player3Label, player4Label, player5Label};
        Platform.runLater(() -> {
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

            // Update player names, teams, and tokens
            ArrayList<Player> players = monopolyClient.getPlayers();

            int i;
            for (i = 0; i < players.size() && players.get(i) != null; i++) {
                    // player name
                    playerLabels[i].setText(players.get(i).getName());

                    // tokens
                    tokenButtons[i].setDisable(monopolyClient.getId() != players.get(i).getPlayerId());
                    tokenButtons[i].setText(players.get(i).getTokenName());
                    tokenButtons[i].setVisible(true);

                    // team numbers
                    if (!monopolyClient.getAlliance()) {
                        teamButtons[i].setVisible(false);
                    } else {
                        teamButtons[i].setVisible(true);
                        teamButtons[i].setDisable(monopolyClient.getId() != players.get(i).getPlayerId());

                        int teamNumber = players.get(i).getTeamNumber();
                        if (teamNumber != 0)
                            teamButtons[i].setText(Integer.toString(teamNumber));
                        else
                            teamButtons[i].setText("-");
                    }
            }
            for (; i < CAPACITY; i++) {
                playerLabels[i].setText("");
                tokenButtons[i].setVisible(false);
                teamButtons[i].setVisible(false);
            }
        });


    }

    @FXML
    protected void checkBoxUsed(ActionEvent e) {
        monopolyClient.setAlliance(allianceBox.isSelected());
        monopolyClient.setPrivateLobby(privateBox.isSelected());
        monopolyClient.setSpeedDie(speedDieBox.isSelected());

        monopolyClient.updateLobbyControllers();
    }

    @FXML
    protected void tokenButtonUsed(ActionEvent e) {
        // TODO: How to give new player a default token?

        for (int i = 0; i < monopolyClient.getPlayers().size(); i++) {
            if (e.getSource().equals(tokenButtons[i]))
                monopolyClient.getPlayers().get(i).setToken(nextToken());
        }

        monopolyClient.updateLobbyControllers();
    }

    @FXML
    protected void teamBoxUsed(ActionEvent e) {
        // TODO: Give all players to team 0 by default.
        // TODO: Also, dont allow starting the game if alliance is on but one of the players are in team 0

        // 0 : no team selected
        // 1, 2, 3: team numbers
        for (int i = 0; i < monopolyClient.getPlayers().size(); i++) {
            if (e.getSource().equals(teamButtons[i]))
                monopolyClient.getPlayers().get(i).setTeamNumber(nextTeam());
        }

        monopolyClient.updateLobbyControllers();
    }

    private Player.Token nextToken() {
        tokenNow = (tokenNow + 1) % tokenList.length;
        while (tokenExists(tokenList[tokenNow])) {
            tokenNow = (tokenNow + 1) % tokenList.length;
        }
        return tokenList[tokenNow];
    }

    private boolean tokenExists(Player.Token token) {
        List<Player> players = monopolyClient.getPlayers();
        for (Player p : players) {
            if (p.getToken() == token)
                return true;
        }
        return false;
    }

    private int nextTeam() {
        teamNow = (teamNow + 1) % 4;
        return teamNow;
    }
}


// TODO: replace house with classroom, hotel with lecture hall in the whole code
