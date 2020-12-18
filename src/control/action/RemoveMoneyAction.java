package control.action;

import control.MonopolyGame;
import control.PlayerController;
import entity.Player;
import network.MonopolyClient;

public class RemoveMoneyAction implements Action {
    private int playerId;
    private int amount;

    public RemoveMoneyAction(int playerId, int amount) {
        this.playerId = playerId;
        this.amount = amount;
    }

    public RemoveMoneyAction() {
    }

    @Override
    public void act() {
        Player player = PlayerController.getById(playerId);
        if (player.getBalance() >= amount)
            player.removeMoney(amount);

        MonopolyGame.getActionLog().addMessage(player.getName() + " pays " + amount + "$ \n");
    }
}
