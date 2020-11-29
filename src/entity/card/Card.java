package entity.card;

import control.MonopolyGame;
import control.action.*;
import entity.Player;
import entity.property.Building;
import entity.property.Property;

import java.util.ArrayList;

public /*abstract*/ class Card {
    //public enum CardType {
    //    CHANCE_CARD,
    //    COMMUNITY_CHEST_CARD
    //}
    int id;
    String instructions;
    //CardType cardType;


    public Card(){
        id = -1;
        instructions = "";
        //cardType = null;
    }
    public Card(Card c){
        this.id = c.id;
        this.instructions = c.instructions;
        //cardType = c.cardType;
    }
    public Card(int id, String instructions/*, CardType cardType*/){
        this.id = id;
        this.instructions = instructions;
        //this.cardType = cardType;
    }

    public String getInstructions(){
        return instructions;
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void processCard(MonopolyGame monopolyGame) {
        Player activePlayer = monopolyGame.getActivePlayer();
        switch (id) {
            case 0:
                new FreeMoveAction(activePlayer, 1).act(); // L Building --> position = 1
                monopolyGame.processTurn();
                break;
            case 1:
                new AddMoneyAction(activePlayer, 1_000).act();
                break;
            case 2:
                // requires ui confirmation
                break;
            case 3:
                new RemoveMoneyAction(activePlayer, 10_000).act();
                break;
            case 4:
                new FreeMoveAction(activePlayer, 0).act(); // ToDo convert freemoveactions to move
                break;
            case 5:
                for (Player p : monopolyGame.getPlayerController().getPlayers() )
                    if ( p.getPlayerId() != activePlayer.getPlayerId() )
                        new TransferAction(p, activePlayer, 1_000).act();
                break;
            case 6:
                new GoToJailAction(activePlayer).act();
                break;
            case 7:
                new GetOutOfJailAction(activePlayer).act(); // ToDo store the card??
                break;
            case 8: // duplicate card with 4
                break;
            case 9:
                new MoveAction(activePlayer, -3).act();
                monopolyGame.processTurn();
                break;
            case 10:
                new FreeMoveAction(activePlayer, 23).act(); // Kirac is in 23th tile
                monopolyGame.processTurn();
                break;
            case 11:
                new GoToJailAction(activePlayer).act();
                break;
            case 12:
                new FreeMoveAction(activePlayer,39).act(); // Library is the last tile (39th)
                monopolyGame.processTurn();
                break;
            case 13:
                new AddMoneyAction(activePlayer, 10_000).act();
                break;
            case 14:
                new RemoveMoneyAction(activePlayer, 2_000).act();
                break;
            case 15:
                int houseCount = 0;
                int hotelCount = 0;
                int repairAmount = 0;
                ArrayList<Property> properties = activePlayer.getProperties().get("BUILDING");
                for (Property p : properties ) {
                    houseCount = houseCount + ((Building) p).getHouseCount();
                    hotelCount = hotelCount + ((Building) p).getHotelCount();
                }
                repairAmount = (4_000 * houseCount) + (11_500 * hotelCount);
                new RemoveMoneyAction(activePlayer, repairAmount);
                break;
        }
    }

    public String toString() {
        return "ID: " + id + " - Instructions: " + instructions;
    }

    // ChanceCard 0 --> You won $200 from lottery
    // ChanceCard 1 --> You went to jail

    // Community Chest 0 -->  go to beginning point
    // Community Chest 1 -->  Go 3 tile backwards
}
