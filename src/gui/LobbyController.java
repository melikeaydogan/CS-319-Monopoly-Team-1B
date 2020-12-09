package gui;

import entity.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class LobbyController {
    @FXML Label pinLabel;
    @FXML CheckBox allianceBox, speedDieBox, privateBox;
    @FXML Button startButton;
    @FXML GridPane playersPane;

    private final int CAPACITY = 6;

    private ArrayList<String> names;
    private ArrayList<Player.Token> tokens;
    private ArrayList<Integer> teams;

    private int allianceEnabled, speedDieEnabled, privateLobby;
    private String pin;

    @FXML
    protected void startButtonPressed(ActionEvent e) {

    }

    public void setUpLobby(String hostName) {
        pin = "312312";
        names = new ArrayList<>();
        tokens = new ArrayList<>();
        teams = new ArrayList<>();

        names.add(hostName);
        updateLobbyState();
    }

    public boolean isFull() {
        return names.size() < CAPACITY;
    }

    public void playerJoined(String name) {
        if (!isFull()) {
            names.add(name);
            updateLobby();
        }
    }

    public void playerLeft(String name) {
        int i = names.indexOf(name);
        names.remove(i);
        teams.remove(i);
        tokens.remove(i);
        redrawPlayers();
    }

    public void

    public void updateLobby() {
        // Insert new children according to data
        for (int i = 0; i < names.size(); i++) {
            int loc = i + 1;
            String name = names.get(i);

            playersPane.add();

        }
    }

}
