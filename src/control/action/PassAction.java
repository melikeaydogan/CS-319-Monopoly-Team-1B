package control.action;

import control.MonopolyGame;
import entity.Player;

public class PassAction implements Action {
    private Player player;

    public PassAction(Player player) {
        this.player = player;
    }

    @Override
    public void act() {
        player.addMoney(2000);

        MonopolyGame.getActionLog().addMessage(player.getName() + " passes from the starting point" );
    }

    /*
    if player
        new RollDiceAction(playerController.getActivePlayer()).act();
        new MoveAction(playerController.getActivePlayer()

     */
}
