package control.action;

import control.MonopolyGame;
import entity.Player;

public class TakeAction implements Action {
    private Player player;
    private int amount;

    public TakeAction(Player player, int amount) {
        this.player = player;
        this.amount = amount;
    }

    @Override
    public void act() {
        player.takeMoney(amount);

        MonopolyGame.getActionLog().addMessage(player.getName() + " takes " + amount + "$");
    }
}
