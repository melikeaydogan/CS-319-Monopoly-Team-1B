package control.action;

import control.MonopolyGame;
import control.PlayerController;
import entity.Player;
import network.MonopolyClient;

public class PassAction implements Action {
    private int playerId;

    public PassAction(int playerId) {
        this.playerId = playerId;
    }

    public PassAction() {
    }

    @Override
    public void act() {
        Player player = PlayerController.getById(playerId);
        new AddMoneyAction(player.getPlayerId(), 20_000).act();

        MonopolyGame.getActionLog().addMessage(player.getName() + " passes from the starting point \n" );
    }

}
