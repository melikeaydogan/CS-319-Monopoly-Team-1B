package entity;

import java.util.ArrayList;

public class Player {
    public enum Token {
        SCOTTISH_TERRIER,
        SHOE,
        BATTLESHIP,
        WHEELBARROW,
        IRON,
        CAR,
        TOP_HAT,
        THIMBLE
    }

    private int playerId;
    private String name;
    private int balance;
    private Token token;
    private int position;
    private boolean bankrupt;
    private int doubleCounter;
    private int jailFreeCardAmount;
    private int teamNumber; // ToDo maybe add a separate Team class?
    private ArrayList<Property> properties; // ToDo add properties to the class diagram
    private boolean inJail; // ToDo add this to the class diagram

    public Player(int playerId, String name, Token token, int teamNumber) {
        this.playerId = playerId;
        this.name = name;
        balance = 2000; // ToDo check the balance from the rule book
        this.token = token;
        position = 0;
        bankrupt = false;
        doubleCounter = 0;
        jailFreeCardAmount = 0;
        inJail = false;
        this.teamNumber = teamNumber;
        properties = new ArrayList<Property>(28);
    }

    public Player(Player player) {
        playerId = player.getPlayerId();
        name = player.getName();
        balance = player.getBalance();
        token = player.getToken();
        position = player.getPosition();
        bankrupt = player.isBankrupt();
        doubleCounter = player.getDoubleCounter();
        jailFreeCardAmount = player.getJailFreeCardAmount();
        teamNumber = player.getTeamNumber();
        properties = new ArrayList<Property>(player.getProperties()); // Full clone!
        inJail = player.isInJail();
    }

    public void giveMoney(int amount) {
        balance = balance - amount;
    }

    public void transferMoney(Player player, int amount) { // ToDo add this method to the diagram
        player.takeMoney(amount);
        giveMoney(amount);
    }

    public void takeMoney(int amount) {
        balance = balance + amount;
    }

    public void move(int tiles) {
        position = position + tiles;
        position = position % 40; // there are 40 squares
    }

    public void freeMove(int tile) {
        position = tile;
    }

    public boolean useJailFreeCard() { // includes the hasJailFreeCard() function in the diagram
        if ( jailFreeCardAmount > 0 && inJail ) {
            jailFreeCardAmount = jailFreeCardAmount - 1;
            inJail = false;
            return true;
        }
        return false;
    }

    public boolean getOutFromJail(int fee) { // ToDo change the name from getOut()
        if ( balance > fee && inJail ) {
            balance = balance - fee;
            inJail = false;
            return true;
        }
        return false;
    }

    public boolean isInSameTeam(Player player) { // ToDo change the name from sameTeam() in the diagram
         return teamNumber == player.getTeamNumber();
    }

    public boolean isComplete(int colorId) { // ToDo enumerate color in the property class
        return false; // ToDo complete the method
    }

    public ArrayList<Property> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<Property> properties) {
        this.properties = new ArrayList<Property>(properties); // Full clone!
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isBankrupt() {
        return bankrupt;
    }

    public void setBankrupt(boolean bankrupt) {
        this.bankrupt = bankrupt;
    }

    public int getDoubleCounter() {
        return doubleCounter;
    }

    public void setDoubleCounter(int doubleCounter) { // replaces the increaseDouble() in the diagram
        this.doubleCounter = doubleCounter;
    }

    public int getJailFreeCardAmount() {
        return jailFreeCardAmount;
    }

    public void setJailFreeCardAmount(int jailFreeCard) {
        this.jailFreeCardAmount = jailFreeCard;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    public boolean isInJail() {
        return inJail;
    }

    public void setInJail(boolean inJail) { // replaces the goToJail() in the diagram
        this.inJail = inJail;
    }

}
