package gui;

import control.ActionLog;
import control.MonopolyGame;
import entity.Player;
import entity.dice.DiceResult;
import entity.property.Building;
import entity.property.Property;
import entity.tile.*;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

public class GameScreenController {
    private final int LOG_CAPACITY = 8;

    MonopolyGame game;

    @FXML Label die1, die2;

    @FXML BorderPane boardPane;
    @FXML GridPane grid0, grid1, grid2, grid3;
    GridPane[] grids;

    @FXML Label log0, log1, log2, log3, log4, log5, log6, log7;
    @FXML Label player1Label, player2Label;

    @FXML Text prompt, playerTurn;

    @FXML Button yesButton, noButton;

    @FXML
    protected void handleQuitButton(ActionEvent e) {
        try {
            game.stopGame();
            Stage stage = (Stage) die1.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("main_menu.fxml"));
            stage.setScene(new Scene(root, Main.WIDTH, Main.HEIGHT));
        } catch (Exception ignored) {}
    }

    @FXML
    protected void handleDiceButton(ActionEvent e) {
        // when multiplayer, make a new ActionEvent class for this and compare active player with the player clicked
        if (game != null) {
            DiceResult result = game.rollDice();
            die1.setText(Integer.toString(result.getFirstDieResult()));
            die2.setText(Integer.toString(result.getSecondDieResult()));

            game.processTurn();
        }
    }

    public void setupGame(String name) {
        try {
            ArrayList<Player> players = new ArrayList<>();
            game = new MonopolyGame(players);

            Player p1 = new Player(1, name, Player.Token.SHOE, 1);
            Player p2 = new Player(2, "Player2", Player.Token.TOP_HAT, 1);
            game.addPlayer(p1);
            game.addPlayer(p2);
            setupBoard();
        } catch (Exception ignored) {}
    }

    private void setupBoard() {
        // Disable yes, no buttons
        yesButton.setDisable(true);
        noButton.setDisable(true);

        // Set up Tiles
        ArrayList<Tile> tiles = game.getBoard().getTiles();

        grids = new GridPane[4];
        grids[0] = grid0;
        grids[1] = grid1;
        grids[2] = grid2;
        grids[3] = grid3;

        for (Tile tile : tiles) {
            // get x and y
            int x, y; // grids[x].get(y)
            int location = tile.getTileId();

            x = calculateX(location);
            y = calculateY(location);

            // get the type of the tile and put images

            if (tile instanceof JailTile) {
                ImageView imageView = new ImageView();
                File file = new File("models/jail_tile.png");
                addImageToGrids(grids, x, y, imageView, file);

            } else if (tile instanceof StartTile) {
                ImageView imageView = new ImageView();
                File file = new File("models/start_tile.png");
                addImageToGrids(grids, x, y, imageView, file);

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
                    addImageToGrids(grids, x, y, imageView, file);

                } else if (((CardTile) tile).getCardType() == CardTile.CardType.CHANCE_CARD) {
                    ImageView imageView = new ImageView();
                    File file = new File("models/chance_tile.png");
                    addImageToGrids(grids, x, y, imageView, file);
                }
            } else if (tile instanceof GoToJailTile) {
                ImageView imageView = new ImageView();
                File file = new File("models/goto_jail_tile.png");
                addImageToGrids(grids, x, y, imageView, file);
            } else if (tile instanceof FreeParkingTile) {
                ImageView imageView = new ImageView();
                File file = new File("models/freeparking_tile.png");
                addImageToGrids(grids, x, y, imageView, file);
            }
        }

        // Setup board
        updateBoardState();
    }

    private void addImageToGrids(GridPane[] grids, int x, int y, ImageView imageView, File file) {
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
        imageView.fitWidthProperty().bind(grids[x].widthProperty());
        imageView.fitHeightProperty().bind(grids[x].heightProperty());

        if (x % 2 == 0) {
            grids[x].add(imageView, y, 0);
        } else {
            grids[x].add(imageView, 0, y);
        }
    }

    public void updateBoardState() {
        // Set playerTurn
        playerTurn.setText(game.getActivePlayer().getName() + "'s Turn!");

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

            if (i >= logSize) {
                logLabels[i].setText("");
            } else {
                int loc = i + logSize - LOG_CAPACITY;
                logLabels[i].setText(loc + ": " + actionLog.getLog().get(loc));
            }
        }

        // Clear Tokens
        for (int i = 0; i < 4; i++) {
            ObservableList<Node> children = grids[i].getChildren();
            for (Node n : children) {
                if (n instanceof ImageView && (n.getId().equals("t1") || n.getId().equals("t2"))) {
                    ImageView token = (ImageView) n;
                    grids[i].getChildren().remove(token);
                }
            }
        }

        // Put Tokens
        int t1x = calculateX(p1.getPosition());
        int t1y = calculateY(p1.getPosition());
        int t2x = calculateX(p2.getPosition());
        int t2y = calculateY(p2.getPosition());

        ImageView token1 = (new ImageView(new Image((
                new File("models/tokens/" + p2.getTokenName() + ".png")).toURI().toString())));
        ImageView token2 = new ImageView(new Image((
                new File("models/tokens/" + p2.getTokenName() + ".png")).toURI().toString()));

        token1.setId("t1");
        token2.setId("t2"); // set id to easily delete later

        token1.setFitHeight(20);
        token1.setFitWidth(20);
        token2.setFitHeight(20);
        token2.setFitWidth(20);

        if (t1x % 2 == 0)
            grids[t1x].add(token1, t1y, 0);
        else
            grids[t1x].add(token1, 0, t1y);

        if (t2x % 2 == 0)
            grids[t2x].add(token2, t2y, 0);
        else
            grids[t2x].add(token2, 0, t2y);
    }

    private static int calculateX(int location) {
        if (location < 12) {
            return 0;
        } else if (location < 21) {
            return 1;
        } else if (location < 33) {
            return 2;
        } else {
            return 3;
        }
    }

    private static int calculateY(int location) {
        if (location < 12) {
            return 11 - location;
        } else if (location < 21) {
            return 20 - location;
        } else if (location < 33) {
            return location - 21;
        } else {
            return location - 33;
        }
    }

    public boolean showPropertyDialog(Property property) {
        Dialog<ButtonType> dialog = new Dialog<>();

        String title;
        String content;

        String name = property.getName();
        Building b = (Building) property;

        if (!b.isOwned()) {
            title = "Buy Property?";
            content = "Do you wish to buy " + b.getName() + "?\n" +
                    "Price: " + b.getPrice() + ".\n" +
                    "Rent: " + b.getRents().get(0) + ".";
        } else if (b.getHouseCount() < 4) {
            title = "Add House?";
            content = "Do you wish to build a house to " + b.getName() + "?\n" +
                    "Price: " + b.getHousePrice() + ".\n" +
                    "Rent: " + b.getRents().get(b.getHouseCount()) + " ==> " +
                    b.getRents().get(b.getHouseCount() + 1);
        } else {
            title = "Add Hotel?";
            content = "Do you wish to build a Hotel to " + b.getName() + "?\n" +
                    "Price: " + b.getHotelPrice() + ".\n" +
                    "Rent: " + b.getRents().get(4) + " ==> " +
                    b.getRents().get(5);
        }

        dialog.setTitle(title);
        dialog.setContentText(content);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
        Optional<ButtonType> result = dialog.showAndWait();
        return result.isPresent() && (result.get().equals(ButtonType.YES));
    }
}