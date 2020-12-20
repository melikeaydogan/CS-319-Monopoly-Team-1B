package control.action;

import control.MonopolyGame;
import entity.Board;
import entity.Player;
import entity.card.Card;
import network.MonopolyClient;

public class DrawChanceCardAction implements Action{
    Player player;
    Card card;

    public DrawChanceCardAction(Player player, Card card) {
        this.player = player;
        this.card = card;
    }

    public DrawChanceCardAction() {
    }

    @Override
    public void act() {
        MonopolyGame.getActionLog().addMessage(player.getName() + " draws the chance card: " + card + "\n" );
    }
}
