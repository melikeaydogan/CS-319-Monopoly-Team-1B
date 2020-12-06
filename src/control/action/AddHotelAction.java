package control.action;

import control.MonopolyGame;
import entity.Player;
import entity.property.Building;

public class AddHotelAction implements Action{
    private Building building;
    private Player player;

    public AddHotelAction(Building building, Player player) {
        this.building = building;
        this.player = player;
    }

    public AddHotelAction() {
    }

    @Override
    public void act() { // color check?
        if ( player.getBalance() > building.getHotelPrice() ) {
            new RemoveMoneyAction(player,building.getHotelPrice()).act();
            building.addHotel(); // ???
        }

        MonopolyGame.getActionLog().addMessage(player.getName() + "adds a hotel to " + building.getName() + "\n");
    }

}
