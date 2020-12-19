package control.action;

import control.ActionLog;
import entity.Player;
import entity.dice.Dice;
import entity.dice.DiceResult;
import entity.dice.SpeedDieResult;
import gui.GameScreenController;
import network.MonopolyClient;

public class RollDiceAction implements Action{

    public DiceResult diceResult;
    public Player player;

    public RollDiceAction() {
    }


    public RollDiceAction(Player player, DiceResult diceResult) {
        this.player = player;
        this.diceResult = diceResult;
    }

    @Override
    public void act() {
        String msg = "";
        if (diceResult.getSpeedDieResult() == SpeedDieResult.NONE)
            msg = player.getName() + " rolls dice and gets " + diceResult.getFirstDieResult()
                    + ", " + diceResult.getSecondDieResult() + "\n";
        else
            msg = player.getName() + " rolls dice and gets " + diceResult.getFirstDieResult()
                    + ", " + diceResult.getSecondDieResult()
                    + ", " + diceResult.getSpeedDieResult().toString() + "\n";
        ActionLog.getInstance().addMessage(msg);

    }

}
