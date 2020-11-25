package control.action;

import control.MonopolyGame;
import entity.dice.DiceResult;
import entity.Player;

public class RollDiceAction implements Action {
    private Player player; // ToDo implement after Board implementation
    private MonopolyGame monopolyGame;

    public RollDiceAction(Player player, MonopolyGame monopolyGame) {
        this.monopolyGame = monopolyGame;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public MonopolyGame getMonopolyGame() {
        return monopolyGame;
    }

    public void setMonopolyGame(MonopolyGame monopolyGame) {
        this.monopolyGame = monopolyGame;
    }

    @Override
    public void act() {
        DiceResult diceResult = monopolyGame.rollDice();

        MonopolyGame.getActionLog().addMessage(player.getName() + " rolls dice and gets " + diceResult.getFirstDieResult()
         + ", " + diceResult.getSecondDieResult());
    }
}
