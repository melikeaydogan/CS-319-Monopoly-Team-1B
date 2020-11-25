package control.action;

import control.MonopolyGame;
import entity.Player;

public class PayAction implements Action{
    private Player player;
    private int amount;

    public PayAction(Player player, int amount) {
        this.player = player;
        this.amount = amount;
    }

    @Override
    public void act() {
        if ( player.getBalance() > amount) {
            player.giveMoney(amount);

            MonopolyGame.getActionLog().addMessage(player.getName() + " pays " + amount + "$");
        }
    }
}
