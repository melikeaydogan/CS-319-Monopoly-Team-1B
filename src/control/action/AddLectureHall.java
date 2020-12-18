package control.action;

import control.MonopolyGame;
import entity.Player;
import entity.property.Building;

public class AddLectureHall implements Action{
    private Building building;
    private Player player;

    public AddLectureHall(Building building, Player player) {
        this.building = building;
        this.player = player;
    }

    public AddLectureHall() {
    }

    @Override
    public void act() { // color check?
        if ( player.getBalance() > building.getLectureHallPrice() ) {
            new RemoveMoneyAction(player,building.getLectureHallPrice()).act();
            building.addLectureHall(); // ???
        }

        MonopolyGame.getActionLog().addMessage(player.getName() + "adds a hotel to " + building.getName() + "\n");

    }

}
