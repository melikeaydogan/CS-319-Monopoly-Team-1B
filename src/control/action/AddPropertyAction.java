package control.action;

import control.MonopolyGame;
import control.PlayerController;
import entity.Board;
import entity.Player;
import entity.property.Building;
import entity.property.Dorm;
import entity.property.Facility;
import entity.property.Property;

public class AddPropertyAction implements Action{
    int playerId;
    int propertyId;

    public AddPropertyAction(int playerId, int propertyId) {
        this.playerId = playerId;
        this.propertyId = propertyId;
    }

    @Override
    public void act() {
        Player player = PlayerController.getById(playerId);
        Property property = Board.getPropertyById(propertyId);

        property.setOwned(true);
        property.setOwnerId(player.getPlayerId());

        if (property instanceof Dorm) {
            player.getProperties().get("DORM").add((Dorm) property);
        }
        else if (property instanceof Facility) {
            player.getProperties().get("FACILITY").add((Facility) property);
        }
        else if (property instanceof Building) {
            player.getProperties().get("BUILDING").add((Building) property);
        }

        MonopolyGame.getActionLog().addMessage(player.getName() + " gets the property " + property.getName() + "\n");
    }
}
