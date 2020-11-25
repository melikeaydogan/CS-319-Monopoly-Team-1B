package control.action;

import control.MonopolyGame;
import entity.Player;

public class FreeMoveAction implements Action{
    private Player player;
    private int position;

    public FreeMoveAction(Player player, int position) {
        this.player = player;
        this.position = position;
    }

    @Override
    public void act() {
        if ( !player.isInJail() ) {
            player.setPosition(position);

            // maybe get tile name from position?
            MonopolyGame.getActionLog().addMessage(player.getName() + " directly moves to " + position);
        }
    }
}
