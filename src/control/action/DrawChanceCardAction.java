package control.action;

import control.MonopolyGame;
import entity.Board;
import entity.Player;
import entity.card.Card;

public class DrawChanceCardAction implements Action{
    Player player;
    Card card;

    public DrawChanceCardAction(Player player, Card card) {
        this.player = player;
        this.card = card;
    }

    @Override
    public void act() {
        MonopolyGame.getActionLog().addMessage(player.getName() + " draws the chance card: " + card + "\n" );
    }
}

// is this action necessary??
