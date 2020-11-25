package control.action;

import control.MonopolyGame;
import entity.Player;

public class GetOutOfJailAction implements Action{
    private Player player;

    public GetOutOfJailAction(Player player) {
        this.player = player;
    }

    @Override
    public void act() {
        if ( player.isInJail() ) {
            player.setInJail(false);
            MonopolyGame.getActionLog().addMessage(player.getName() + " gets out of jail!");
        }
    }
}

