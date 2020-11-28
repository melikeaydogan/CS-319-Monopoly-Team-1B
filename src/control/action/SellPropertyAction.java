package control.action;

import control.MonopolyGame;
import entity.Player;
import entity.property.Property;

public class SellPropertyAction implements Action {
    private Player player;
    private Property property;

    public SellPropertyAction(Player player, Property property) {
        this.player = player;
        this.property = property;
    }

    @Override
    public void act() {
        property.setOwned(false);
        property.setOwnerId(-1);

        player.getProperties().remove(property);

        player.addMoney(property.getPrice());

        MonopolyGame.getActionLog().addMessage(player.getName() + " sells the property " + property.getName());
    }
}
