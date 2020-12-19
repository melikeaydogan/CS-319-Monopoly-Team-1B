package gui;

import entity.Player;
import entity.property.Building;
import entity.property.Property;
import entity.trade.TradeOffer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import network.MonopolyClient;

import java.util.ArrayList;
import java.util.Map;

public class TradeController {

    @FXML
    ChoiceBox propertyBox, propertyBox1, playerBox;

    @FXML
    TextField feeTextField, feeTextField1;

    @FXML
    Label propertyLabel, feeLabel, propertyLabel1, feeLabel1, statusLabel;

    @FXML
    Button setButton, setButton1, resetButton1, addButton1, sendButton;

    MonopolyClient monopolyClient;
    TradeOffer tradeOffer;
    Player requestedPlayer;

    @FXML
    protected void handleAddButton(ActionEvent e) {
        Property property = (Property) propertyBox.getValue();
        propertyBox.getItems().remove(propertyBox.getValue());
        tradeOffer.getPropertyOffered().add(property.getId());

        propertyLabel.setText(propertyLabel.getText() + " - " + property);
    }

    @FXML
    protected void handleResetButton(ActionEvent e) {
        tradeOffer.getPropertyOffered().clear();
        updateBoxes(propertyBox, monopolyClient.getMonopolyGame().getActivePlayer());
        propertyLabel.setText("Properties that will be offered:");
    }

    @FXML
    protected void handleSetButton(ActionEvent e) {
        int offeredFee = Integer.parseInt(feeTextField.getText());
        tradeOffer.setFeeOffered(offeredFee);
        feeLabel.setText("Fee that will be offered: " + offeredFee);
    }

    @FXML
    protected void handleSendButton(ActionEvent e) {
        sendButton.setDisable(true);
        statusLabel.setText("Status: Waiting for response...");
        monopolyClient.sendObject(tradeOffer);
    }

    @FXML
    protected void handleAddButton1(ActionEvent e) {
        Property property = (Property) propertyBox1.getValue();
        propertyBox1.getItems().remove(propertyBox1.getValue());
        tradeOffer.getPropertyRequested().add(property.getId());

        propertyLabel1.setText(propertyLabel1.getText() + " - " + property);
    }

    @FXML
    protected void handleResetButton1(ActionEvent e) {
        tradeOffer.getPropertyRequested().clear();
        updateBoxes(propertyBox1, requestedPlayer);
        propertyLabel1.setText("Properties that will be offered:");
    }

    @FXML
    protected void handleSetButton1(ActionEvent e) {
        int requestedFee = Integer.parseInt(feeTextField1.getText());
        tradeOffer.setFeeRequested(requestedFee);
        feeLabel1.setText("Fee that will be requested: " + requestedFee);
    }

    @FXML
    protected void handleChooseButton(ActionEvent e) {
        Player player = (Player) playerBox.getValue();
        tradeOffer.setReceiverID(player.getPlayerId());
        requestedPlayer = player;

        updateBoxes(propertyBox1, player);

        setButton1.setDisable(false);
        resetButton1.setDisable(false);
        addButton1.setDisable(false);
        sendButton.setDisable(false);
        propertyBox1.setDisable(false);
        feeTextField1.setDisable(false);
    }

    protected void updateBoxes(ChoiceBox box, Player p) {
        box.getItems().clear();
        for (Map.Entry<String, ArrayList<Property>> entry : p.getProperties().entrySet()) {
            String key = entry.getKey();
            ArrayList<Property> properties = entry.getValue();
            if ( key.equals("BUILDING")) {
                properties.sort((b1, b2) -> {
                    Building building1 = (Building) b1;
                    Building building2 = (Building) b2;
                    return building1.getColor().compareTo(building2.getColor());
                });
                properties.forEach(property -> {
                    box.getItems().add(property);

                });
            }
            else {
                properties.forEach(property -> {
                    //propertyBox.getItems().add(key + " - " + property.getName());
                    box.getItems().add(property);
                });
            }
        }
    }

    public void setupTradeMenu(MonopolyClient monopolyClient) {
        this.monopolyClient = monopolyClient;
        Player activePlayer = monopolyClient.getMonopolyGame().getActivePlayer();

        updateBoxes(propertyBox, activePlayer);

        tradeOffer = new TradeOffer();
        tradeOffer.setSenderID(activePlayer.getPlayerId());

        ArrayList<Player> players = monopolyClient.getMonopolyGame().getPlayerController().getPlayers();
        for (Player p : players) {
            if (!p.equals(activePlayer)) {
                playerBox.getItems().add(p);
            }
        }

        // restrict fee textfields to only numeric values
        feeTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    feeTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }

                if ( !feeTextField.getText().equals("") &&
                        Integer.parseInt(newValue) > monopolyClient.getMonopolyGame().getActivePlayer().getBalance()) {
                    setButton.setDisable(true);
                }
                else {
                    setButton.setDisable(false);
                }
            }
        });

        feeTextField1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    feeTextField1.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        setButton1.setDisable(true);
        resetButton1.setDisable(true);
        addButton1.setDisable(true);
        sendButton.setDisable(true);
        propertyBox1.setDisable(true);
        feeTextField1.setDisable(true);
    }

    public Label getStatusLabel() {
        return statusLabel;
    }
}
