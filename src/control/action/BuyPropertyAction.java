package control.action;

import control.MonopolyGame;
import control.PlayerController;
import entity.Board;
import entity.Player;
import entity.property.Building;
import entity.property.Dorm;
import entity.property.Facility;
import entity.property.Property;
import network.MonopolyClient;

import java.util.ArrayList;

public class BuyPropertyAction implements Action{
    private int propertyId;
    private int playerId;

    public BuyPropertyAction(int propertyId, int playerId) {
        this.propertyId = propertyId;
        this.playerId = playerId;
    }

    public BuyPropertyAction() {
    }

    @Override
    public void act() {
        Player player = PlayerController.getById(playerId);
        Property property = Board.getPropertyById(propertyId);
        if ( player.getBalance() >= property.getPrice() ) {
            property.setOwned(true);
            property.setOwnerId(player.getPlayerId());

            if (property instanceof Dorm) {
                player.getProperties().get("DORM").add((Dorm) property);
            }
            else if (property instanceof Facility ) {
                player.getProperties().get("FACILITY").add((Facility) property);
            }
            else if (property instanceof Building ) {
                player.getProperties().get("BUILDING").add((Building) property);
            }

            new RemoveMoneyAction(player.getPlayerId(), property.getPrice()).act();

            MonopolyGame.getActionLog().addMessage(player.getName() + " buys the property " + property.getName() + "\n");
        }
        else {
            System.out.println("not enough money");
        }
    }

}
