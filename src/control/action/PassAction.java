package control.action;

import control.MonopolyGame;
import entity.Player;

public class PassAction implements Action {
    private Player player;

    public PassAction(Player player) {
        this.player = player;
    }

    public PassAction() {
    }

    @Override
    public void act() {
        new AddMoneyAction(player, 20_000).act();

        MonopolyGame.getActionLog().addMessage(player.getName() + " passes from the starting point \n" );
    }

}
