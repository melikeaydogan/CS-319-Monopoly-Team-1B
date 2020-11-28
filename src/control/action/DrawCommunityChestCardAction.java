package control.action;

import control.MonopolyGame;
import entity.Board;
import entity.Player;
import entity.card.Card;

public class DrawCommunityChestCardAction implements Action{
    private Player player;

    public DrawCommunityChestCardAction(Player player) {
        this.player = player;
    }

    @Override
    public void act() {
        //Card communityChestCard = board.drawCommunityChestCard();
        // ???
        //communityChestCard.processCard();

        MonopolyGame.getActionLog().addMessage(player.getName() + " draws a community chance card \n");
    }
}

// is this action necessary??
