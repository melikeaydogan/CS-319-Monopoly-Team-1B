package control.action;

import control.MonopolyGame;
import entity.Board;
import entity.Player;
import entity.card.Card;

public class DrawCommunityChestCardAction implements Action{
    private Player player;
    private Card card;

    public DrawCommunityChestCardAction(Player player, Card card) {
        this.player = player;
        this.card = card;
    }

    @Override
    public void act() {
        //Card communityChestCard = board.drawCommunityChestCard();
        // ???
        //communityChestCard.processCard();

        MonopolyGame.getActionLog().addMessage(player.getName() + " draws the community chance card: " + card + "\n");
    }
}

// is this action necessary??
