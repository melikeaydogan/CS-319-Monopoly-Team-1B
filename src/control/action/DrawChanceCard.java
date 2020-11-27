package control.action;

import control.MonopolyGame;
import entity.Board;
import entity.Player;
import entity.card.Card;

public class DrawChanceCard implements Action{
    Player player;

    public DrawChanceCard(Player player) {
        this.player = player;
    }

    @Override
    public void act() {
        MonopolyGame.getActionLog().addMessage(player.getName() + " draws a chance card" );
    }
}

// is this action necessary??
