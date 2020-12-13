package control.action;

import control.MonopolyGame;
import entity.Player;
import entity.property.Building;
import entity.property.Property;
import network.MonopolyClient;

public class AddHouseAction implements Action {
    private Building building;
    private Player player;

    public AddHouseAction(Building building, Player player) {
        this.building = building;
        this.player = player;
    }

    public AddHouseAction() {
    }

    @Override
    public void act() { // color check?
        if ( player.getBalance() > building.getHousePrice() ) {
                new RemoveMoneyAction(player, building.getHousePrice()).act();
                building.addHouse(); // ???
        }

        MonopolyGame.getActionLog().addMessage(player.getName() + "adds a house to "
                + building.getName() + "\n");

        MonopolyClient.getInstance().sendAction(this);
    }

}
