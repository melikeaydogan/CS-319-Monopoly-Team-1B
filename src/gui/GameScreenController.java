package gui;

import control.ActionLog;
import control.MonopolyGame;
import entity.Player;
import entity.dice.DiceResult;
import entity.property.Building;
import entity.property.Dorm;
import entity.property.Facility;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class GameScreenController {
    private final int LOG_CAPACITY = 8;

    // Game object
    MonopolyGame game;

    // Labels for die results
    @FXML Label die1, die2;

    // The squares on board
    @FXML StackPane square0, square1, square2, square3, square4, square5, square6, square7, square8, square9, square10,
            square11, square12, square13, square14,square15,square16,square17,square18,square19, square20, square21,
            square22, square23, square24, square25, square26, square27, square28, square29, square30, square31, square32,
            square33, square34, square35, square36, square37, square38, square39;
    StackPane[] squares;

    @FXML AnchorPane anchorPane;

    // Activity Log
    @FXML Label log0, log1, log2, log3, log4, log5, log6, log7;

    // Player information labels
    @FXML Label player1Label, player2Label;

    // Text that shows whose turn is it
    @FXML Text playerTurn;


    // Stop the game and go to main menu when quit button is pressed
    @FXML
    protected void handleQuitButton(ActionEvent e) {
        try {
            game.stopGame();
            Stage stage = (Stage) die1.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("main_menu.fxml"));
            stage.setScene(new Scene(root, Main.WIDTH, Main.HEIGHT));
        } catch (Exception ignored) {}
    }

    // Roll dice when dice button is pressed
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
            Player p1 = new Player(1, name, Player.Token.SHOE, 1);
            Player p2 = new Player(2, "Deneme", Player.Token.TOP_HAT, 1);

            game = new MonopolyGame(players, this);
            game.addPlayer(p1);
            game.addPlayer(p2);

            setupBoard();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupBoard() {
         StackPane[] squares = {square0, square1, square2, square3, square4, square5, square6, square7, square8, square9, square10,
                square11, square12, square13, square14,square15,square16,square17,square18,square19, square20, square21,
                square22, square23, square24, square25, square26, square27, square28, square29, square30, square31, square32,
                square33, square34, square35, square36, square37, square38, square39};
        this.squares = squares;

        // Set up Tiles
        ArrayList<Tile> tiles = game.getBoard().getTiles();

        for (Tile tile : tiles) {
            int location = tile.getTileId();

            // get the type of the tile and put labels
            if (tile instanceof PropertyTile) {
                Property property = game.getBoard().getPropertyById(((PropertyTile) tile).getPropertyId());
                String name = property.getName();
                int price = property.getPrice();
                String type;
                if (property instanceof Building) {
                    type = "Building";
                } else  if (property instanceof Dorm) {
                    type = "Dorm";
                } else { // property instanceof Facility
                    type = "Facility";
                }

                DecimalFormat decimalFormat = new DecimalFormat();

                BorderPane propertyPane = new BorderPane();
                propertyPane.setTop(new Label("     " + type));
                propertyPane.setCenter(new Label(name));
                propertyPane.setBottom(new Label("     " + decimalFormat.format(price) + "$"));

                this.squares[location].getChildren().add(propertyPane);
            } else if (tile instanceof TaxTile) {
                DecimalFormat decimalFormat = new DecimalFormat();
                int amount = ((TaxTile) tile).getAmount();
                BorderPane taxPane = new BorderPane();
                taxPane.setCenter(new Label("Tax"));
                taxPane.setBottom(new Label("     " + decimalFormat.format(amount) + "$"));

                this.squares[location].getChildren().add(taxPane);
            }
        }

        // Setup others
        updateBoardState();
    }

    public void updateBoardState() {
        // Set playerTurn
        playerTurn.setText(game.getActivePlayer().getName() + "'s Turn!");

        DecimalFormat decimalFormat = new DecimalFormat();

        // Update player labels
        Player p1 = game.getPlayerController().getPlayers().get(0);
        Player p2 = game.getPlayerController().getPlayers().get(1);
        player1Label.setText("  " + p1.getName() + " - " + p1.getTokenName() + " - " + decimalFormat.format(p1.getBalance()) + "$");
        player2Label.setText("  " + p2.getName() + " - " + p2.getTokenName() + " - " + decimalFormat.format(p2.getBalance()) + "$");

        // Update Game Log
        ActionLog actionLog = MonopolyGame.getActionLog();
        Label[] logLabels = {log0, log1, log2, log3, log4, log5, log6, log7};
        int logSize = actionLog.getNumActions();
        if (logSize > LOG_CAPACITY) {
            for (int i = logSize - 1, j = LOG_CAPACITY - 1; j >= 0; i--, j--) {
                logLabels[j].setText(actionLog.getLog().get(i));
            }
        } else {
            int i = 0;
            for ( ; i < logSize; i++)
                logLabels[i].setText(actionLog.getLog().get(i));

            for ( ; i < LOG_CAPACITY; i++)
                logLabels[i].setText(" ");
        }

        // Clear Tokens
        ArrayList<ImageView> tokens = new ArrayList<>(6);
        for (StackPane square : squares) {
            ObservableList<Node> children = square.getChildren();
            for (Node n : children) {
                if (!Objects.isNull(n.getId())) {
                    if (n instanceof ImageView && (n.getId().equals("t1") || n.getId().equals("t2"))) {
                        ImageView token = (ImageView) n;
                        tokens.add(token);
                    }
                }

            }
            for (ImageView tokenImage : tokens)
                square.getChildren().remove(tokenImage);
        }

        // Put Tokens
        int p1Pos = p1.getPosition();
        int p2Pos = p2.getPosition();

        File file1 = new File("src/gui/models/tokens/" + p1.getTokenName() + ".png");
        Image image1 = new Image(file1.toURI().toString());
        ImageView token1 = new ImageView(image1);

        File file2 = new File("src/gui/models/tokens/" + p2.getTokenName() + ".png");
        Image image2 = new Image(file2.toURI().toString());
        ImageView token2 = new ImageView(image2);

        token1.setId("t1");
        token2.setId("t2"); // set id to easily delete later

        token1.setFitHeight(50);
        token1.setFitWidth(50);
        token2.setFitHeight(50);
        token2.setFitWidth(50);

        squares[p1Pos].getChildren().add(token1);
        squares[p2Pos].getChildren().add(token2);

        //anchorPane.getChildren().add(token1);
        //token1.toFront();
       // token2.toFront();
    }

    public boolean showPropertyDialog(Property property) {
        Dialog<ButtonType> dialog = new Dialog<>();

        String title = "";
        String content = "";

        String name = property.getName();

        if (property instanceof Building) {
            Building b = (Building) property; // ToDo it can be also Dorm or Facility, so it gives ClassCastException

            if (!b.isOwned()) {
                title = "Buy Property?";
                content = "Do you wish to buy " + b.getName() + "?\n" +
                        "Price: " + b.getPrice() + ".\n" +
                        "Rent: " + b.getRents().get(0) + ".";
            } else if (b.getHouseCount() < 4 && game.getActivePlayer().isComplete(b)) {
                title = "Add House?";
                content = "Do you wish to build a house to " + b.getName() + "?\n" +
                        "Price: " + b.getHousePrice() + ".\n" +
                        "Rent: " + b.getRents().get(b.getHouseCount()) + " ==> " +
                        b.getRents().get(b.getHouseCount() + 1);
            } else if (b.getHotelCount() == 4 && game.getActivePlayer().isComplete(b)) {
                title = "Add Hotel?";
                content = "Do you wish to build a Hotel to " + b.getName() + "?\n" +
                        "Price: " + b.getHotelPrice() + ".\n" +
                        "Rent: " + b.getRents().get(4) + " ==> " +
                        b.getRents().get(5);
            }
        }
        else if (property instanceof Dorm) {
            Dorm d = (Dorm) property;

            if (!d.isOwned()) {
                title = "Buy Dormitory?";
                content = "Do you wish to buy " + d.getName() + "?\n" +
                        "Price: " + d.getPrice() + ".\n" +
                        "Rent: " + d.getRents().get(0) + ".";
            }
        }

        else {
            Facility f = (Facility) property;

            if (!f.isOwned()) {
                title = "Buy Facility?";
                content = "Do you wish to buy " + f.getName() + "?\n" +
                        "Price: " + f.getPrice() + ".\n" +
                        "Rent: " + f.getRents().get(0) + ".";
            }
        }


        dialog.setTitle(title);
        dialog.setContentText(content);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
        Optional<ButtonType> result = dialog.showAndWait();

        return result.isPresent() && (result.get().equals(ButtonType.YES));
    }
}