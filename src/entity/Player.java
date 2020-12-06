package entity;

import entity.property.Building;
import entity.property.Dorm;
import entity.property.Property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
    private int jailTurnCount;
    private int teamNumber; // ToDo maybe add a separate Team class?
    //private ArrayList<Property> properties;
    private HashMap<String, ArrayList<Property>> properties;
    private boolean inJail;

    public Player(int playerId, String name, Token token, int teamNumber) {
        this.playerId = playerId;
        this.name = name;
        balance = 150_000;
        this.token = token;
        position = 0;
        bankrupt = false;
        doubleCounter = 0;
        jailTurnCount = 0;
        inJail = false;
        this.teamNumber = teamNumber;
        properties = new HashMap<>(3);
        properties.put("DORM", new ArrayList<Property>(4));
        properties.put("BUILDING", new ArrayList<Property>(22));
        properties.put("FACILITY", new ArrayList<Property>(2));
    }

    public Player(String name, Token token, int teamNumber) {
        // this.playerId = playerId; set id from server side
        this.name = name;
        balance = 150_000;
        this.token = token;
        position = 0;
        bankrupt = false;
        doubleCounter = 0;
        jailTurnCount = 0;
        inJail = false;
        this.teamNumber = teamNumber;
        properties = new HashMap<>(3);
        properties.put("DORM", new ArrayList<Property>(4));
        properties.put("BUILDING", new ArrayList<Property>(22));
        properties.put("FACILITY", new ArrayList<Property>(2));
    }

    public Player(Player player) {
        playerId = player.getPlayerId();
        name = player.getName();
        balance = player.getBalance();
        token = player.getToken();
        position = player.getPosition();
        bankrupt = player.isBankrupt();
        doubleCounter = player.getDoubleCounter();
        jailTurnCount = player.getJailTurnCount();
        teamNumber = player.getTeamNumber();
        properties = new HashMap<String, ArrayList<Property>>(player.getProperties());
        inJail = player.isInJail();
    }

    public Player() {
    }

    public void removeMoney(int amount) {
        balance = balance - amount;
    }

    public void transferMoney(Player player, int amount) { // ToDo add this method to the diagram
        player.addMoney(amount);
        removeMoney(amount);
    }

    public void addMoney(int amount) {
        balance = balance + amount;
    }

    public boolean move(int tiles) {
        position = position + tiles;
        if (position >= 40) {
            position = position % 40; // there are 40 squares
            return true; // player passes the "go" tile
        }
        else if (position < 0) {
            position = position + 40; // position is negative, player moved backwards
        }
        return false;
    }

    public void freeMove(int tile) {
        position = tile;
    }

    /*public boolean useJailFreeCard() { // includes the hasJailFreeCard() function in the diagram
        if ( jailFreeCardAmount > 0 && inJail ) {
            jailFreeCardAmount = jailFreeCardAmount - 1;
            inJail = false;
            return true;
        }
        return false;
    }*/

    public boolean getOutFromJail(int fee) {
        if ( balance > fee && inJail ) {
            balance = balance - fee;
            inJail = false;
            return true;
        }
        return false;
    }

    public boolean isInSameTeam(Player player) {
         return teamNumber == player.getTeamNumber();
    }

    public boolean isComplete(Building building) {
        int sameColorCount = 0;

        for (Property property : properties.get("BUILDING")) {
            Building building1 = (Building) property;

            if ( building.getColor().equals(building1.getColor()) && building.getId() != building1.getId())
                sameColorCount++;
        }

        if ( sameColorCount == 2 ) {
            return true;
        }
        else return building.getColor().equals("BROWN") && sameColorCount == 1;
    }

    public HashMap<String, ArrayList<Property>> getProperties() {
        return properties;
    }

    public void setProperties(HashMap<String, ArrayList<Property>> properties) {
        this.properties = new HashMap<>(properties); // Full clone!
    }

    public String getTokenName() {
        if (token == Token.BATTLESHIP)
            return "Battleship";
        else if (token == Token.CAR)
            return "Car";
        else if (token == Token.SCOTTISH_TERRIER)
            return "Scottish_Terrier";
        else if (token == Token.IRON)
            return "Iron";
        else if (token == Token.SHOE)
            return "Shoe";
        else if (token == Token.THIMBLE)
            return "Thimble";
        else if (token == Token.TOP_HAT)
            return "Top_Hat";
        else if (token == Token.WHEELBARROW)
            return "Wheelbarrow";
        return "";
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

    public int getJailTurnCount() {
        return jailTurnCount;
    }

    public void setJailTurnCount(int jailTurnCount) {
        this.jailTurnCount = jailTurnCount;
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

    public String toString() {
        return "Player --> ID: " + playerId + ", Name: " + name;
    }

}

// Starting balance??
// 4M is equal to 40000 in our game
