package control.action;

import control.MonopolyGame;
import entity.Player;
import network.MonopolyClient;

public class TransferAction implements Action{
    private Player player;
    private Player player2;
    private int amount;

    public TransferAction(Player player, Player player2, int amount) {
        this.player = player;
        this.player2 = player2;
        this.amount = amount;
    }

    public TransferAction() {
    }

    @Override
    public void act() {
        if ( player.getBalance() >= amount ) {
            player.transferMoney(player2, amount);

            MonopolyGame.getActionLog().addMessage(player.getName() + " gives " + amount + "$" + " to "
                    + player2.getName() + "\n");

            MonopolyClient.getInstance().sendAction(this);
        }
    }
}
