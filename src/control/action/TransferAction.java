package control.action;

import control.MonopolyGame;
import control.PlayerController;
import entity.Player;
import network.MonopolyClient;

public class TransferAction implements Action{
    private int playerId;
    private int playerId2;
    private int amount;

    public TransferAction(int playerId, int playerId2, int amount) {
        this.playerId = playerId;
        this.playerId2 = playerId2;
        this.amount = amount;
    }

    public TransferAction() {
    }

    @Override
    public void act() {
        Player player = PlayerController.getById(playerId);
        Player player2 = PlayerController.getById(playerId2);

        if ( player.getBalance() >= amount ) {
            player.transferMoney(player2, amount);

            MonopolyGame.getActionLog().addMessage(player.getName() + " gives " + amount + "$" + " to "
                    + player2.getName() + "\n");

        }
    }
}
