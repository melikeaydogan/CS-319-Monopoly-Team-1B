package control.action;

import control.MonopolyGame;
import entity.Player;
import entity.card.CommunityChestCard;

public class DrawCommunityChestCardAction implements Action{
    private Player player; // ToDo should we put players into the MonopolyGame instead of Board??
    private Board board;

    public DrawCommunityChestCardAction(Player player, Board board) {
        this.player = player;
        this.board = board;
    }

    @Override
    public void act() {
        CommunityChestCard communityChestCard = Board.drawCommunityChestCard();
        // ???
        Action action = communityChestCard.getAction();
        action.act();

        MonopolyGame.getActionLog().addMessage(player.getName() + " draws a community chance card");
    }
}
