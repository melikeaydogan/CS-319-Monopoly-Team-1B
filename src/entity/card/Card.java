package entity.card;

import control.MonopolyGame;
import control.action.*;
import entity.Player;

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
            case 1:
                new AddMoneyAction(activePlayer, 200).act();
                break;
            case 2:

        }
    }

    // ChanceCard 0 --> You won $200 from lottery
    // ChanceCard 1 --> You went to jail

    // Community Chest 0 -->  go to beginning point
    // Community Chest 1 -->  Go 3 tile backwards
}
