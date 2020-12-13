package control.action;

import control.MonopolyGame;
import entity.Player;
import network.MonopolyClient;

public class MoveAction implements Action{

    private Player player;
    private int moveAmount;

    public MoveAction(Player player, int moveAmount) {
        this.player = player;
        this.moveAmount = moveAmount;
    }

    public MoveAction() {
    }

    @Override
    public void act() {
        if ( !player.isInJail() ) {
            boolean passedTheGoTile = player.move(moveAmount);
            MonopolyGame.getActionLog().addMessage(player.getName() + " moves " + moveAmount
                    + " squares (current position: " + player.getPosition() + ")\n");

            MonopolyClient.getInstance().sendAction(this); // might cause recursion

            if (passedTheGoTile) {
                new PassAction(player).act();
            }
        }

    }
}
