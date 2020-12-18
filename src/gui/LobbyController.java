package gui;

import entity.Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
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
    private final int TEAM_MAX = 3;

    private MonopolyClient monopolyClient;

    static final Player.Token[] tokenList = {Player.Token.NONE, Player.Token.CAR, Player.Token.BATTLESHIP, Player.Token.TOP_HAT,
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
        // TODO:  if ((alliance is on && one players has team 0) || (one player has token NONE))
        //              Dont allow starting the game.

        monopolyClient.sendStartGameCommand();
    }

    public void startGame() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game_screen.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root, 1920, 1080);
            Stage stage = (Stage) pinLabel.getScene().getWindow();

            // Initialize Game
            GameScreenController gameScreenController = loader.getController();
            monopolyClient.setupMonopolyGame(gameScreenController);

            // Switch Scene
            Platform.runLater(() -> {
                stage.setScene(scene);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUpLobby(MonopolyClient monopolyClient) {
        tokenNow = 0;
        teamNow = 0;
        //updateLobbyState(monopolyClient);
    }

    public boolean isFull() {
        return !(monopolyClient.getPlayers().size() < CAPACITY);
    }

    @FXML
    protected void leaveButtonPressed(ActionEvent e) {
        if (monopolyClient.getId() == 0) {
            monopolyClient.sendEndLobby();
        } else {
            monopolyClient.sendLeftLobby(monopolyClient.getId());
            monopolyClient.disconnect();
            leaveLobby();
        }
    }

    public void leaveLobby() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main_menu.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
            Stage stage = (Stage) pinLabel.getScene().getWindow();

            GameScreenController gameScreenController = loader.getController();
            monopolyClient.setupMonopolyGame(gameScreenController);

            // Switch Scene
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

            boolean isHost = monopolyClient.getId() == 0;
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
        for (int i = 0; i < monopolyClient.getPlayers().size(); i++) {
            if (e.getSource().equals(tokenButtons[i]))
                monopolyClient.getPlayers().get(i).setToken(nextToken());
        }

        monopolyClient.updateLobbyControllers();
    }

    @FXML
    protected void teamBoxUsed(ActionEvent e) {
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
        while (tokenNow == 0 || tokenExists(tokenList[tokenNow])) {
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
        teamNow = (teamNow + 1) % (TEAM_MAX + 1);
        return teamNow;
    }
}


// TODO: replace house with classroom, hotel with lecture hall in the whole code
