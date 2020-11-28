package gui;

import control.ActionLog;
import control.MonopolyGame;
import entity.Board;
import entity.Player;
import entity.dice.DiceResult;
import entity.property.Property;
import entity.tile.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class GameScreenController {
    private final int LOG_CAPACITY = 8;

    MonopolyGame game;

    @FXML Label label;

    @FXML Label die1;
    @FXML Label die2;

    @FXML BorderPane boardPane;
    @FXML GridPane grid0;
    @FXML GridPane grid1;
    @FXML GridPane grid2;
    @FXML GridPane grid3;

    @FXML Label log0, log1, log2, log3, log4, log5, log6, log7;

    @FXML Label player1Label, player2Label;

    @FXML
    protected void handleQuitButton(ActionEvent e) {
        try {
            Stage stage = (Stage) label.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("main_menu.fxml"));
            stage.setScene(new Scene(root, Main.WIDTH, Main.HEIGHT));
        } catch (Exception ignored) {}
    }

    @FXML
    protected void handleDiceButton(ActionEvent e) {
        // when multiplayer, make a new ActionEvent class for this and compare active player with the player clicked
        if (true) {
            DiceResult result = game.rollDice();
            die1.setText(Integer.toString(result.getFirstDieResult()));
            die2.setText(Integer.toString(result.getSecondDieResult()));

            game.processTurn();
        }
    }

    public void setupGame(String name) {
        try {
            ArrayList<Player> players = new ArrayList<Player>();
            game = new MonopolyGame(players);

            Player p1 = new Player(1, name, Player.Token.SHOE, 1);
            Player p2 = new Player(2, "Player2", Player.Token.TOP_HAT, 1);
            game.addPlayer(p1);
            game.addPlayer(p2);
            setupBoard();
        } catch (Exception ignored) {}
    }

    private void setupBoard() {
        // Set up Tiles
        ArrayList<Tile> tiles = game.getBoard().getTiles();

        GridPane[] grids = new GridPane[4];
        grids[0] = grid0;
        grids[1] = grid1;
        grids[2] = grid2;
        grids[3] = grid3;

        for (Tile tile : tiles) {
            int x, y; // grids[x].get(y)
            int location = tile.getTileId();

            // calculate x and y
            if (location < 12) {
                x = 0;
                y = 11 - location;
            } else if (location < 21) {
                x = 1;
                y = 20 - location;
            } else if (location < 33) {
                x = 2;
                y = location - 21;
            } else {
                x = 3;
                y = location - 33;
            }

            // get the type of the tile and put images

            if (tile instanceof JailTile) {
                ImageView imageView = new ImageView();
                File file = new File("models/jail_tile.png");
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);

                if (x % 2 == 0)
                    grids[x].add(imageView, y, 0);
                else
                    grids[x].add(imageView, 0, y);

            } else if (tile instanceof StartTile) {
                ImageView imageView = new ImageView();
                File file = new File("models/start_tile.png");
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);

                if (x % 2 == 0)
                    grids[x].add(imageView, y, 0);
                else
                    grids[x].add(imageView, 0, y);

            } else if (tile instanceof PropertyTile) {
                Property p = game.getBoard().getProperties().get(((PropertyTile) tile).getPropertyId());
                String name = p.getName();
                int price = p.getPrice();
                // TODO

            } else if (tile instanceof TaxTile) {
                int amount = ((TaxTile) tile).getAmount();
                BorderPane taxPane = new BorderPane();
                taxPane.setCenter(new Label("PAY"));
                taxPane.setBottom(new Label(Integer.toString(amount)));
                if (x % 2 == 0)
                        grids[x].add(taxPane, y, 0);
                else
                        grids[x].add(taxPane, 0, y);

            } else if (tile instanceof CardTile) {
                if (((CardTile) tile).getCardType() == CardTile.CardType.COMMUNITY_CHEST_CARD) {
                    ImageView imageView = new ImageView();
                    File file = new File("models/community_chest_tile.png");
                    Image image = new Image(file.toURI().toString());
                    imageView.setImage(image);

                    if (x % 2 == 0)
                        grids[x].add(imageView, y, 0);
                    else
                        grids[x].add(imageView, 0, y);

                } else if (((CardTile) tile).getCardType() == CardTile.CardType.CHANCE_CARD) {
                    ImageView imageView = new ImageView();
                    File file = new File("models/chance_tile.png");
                    Image image = new Image(file.toURI().toString());
                    imageView.setImage(image);

                    if (x % 2 == 0)
                        grids[x].add(imageView, y, 0);
                    else
                        grids[x].add(imageView, 0, y);
                }
            } else if (tile instanceof GoToJailTile) {
                ImageView imageView = new ImageView();
                File file = new File("models/goto_jail_tile.png");
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);

                if (x % 2 == 0)
                    grids[x].add(imageView, y, 0);
                else
                    grids[x].add(imageView, 0, y);

            } else if (tile instanceof FreeParkingTile) {
                ImageView imageView = new ImageView();
                File file = new File("models/freeparking_tile.png");
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);

                if (x % 2 == 0)
                    grids[x].add(imageView, y, 0);
                else
                    grids[x].add(imageView, 0, y);

            }
        }
        updateBoardState();
    }

    private void updateBoardState() {
        // Update player labels
        Player p1 = game.getPlayerController().getPlayers().get(0);
        Player p2 = game.getPlayerController().getPlayers().get(1);
        player1Label.setText("  " + p1.getName() + " - " + p1.getTokenName() + " - " + p1.getBalance());
        player2Label.setText("  " + p2.getName() + " - " + p2.getTokenName() + " - " + p2.getBalance());

        // Update Game Log
        ActionLog actionLog = MonopolyGame.getActionLog();
        Label[] logLabels = {log0, log1, log2, log3, log4, log5, log6, log7};
        for (int i = 0; i < LOG_CAPACITY; i++) {
            int logSize = actionLog.getNumActions();

            if (i >= logSize)
                logLabels[i].setText("");
            else
                logLabels[i].setText(actionLog.getLog().get(i + logSize - LOG_CAPACITY));

        }

        // TODO Draw Tokens
        ImageView token1 = new ImageView(new Image((new File("models/tokens/" + p2.getTokenName() + ".png")).toURI().toString()));
        ImageView token2 = new ImageView(new Image((new File("models/tokens/" + p2.getTokenName() + ".png")).toURI().toString()));
    }
}