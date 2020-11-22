package entity.card;

import control.GameMode;
import entity.card.Card;

public class CommunityChestCard extends Card {
    public CommunityChestCard(int id, String instructions, MonopolyAction action){
        super(id, instructions, action);
    }
    public CommunityChestCard(CommunityChestCard savedCard){
        super(savedCard);
    }

}
