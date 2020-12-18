package control.action;

import control.MonopolyGame;
import control.PlayerController;
import entity.Board;
import entity.Player;
import entity.property.Property;
import network.MonopolyClient;

public class SellPropertyAction implements Action {
    private int playerId;
    private int propertyId;

    public SellPropertyAction(int playerId, int propertyId) {
        this.playerId = playerId;
        this.propertyId = propertyId;
    }

    public SellPropertyAction() {
    }

    @Override
    public void act() {
        Property property = Board.getPropertyById(propertyId);
        Player player = PlayerController.getById(playerId);

        property.setOwned(false);
        property.setOwnerId(-1);

        player.getProperties().remove(property);

        player.addMoney(property.getPrice());

        MonopolyGame.getActionLog().addMessage(player.getName() + " sells the property " + property.getName() + "\n");

    }
}
