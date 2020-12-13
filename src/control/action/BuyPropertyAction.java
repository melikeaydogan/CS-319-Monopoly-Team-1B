package control.action;

import control.MonopolyGame;
import entity.Player;
import entity.property.Building;
import entity.property.Dorm;
import entity.property.Facility;
import entity.property.Property;
import network.MonopolyClient;

import java.util.ArrayList;

public class BuyPropertyAction implements Action{
    private Property property;
    private Player player;

    public BuyPropertyAction(Property property, Player player) {
        this.property = property;
        this.player = player;
    }

    public BuyPropertyAction() {
    }

    @Override
    public void act() { // else throw exception
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

            new RemoveMoneyAction(player, property.getPrice()).act();

            MonopolyGame.getActionLog().addMessage(player.getName() + " buys the property " + property.getName() + "\n");

            MonopolyClient.getInstance().sendAction(this);
        }
    }

}
