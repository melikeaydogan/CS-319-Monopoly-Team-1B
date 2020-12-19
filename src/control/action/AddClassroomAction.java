package control.action;

import control.MonopolyGame;
import control.PlayerController;
import entity.Board;
import entity.Player;
import entity.property.Building;

public class AddClassroomAction implements Action {
    private int buildingId;
    private int playerId;

    public AddClassroomAction(int buildingId, int playerId) {
        this.buildingId = buildingId;
        this.playerId = playerId;
    }

    public AddClassroomAction() {
    }

    @Override
    public void act() {
        Player player = PlayerController.getById(playerId);
        Building building = (Building) Board.getPropertyById(buildingId);

        if ( player.getBalance() > building.getClassroomPrice() ) {
                new RemoveMoneyAction(player.getPlayerId(), building.getClassroomPrice()).act();
                building.addClassroom();

                MonopolyGame.getActionLog().addMessage(player.getName() + " adds a classroom to "
                    + building.getName() + "\n");
        }

    }

}
