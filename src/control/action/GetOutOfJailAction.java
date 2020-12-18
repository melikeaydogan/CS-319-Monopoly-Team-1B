package control.action;

import control.MonopolyGame;
import control.PlayerController;
import entity.Player;
import network.MonopolyClient;

public class GetOutOfJailAction implements Action{
    private int playerId;

    public GetOutOfJailAction(int playerId) {
        this.playerId = playerId;
    }

    public GetOutOfJailAction() {
    }

    @Override
    public void act() {
        Player player = PlayerController.getById(playerId);
        if ( player.isInJail() ) {
            player.setInJail(false);
            MonopolyGame.getActionLog().addMessage(player.getName() + " gets out of jail! \n");

        }
    }
}

