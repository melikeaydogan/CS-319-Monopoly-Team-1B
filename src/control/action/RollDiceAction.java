package control.action;

import control.ActionLog;
import entity.Player;
import entity.dice.Dice;
import entity.dice.DiceResult;

public class RollDiceAction implements Action{
    boolean isSpeedDie;
    Dice dice;
    DiceResult diceResult;
    Player player;

    public RollDiceAction() {
    }

    public RollDiceAction(Player player, boolean isSpeedDie, Dice dice, DiceResult diceResult) {
        this.isSpeedDie = isSpeedDie;
        this.dice = dice;
        this.diceResult = diceResult;
        this.player = player;
    }

    @Override
    public void act() {
        diceResult = dice.roll(isSpeedDie);

        ActionLog.getInstance().addMessage(player.getName() + " rolls dice and gets " + diceResult.getFirstDieResult()
                + ", " + diceResult.getSecondDieResult() + "\n");
    }

}
