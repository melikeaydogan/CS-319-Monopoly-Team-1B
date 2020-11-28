package gui;

import control.MonopolyGame;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GameScreenController {
    private int numberOfPlayers;
    private String username;
    @FXML Label temp;


    public GameScreenController() {
        numberOfPlayers = 2;
        username = "player";
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        this.temp.setText(username);
    }
}
