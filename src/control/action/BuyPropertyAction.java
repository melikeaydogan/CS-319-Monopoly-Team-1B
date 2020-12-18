package control.action;

import control.MonopolyGame;
import control.PlayerController;
import entity.Player;
import entity.property.Building;
import entity.property.Dorm;
import entity.property.Facility;
import entity.property.Property;
import network.MonopolyClient;

import java.util.ArrayList;

public class BuyPropertyAction implements Action{
    private Property property;
    private int playerId;

    public BuyPropertyAction(Property property, int playerId) { // it should be by ID
        this.property = property;
        this.playerId = playerId;
    }

    public BuyPropertyAction() {
    }

    @Override
    public void act() { // else throw exception
        Player player = PlayerController.getById(playerId);
        if ( player.getBalance() >= property.getPrice() ) {
            property.setOwned(true);
            property.setOwnerId(player.getPlayerId());
            System.out.println("[BuyPropertyAction] Player --> " + player);
            System.out.println("[BuyPropertyAction] Property --> " + property);

            if (property instanceof Dorm) {
                player.getProperties().get("DORM").add((Dorm) property);
                System.out.println("[BuyPropertyAction] It is a Dorm");
            }
            else if (property instanceof Facility ) {
                player.getProperties().get("FACILITY").add((Facility) property);
                System.out.println("[BuyPropertyAction] It is a Facility");
            }
            else if (property instanceof Building ) {
                player.getProperties().get("BUILDING").add((Building) property);
                System.out.println("[BuyPropertyAction] It is a Building");
            }

            new RemoveMoneyAction(player, property.getPrice()).act();

            MonopolyGame.getActionLog().addMessage(player.getName() + " buys the property " + property.getName() + "\n");
        }
        else {
            System.out.println("not enough money");
        }
    }

}
