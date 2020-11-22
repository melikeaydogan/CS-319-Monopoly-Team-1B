package entity.card;

import control.GameMode;

public class ChanceCard extends Card {
    public ChanceCard(int id, String instructions, MonopolyAction action){
        super(id, instructions, action);
    }
    public ChanceCard(ChanceCard savedCard){
        super(savedCard);
    }
}
