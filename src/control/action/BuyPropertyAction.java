package control.action;

import control.MonopolyGame;
import entity.Player;
import entity.property.Property;

import java.util.ArrayList;

public class BuyPropertyAction implements Action{
    private Property property; // ToDo implement after Property
    private Player player;

    public BuyPropertyAction(Property property, Player player) {
        this.property = property;
        this.player = player;
    }

    @Override
    public void act() { // else throw exception
        if ( player.getBalance() > property.getPrice() ) {
            property.setOwned(true);
            property.setOwnerId(player.getPlayerId());

            player.getProperties().add(property);

            player.removeMoney(property.getPrice());

            MonopolyGame.getActionLog().addMessage(player.getName() + " buys the property " + property.getName());
        }
    }

}
