package control.action;

import control.MonopolyGame;
import control.PlayerController;
import entity.Player;
import network.MonopolyClient;

public class MoveAction implements Action{

    private int playerId;
    private int moveAmount;

    public MoveAction(int playerId, int moveAmount) {
        this.playerId = playerId;
        this.moveAmount = moveAmount;
    }

    public MoveAction() {
    }

    @Override
    public void act() {
        Player player = PlayerController.getById(playerId);
        if ( !player.isInJail() ) {
            boolean passedTheGoTile = player.move(moveAmount);
            MonopolyGame.getActionLog().addMessage(player.getName() + " moves " + moveAmount
                    + " squares (current position: " + player.getPosition() + ")\n");

            if (passedTheGoTile) {
                new PassAction(player.getPlayerId()).act();
            }
        }

    }
}
