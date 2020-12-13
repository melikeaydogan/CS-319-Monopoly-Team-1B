package control.action;

import control.MonopolyGame;
import entity.Player;
import network.MonopolyClient;

public class RemoveMoneyAction implements Action {
    private Player player;
    private int amount;

    public RemoveMoneyAction(Player player, int amount) {
        this.player = player;
        this.amount = amount;
    }

    public RemoveMoneyAction() {
    }

    @Override
    public void act() {
        if (player.getBalance() >= amount)
            player.removeMoney(amount);

        MonopolyGame.getActionLog().addMessage(player.getName() + " pays " + amount + "$ \n");

        MonopolyClient.getInstance().sendAction(this);
    }
}
