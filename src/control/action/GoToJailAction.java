package control.action;

import control.MonopolyGame;
import control.PlayerController;
import entity.Player;
import network.MonopolyClient;

public class GoToJailAction implements Action{
    private int playerId;

    public GoToJailAction(int playerId) {
        this.playerId = playerId;
    }

    public GoToJailAction() {
    }

    @Override
    public void act() {
        Player player = PlayerController.getById(playerId);
        player.setInJail(true);
        player.setPosition(10);
        MonopolyGame.getActionLog().addMessage(player.getName() + " goes to jail \n");

    }
}
