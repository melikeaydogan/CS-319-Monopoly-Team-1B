package control.action;

import control.MonopolyGame;
import control.PlayerController;
import entity.Player;
import network.MonopolyClient;

public class FreeMoveAction implements Action{
    private int playerId;
    private int position;

    public FreeMoveAction(int playerId, int position) {
        this.playerId = playerId;
        this.position = position;
    }

    public FreeMoveAction() {
    }

    @Override
    public void act() {
        Player player = PlayerController.getById(playerId);
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
