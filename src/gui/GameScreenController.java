package gui;

import control.ActionLog;
import control.MonopolyGame;
import control.action.Action;
import entity.Player;
import entity.dice.DiceResult;
import entity.property.Building;
import entity.property.Dorm;
import entity.property.Facility;
import entity.property.Property;
import entity.tile.*;

import javafx.application.Platform;
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
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import network.MonopolyClient;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class GameScreenController {
    private final int LOG_CAPACITY = 8;

    MonopolyClient monopolyClient;

    // Labels for die results
    @FXML Label die1, die2;
    @FXML Button diceButton;

    // The squares on board
    @FXML StackPane square0, square1, square2, square3, square4, square5, square6, square7, square8, square9, square10,
            square11, square12, square13, square14,square15,square16,square17,square18,square19, square20, square21,
            square22, square23, square24, square25, square26, square27, square28, square29, square30, square31, square32,
            square33, square34, square35, square36, square37, square38, square39;
    StackPane[] squares;

    @FXML AnchorPane anchorPane;

    // Activity Log
    @FXML Label log0, log1, log2, log3, log4, log5, log6, log7;
    Label [] logs;

    // Player information and property labels
    @FXML Label player0Label, player1Label, player2Label, player3Label,player4Label, player5Label,
            player0Properties, player1Properties, player2Properties, player3Properties, player4Properties, player5Properties;
    Label[] playerLabels, playerProperties;

    // Text that shows whose turn is it
    @FXML Text playerTurn;


    // Stop the game and go to main menu when quit button is pressed
    @FXML
    protected void handleQuitButton(ActionEvent e) {
        try {
            getGame().stopGame();
            Stage stage = (Stage) die1.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("main_menu.fxml"));
            stage.setScene(new Scene(root, Main.WIDTH, Main.HEIGHT));
        } catch (Exception ignored) {}
    }

    // Roll dice when dice button is pressed
    @FXML
    protected void handleDiceButton(ActionEvent e) {
        // when multiplayer, make a new ActionEvent class for this and compare active player with the player clicked
        if (getGame() != null) {
            //DiceResult result = getGame().rollDice();
            getGame().rollDice();
            //die1.setText(Integer.toString(result.getFirstDieResult()));
            //die2.setText(Integer.toString(result.getSecondDieResult()));

            //getGame().processTurn();
        }
    }

    public void setDiceLabel(DiceResult result) {
        Platform.runLater(() -> {
            die1.setText(Integer.toString(result.getFirstDieResult()));
            die2.setText(Integer.toString(result.getSecondDieResult()));

            if (!diceButton.isDisabled()) {
                getGame().processTurn();
            }

        });

    }

    public void setMonopolyClient(MonopolyClient monopolyClient) {
        this.monopolyClient = monopolyClient;
        setupBoard();
    }

    private void setupBoard() {
            this.squares = new StackPane[]{square0, square1, square2, square3, square4, square5, square6, square7,
                    square8, square9, square10, square11, square12, square13, square14, square15, square16, square17,
                    square18,square19, square20, square21, square22, square23, square24, square25, square26,
                    square27, square28, square29, square30, square31, square32, square33, square34, square35,
                    square36, square37, square38, square39};

            this.playerLabels = new Label[]{player0Label, player1Label, player2Label, player3Label,
                    player4Label, player5Label};

            this.playerProperties = new Label[]{player0Properties, player1Properties, player2Properties,
                    player3Properties, player4Properties, player5Properties};

            this.logs = new Label[]{log0, log1, log2, log3, log4, log5, log6, log7};

            // Set up Tiles
            ArrayList<Tile> tiles = getGame().getBoard().getTiles();

            for (Tile tile : tiles) {
                int location = tile.getTileId();

                // get the type of the tile and put labels
                if (tile instanceof PropertyTile) {
                    Property property = getGame().getBoard().getPropertyById(((PropertyTile) tile).getPropertyId());
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
            // Set playerTurn and enable dice button only for the player
            playerTurn.setText(getGame().getActivePlayer().getName() + "'s Turn!");
            diceButton.setDisable(getGame().getActivePlayer().getPlayerId() != monopolyClient.getId());

            DecimalFormat decimalFormat = new DecimalFormat();

            // Labels and properties
            int numberOfPlayers = getGame().getPlayerController().getPlayers().size();
            int i;
            for (i = 0; i < numberOfPlayers; i++) {
                Player p = getGame().getPlayerController().getPlayers().get(i);
                playerLabels[i].setText("  " + p.getName() + " - " + p.getTokenName() + " - " + decimalFormat.format(p.getBalance()) + "$");
                playerProperties[i].setText("");
                setPropertyTable(p, playerProperties[i]);
            }
            for (; i < 6 ; i++) {
                playerLabels[i].setText("");
                playerProperties[i].setText("");
            }

            // Update Game Log
            updateGameLog();

            // Clear Tokens
            clearTokens();

            // Put Tokens
            for (i = 0; i < numberOfPlayers; i++) {
                Player p = getGame().getPlayerController().getPlayers().get(i);
                int pPos = p.getPosition();
                File file = new File("src/gui/models/tokens/" + p.getTokenName() + ".png");
                Image image = new Image(file.toURI().toString());
                ImageView token = new ImageView(image);
                token.setId("t" + Integer.toString(i));
                token.setFitHeight(50);
                token.setFitWidth(50);

                squares[pPos].getChildren().add(token);
            }
    }

    private void updateGameLog() {
        Platform.runLater(() -> {
            ActionLog actionLog = MonopolyGame.getActionLog();
            int logSize = actionLog.getNumActions();
            if (logSize > LOG_CAPACITY) {
                for (int i = logSize - 1, j = LOG_CAPACITY - 1; j >= 0; i--, j--) {
                    logs[j].setText(actionLog.getLog().get(i));
                }
            } else {
                int i = 0;
                for ( ; i < logSize; i++)
                    logs[i].setText(actionLog.getLog().get(i));

                for ( ; i < LOG_CAPACITY; i++)
                    logs[i].setText(" ");
            }
        });
    }

    private void clearTokens() {
        ArrayList<ImageView> tokens = new ArrayList<>(6);
        for (StackPane square : squares) {
            ObservableList<Node> children = square.getChildren();
            for (Node n : children) {
                if (!Objects.isNull(n.getId())) {
                    if (n instanceof ImageView && (n.getId().charAt(0) == 't' && n.getId().charAt(1) < '6' &&
                            n.getId().charAt(1) >= '0' && n.getId().length() == 2)) {
                        ImageView token = (ImageView) n;
                        tokens.add(token);
                    }
                }

            }
            for (ImageView tokenImage : tokens)
                square.getChildren().remove(tokenImage);
        }
    }

    private void setPropertyTable(Player p1, Label player1Properties) {
        for (Map.Entry<String, ArrayList<Property>> entry : p1.getProperties().entrySet()) {
            String key = entry.getKey();
            ArrayList<Property> properties = entry.getValue();
            if ( key.equals("BUILDING")) {
                properties.sort((b1, b2) -> {
                    Building building1 = (Building) b1;
                    Building building2 = (Building) b2;
                    return building1.getColor().compareTo(building2.getColor());
                });
                properties.forEach(property -> {
                    Building building = (Building) property;
                    player1Properties.setText(player1Properties.getText() + "\n " + building.getColor() + " - "
                            + building.getName() + " - " + building.getClassroomCount() + ", " + building.getLectureHallCount());
                });
            }
            else {
                properties.forEach(property -> {
                    player1Properties.setText(player1Properties.getText() + "\n " + key + " - " + property.getName());
                });
            }
        }
    }

    public boolean showPropertyDialog(Property property) {
        Dialog<ButtonType> dialog = new Dialog<>();

        String title = "";
        String content = "";
        DecimalFormat decimalFormat = new DecimalFormat();

        String name = property.getName();

        if (property instanceof Building) {
            Building b = (Building) property;

            if (!b.isOwned()) {
                title = "Buy Property?";
                content = "Do you wish to buy " + b.getName() + "?\n\n" +
                        "Price: " + decimalFormat.format(b.getPrice()) + "$\n";
            } else if (b.getClassroomCount() < 4 && getGame().getActivePlayer().isComplete(b)) {
                title = "Add House?";
                content = "Do you wish to build a house to " + b.getName() + "?\n" +
                        "Price: " + decimalFormat.format(b.getClassroomPrice()) + "$\n" +
                        "Rent: " + decimalFormat.format(b.getRents().get(b.getClassroomCount())) + " ==> " +
                        decimalFormat.format(b.getRents().get(b.getClassroomCount() + 1));
            } else if (b.getLectureHallCount() == 4 && getGame().getActivePlayer().isComplete(b)) {
                title = "Add Hotel?";
                content = "Do you wish to build a Hotel to " + b.getName() + "?\n" +
                        "Price: " + decimalFormat.format(b.getLectureHallPrice()) + "$\n" +
                        "Rent: " + decimalFormat.format(b.getRents().get(4)) + " ==> " +
                        decimalFormat.format(b.getRents().get(5));
            }
        }
        else if (property instanceof Dorm) {
            Dorm d = (Dorm) property;

            if (!d.isOwned()) {
                title = "Buy Dormitory?";
                content = "Do you wish to buy " + d.getName() + "?\n" +
                        "Price: " + decimalFormat.format(d.getPrice()) + ".\n";
            }
        }

        else {
            Facility f = (Facility) property;

            if (!f.isOwned()) {
                title = "Buy Facility?";
                content = "Do you wish to buy " + f.getName() + "?\n" +
                        "Price: " + decimalFormat.format(f.getPrice()) + "$\n";
            }
        }


        dialog.setTitle(title);
        dialog.setContentText(content);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
        Optional<ButtonType> result = dialog.showAndWait();

        return result.isPresent() && (result.get().equals(ButtonType.YES));
    }

    public boolean showAddHouseDialog(Building building) {
        Dialog<ButtonType> dialog = new Dialog<>();

        String title = "";
        String content = "";
        DecimalFormat decimalFormat = new DecimalFormat();

        String name = building.getName();

        title = "Add House?";
        content = "Do you wish to build a classroom to " + building.getName() + "?\n" +
                "Price: " + decimalFormat.format(building.getClassroomPrice()) + "$\n" +
                "Rent: " + decimalFormat.format(building.getRents().get(building.getClassroomCount())) + " ==> " +
                decimalFormat.format(building.getRents().get(building.getClassroomCount() + 1));

        dialog.setTitle(title);
        dialog.setContentText(content);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
        Optional<ButtonType> result = dialog.showAndWait();

        return result.isPresent() && (result.get().equals(ButtonType.YES));
    }

    public boolean showAddHotelDialog(Building building) {
        Dialog<ButtonType> dialog = new Dialog<>();

        String title = "";
        String content = "";
        DecimalFormat decimalFormat = new DecimalFormat();

        String name = building.getName();

        title = "Add Hotel?";
        content = "Do you wish to build a lecture hall to " + building.getName() + "?\n" +
        "Price: " + decimalFormat.format(building.getLectureHallPrice()) + "$\n" +
        "Rent: " + decimalFormat.format(building.getRents().get(4)) + " ==> " +
        decimalFormat.format(building.getRents().get(5));

        dialog.setTitle(title);
        dialog.setContentText(content);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
        Optional<ButtonType> result = dialog.showAndWait();

        return result.isPresent() && (result.get().equals(ButtonType.YES));
    }

    public MonopolyGame getGame() {
        return monopolyClient.getMonopolyGame();
    }

    public void sendAction(Action action) {
        monopolyClient.sendAction(action);
    }

    public void sendString(String s) {
        monopolyClient.sendString(s);
    }

    public void activateButtons() {
        diceButton.setDisable(false);
        getGame().getPlayerController().setActivePlayerIndex(monopolyClient.getId());
        updateBoardState();
    }

    public void deactivateButtons() {
        diceButton.setDisable(true);
    }

    public void sendObject(Object o) {
        monopolyClient.sendObject(o);
    }
}