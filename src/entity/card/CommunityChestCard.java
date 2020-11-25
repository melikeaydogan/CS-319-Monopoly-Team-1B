package entity.card;

//import control.GameMode;
import control.MonopolyGame;
import control.action.Action;
import entity.card.Card;

public class CommunityChestCard extends Card {
    public CommunityChestCard(int id, String instructions){
        super(id, instructions);
    }

    @Override
    public void processCard(MonopolyGame monopolyGame) {

    }

    public CommunityChestCard(CommunityChestCard savedCard){
        super(savedCard);
    }

}
