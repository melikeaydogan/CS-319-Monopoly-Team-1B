package control.action;

import control.MonopolyGame;
import control.PlayerController;
import entity.Board;
import entity.Player;
import entity.property.Property;

public class UnmortgagePropertyAction implements Action {

    private int playerId;
    private int propertyId;
    private final double INTEREST_RATE = 0.1;

    public UnmortgagePropertyAction(int propertyId, int playerId) {
        this.propertyId = propertyId;
        this.playerId = playerId;
    }

    public UnmortgagePropertyAction() {
        this.propertyId = -1;
        this.playerId = -1;
    }

    @Override
    public void act() {
        Player player = PlayerController.getById(playerId);
        Property property = Board.getPropertyById(propertyId);

        if(player == null){
            System.out.println("[UnmortgagePropertyAction] NULL player.");
            return;
        }
        if(property == null){
            System.out.println("[UnmortgagePropertyAction] NULL property.");
            return;
        }

        // check whether the given player is the owner of the given property
        if( playerId != property.getOwnerId()){
            System.out.println("[UnmortgagePropertyAction] This player is not the owner of this property.");
            return;
        }

        // if property is mortgaged, make property unmortgaged
        if ( property.isMortgaged() ){
            int payment = calculateInterest(property.getMortgagePrice())+ property.getMortgagePrice();
            //check do the player has enough money
            if ( player.getBalance() >= payment ){
                property.setMortgaged(true);
                System.out.println("[UnmortgagePropertyAction] Player --> " + player);
                System.out.println("[UnmortgagePropertyAction] Property --> " + property);

                new RemoveMoneyAction(player.getPlayerId(), payment).act();
                MonopolyGame.getActionLog().addMessage(player.getName() + " lift the mortgage of the property " + property.getName() + "\n");
            }
            else{
                System.out.println("[UnmortgagePropertyAction] Not enough money.");
            }
        }
        else{
            System.out.println("[UnmortgagePropertyAction] This property is not mortgaged.");
        }

    }
    private int calculateInterest( int mortgagePrice ) {
        return (int)( INTEREST_RATE * mortgagePrice);
    }
}
