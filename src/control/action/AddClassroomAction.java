package control.action;

import control.MonopolyGame;
import entity.Player;
import entity.property.Building;

public class AddClassroomAction implements Action {
    private Building building;
    private Player player;

    public AddClassroomAction(Building building, Player player) {
        this.building = building;
        this.player = player;
    }

    public AddClassroomAction() {
    }

    @Override
    public void act() { // color check?
        if ( player.getBalance() > building.getClassroomPrice() ) {
                new RemoveMoneyAction(player, building.getClassroomPrice()).act();
                building.addClassroom(); // ???
        }

        MonopolyGame.getActionLog().addMessage(player.getName() + "adds a house to "
                + building.getName() + "\n");

    }

}
