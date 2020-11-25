package control.action;

import entity.Player;

public class MonopolyAction {
    enum MonopolyActionType {
        PASS,
        ROLL_DICE,
        MOVE,
        PAY,
        TRANSFER,
        TAKE,
        FREE_MOVE,
        BUY_PROPERTY,
        SELL_PROPERTY,
        ADD_BUILDING,
        DRAW_COMMUNITY_CHEST_CARD,
        DRAW_CHANCE_CARD,
        GO_TO_JAIL,
        GET_OUT_OF_JAIL
    }

    private Player player;
    private Player player2;
    private MonopolyActionType monopolyActionType;
    private int amount;

    public MonopolyAction(Player player, MonopolyActionType monopolyActionType) {
        this.player = player;
        this.monopolyActionType = monopolyActionType;
        amount = -1; // action does not require an amount
        player2 = null; // it is an action for one person
    }

    public MonopolyAction(Player player, MonopolyActionType monopolyActionType, int amount) {
        this.player = player;
        this.monopolyActionType = monopolyActionType;
        this.amount = amount;
        player2 = null; // it is an action for one person
    }

    public MonopolyAction(Player player, MonopolyActionType monopolyActionType, int amount, Player player2) {
        this.player = player;
        this.monopolyActionType = monopolyActionType;
        this.amount = amount;
        this.player2 = player2;
    }

    public MonopolyAction(MonopolyAction monopolyAction) {
        player = monopolyAction.getPlayer();
        monopolyActionType = monopolyAction.getMonopolyActionType();
        amount = monopolyAction.getAmount();
        player2 = monopolyAction.getPlayer2();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public MonopolyActionType getMonopolyActionType() {
        return monopolyActionType;
    }

    public void setMonopolyActionType(MonopolyActionType monopolyActionType) {
        this.monopolyActionType = monopolyActionType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public String toString() {
        if (player2 == null) {
            return "Player " + player.getName() + " --> " + monopolyActionType;
        }
        return "Player " + player.getName() + " and Player " + player2.getName() + " --> " + monopolyActionType;
    }

}
