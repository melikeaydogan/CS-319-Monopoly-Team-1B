package control.action;

import control.MonopolyGame;
import entity.Player;
import network.MonopolyClient;

public class GetOutOfJailAction implements Action{
    private Player player;

    public GetOutOfJailAction(Player player) {
        this.player = player;
    }

    public GetOutOfJailAction() {
    }

    @Override
    public void act() {
        if ( player.isInJail() ) {
            player.setInJail(false);
            MonopolyGame.getActionLog().addMessage(player.getName() + " gets out of jail! \n");

        }
    }
}

