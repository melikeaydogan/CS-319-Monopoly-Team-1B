package control.action;

import control.MonopolyGame;
import control.PlayerController;
import entity.Board;
import entity.Player;
import entity.property.Property;

public class MortgagePropertyAction implements Action {

    private int playerId;
    private int propertyId;

    public MortgagePropertyAction(int propertyId, int playerId) {
        this.propertyId = propertyId;
        this.playerId = playerId;
    }

    public MortgagePropertyAction() {
        this.propertyId = -1;
        this.playerId = -1;
    }

    @Override
    public void act() {
        Player player = PlayerController.getById(playerId);
        Property property = Board.getPropertyById(propertyId);
        if(player == null){
            System.out.println("[MortgagePropertyAction] NULL player.");
            return;
        }
        if(property == null){
            System.out.println("[MortgagePropertyAction] NULL property.");
            return;
        }

        // check whether the given player is the owner of the given property
        if( playerId != property.getOwnerId()){
            System.out.println("[MortgagePropertyAction] This player is not the owner of this property.");
            return;
        }

        // if property is not mortgaged, make property mortgaged
        if (!property.isMortgaged()){
            property.setMortgaged(true);
            player.addMoney( property.getMortgagePrice() );
            MonopolyGame.getActionLog().addMessage(player.getName() + " mortgaged the property " + property.getName() + "\n");
        }
        else{
            System.out.println("[MortgagePropertyAction] This property is already mortgaged.");
        }

    }
}
