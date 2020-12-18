package control.action;

import control.MonopolyGame;
import control.PlayerController;
import entity.Board;
import entity.Player;
import entity.property.Property;

public class UnmortgagePropertyAction implements Action {

    private int playerId;
    private int propertyId;
    final private double interestRate = 0.1;

    public UnmortgagePropertyAction(int propertyId, int playerId) {
        this.propertyId = propertyId;
        this.playerId = playerId;
    }

    public UnmortgagePropertyAction() {
    }

    @Override
    public void act() {
        Player player = PlayerController.getById(playerId);
        Property property = Board.getPropertyById(propertyId);

        //make property mortgaged
        if(player == null){
            System.out.println("[UnmortgagePropertyAction] NULL player.");
            return;
        }
        if(property == null){
            System.out.println("[UnmortgagePropertyAction] NULL property.");
            return;
        }
        if( playerId != property.getOwnerId()){
            System.out.println("[UnmortgagePropertyAction] This player is not the owner of this property.");
            return;
        }

        if ( property.isMortgaged() ){
            int payment = calculateInterest(property.getMortgagePrice())+ property.getMortgagePrice();
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
        return (int)(interestRate * mortgagePrice);
    }
}
