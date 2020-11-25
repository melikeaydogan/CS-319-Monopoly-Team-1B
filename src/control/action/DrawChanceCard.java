package control.action;

import control.MonopolyGame;
import entity.Player;

public class DrawChanceCard implements Action{
    Player player;

    @Override
    public void act() {


        MonopolyGame.getActionLog().addMessage(player.getName() + " draws a chance card" );
    }
}
