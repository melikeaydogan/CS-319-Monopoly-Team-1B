package control.action;

import control.MonopolyGame;
import entity.Player;
import network.MonopolyClient;

public class FreeMoveAction implements Action{
    private Player player;
    private int position;

    public FreeMoveAction(Player player, int position) {
        this.player = player;
        this.position = position;
    }

    public FreeMoveAction() {
    }

    @Override
    public void act() {
        if ( !player.isInJail() ) {
            int moveAmount = 0;
            if ( position < player.getPosition() ) {
                position = position + 40;
                moveAmount = position - player.getPosition();
            }
            else {
                moveAmount = position - player.getPosition();
            }

            new MoveAction(player.getPlayerId(), moveAmount).act(); // causes recursion??
        }
    }
}
