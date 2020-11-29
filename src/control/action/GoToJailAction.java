package control.action;

import control.MonopolyGame;
import entity.Player;

public class GoToJailAction implements Action{
    private Player player; // Does Board need a Jail class???

    public GoToJailAction(Player player) {
        this.player = player;
    }

    @Override
    public void act() {
        player.setInJail(true);
        player.setPosition(10);
        MonopolyGame.getActionLog().addMessage(player.getName() + " goes to jail \n");
    }
}
