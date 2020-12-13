package control.action;

import control.ActionLog;
import entity.Player;
import entity.dice.Dice;
import entity.dice.DiceResult;
import network.MonopolyClient;

public class RollDiceAction implements Action{

    DiceResult diceResult;
    Player player;

    public RollDiceAction() {
    }

    public RollDiceAction(Player player, DiceResult diceResult) {
        this.diceResult = diceResult;
        this.player = player;
    }

    @Override
    public void act() {
        ActionLog.getInstance().addMessage(player.getName() + " rolls dice and gets " + diceResult.getFirstDieResult()
                + ", " + diceResult.getSecondDieResult() + "\n");

        MonopolyClient.getInstance().sendAction(this);
    }

}
