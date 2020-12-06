package control.action;

import control.MonopolyGame;
import entity.Player;

public class AddMoneyAction implements Action{
    private Player player;
    private int amount;

    public AddMoneyAction(Player player, int amount) {
        this.player = player;
        this.amount = amount;
    }

    public AddMoneyAction() {
    }

    @Override
    public void act() {
        player.addMoney(amount);

        MonopolyGame.getActionLog().addMessage(player.getName() + " gets " + amount + "$ \n");
    }
}
