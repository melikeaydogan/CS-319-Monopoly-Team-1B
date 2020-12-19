package control.action;

import control.MonopolyGame;
import control.PlayerController;
import entity.Board;
import entity.Player;
import entity.property.Building;
import entity.property.Dorm;
import entity.property.Facility;
import entity.property.Property;

public class RemovePropertyAction implements Action{
    int playerId;
    int propertyId;

    public RemovePropertyAction(int playerId, int propertyId) {
        this.playerId = playerId;
        this.propertyId = propertyId;
    }

    @Override
    public void act() {
        Player player = PlayerController.getById(playerId);
        Property property = Board.getPropertyById(propertyId);

        property.setOwned(false);
        property.setOwnerId(-1);

        if (property instanceof Dorm) {
            player.getProperties().get("DORM").remove((Dorm) property);
        }
        else if (property instanceof Facility) {
            player.getProperties().get("FACILITY").remove((Facility) property);
        }
        else if (property instanceof Building) {
            player.getProperties().get("BUILDING").remove((Building) property);
        }

        MonopolyGame.getActionLog().addMessage(player.getName() + " gives the property " + property.getName() + "\n");

    }
}
