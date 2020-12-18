package control.action;

import control.MonopolyGame;
import control.PlayerController;
import entity.Player;
import network.MonopolyClient;

public class AddMoneyAction implements Action{
    private int playerId;
    private int amount;

    public AddMoneyAction(int playerId, int amount) {
        this.playerId = playerId;
        this.amount = amount;
    }

    public AddMoneyAction() {
    }

    @Override
    public void act() {
        Player player = PlayerController.getById(playerId);
        player.addMoney(amount);

        MonopolyGame.getActionLog().addMessage(player.getName() + " gets " + amount + "$ \n");

    }
}
