package control.action;

import control.MonopolyGame;
import control.PlayerController;
import entity.Board;
import entity.Player;
import entity.property.Building;

public class AddLectureHallAction implements Action{
    private int buildingId;
    private int playerId;

    public AddLectureHallAction(int buildingId, int playerId) {
        this.buildingId = buildingId;
        this.playerId = playerId;
    }

    public AddLectureHallAction() {
    }

    @Override
    public void act() { // color check?
        Player player = PlayerController.getById(playerId);
        Building building = (Building) Board.getPropertyById(buildingId);
        if ( player.getBalance() > building.getLectureHallPrice() ) {
            new RemoveMoneyAction(player.getPlayerId(),building.getLectureHallPrice()).act();
            building.addLectureHall(); // ???
        }

        MonopolyGame.getActionLog().addMessage(player.getName() + "adds a hotel to " + building.getName() + "\n");

    }

}
