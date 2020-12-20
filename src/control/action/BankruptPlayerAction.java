package control.action;

import control.MonopolyGame;
import control.PlayerController;
import entity.Player;

public class BankruptPlayerAction implements Action{
    int playerId;

    public BankruptPlayerAction(int playerId) {
        this.playerId = playerId;
    }

    public BankruptPlayerAction() {
    }

    @Override
    public void act() {
        Player p = PlayerController.getById(playerId);
        p.setBankrupt(true);

        MonopolyGame.getActionLog().addMessage(p.getName() + " gets bankrupt and leaves the game :( \n");
    }
}
