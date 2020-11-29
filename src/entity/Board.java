package entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import control.action.BuyPropertyAction;
import entity.card.Card;
import entity.card.Deck;
import entity.property.Property;
import entity.tile.Tile;
//import tile.Tile;
//import card.*;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Board {
    ArrayList<Tile> tiles; // convert to arraylist
    ArrayList<Property> properties; // convert to arraylist
    //GameMode gameMode;
    Deck chanceDeck;
    Deck communityChestDeck;
    //Image boardImage;
    //TradeManager tradeManager;

    //public Board(GameMode gameMode){
        //read tiles
        //read properties
        //this.gameMode = gameMode;
        //create chanceDeck
        //create communityChestDeck
    //}

    public Board(ArrayList<Tile> tiles, ArrayList<Property> properties, Deck chanceDeck, Deck communityChestDeck) throws IOException {
        this.tiles = tiles;
        this.properties = properties;
        this.chanceDeck = chanceDeck;
        this.communityChestDeck = communityChestDeck;

        initializeProperties("properties.json");
        initializeCommunityChestCardDeck("communityChestCard.json");
        initializeChanceCardDeck("chanceCard.json");
    }

    public Board() throws IOException {
        initializeTiles("tiles.json");
        initializeProperties("properties.json");
        initializeCommunityChestCardDeck("communityChestCard.json");
        initializeChanceCardDeck("chanceCard.json");
    }

    public Board(Board savedBoard){
        this.tiles = savedBoard.tiles;
        this.properties = savedBoard.properties;
        //this.gamemode = savedBoard.gamemode;
        this.chanceDeck = savedBoard.chanceDeck;
        this.communityChestDeck = savedBoard.communityChestDeck;
    }

    //public GameMode getGameMode(){
        //return gameMode;
    //}
    //public boolean purchaseProperty( int playerID){}
    //public boolean canPurchaseProperty( int playerID){}
    //public boolean addBuilding( int playerID){}
    //public boolean canAddBuilding( int playerID){}
    //public boolean sellProperty( int PlayerID ){}
    public void initializeTiles(String fileName) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Tile.class, new Tile.CustomDeserializer());
        Gson customGson = builder.create();

        Reader reader = Files.newBufferedReader(Paths.get(fileName));

        // convert JSON array to list of users
        tiles = customGson.fromJson(reader, new TypeToken<List<Tile>>() {}.getType());

        for (Tile t : tiles)
            System.out.println("Added a new Tile --> " + t);
    }

    public void initializeProperties(String fileName) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Property.class, new Property.CustomDeserializer());
        Gson customGson = builder.create();

        Reader reader = Files.newBufferedReader(Paths.get(fileName));

        // convert JSON array to list of users
        properties = customGson.fromJson(reader, new TypeToken<List<Property>>() {}.getType());

        for (Property p : properties)
            System.out.println("Added a new property --> " + p);
    }

    public void initializeChanceCardDeck(String filename) {
        chanceDeck = new Deck(filename);

    }

    public Property getPropertyById(int id) {
        for (Property p : properties)
            if (p.getId() == id)
                return p;
        return null;
    }

    public void initializeCommunityChestCardDeck(String filename) {
        communityChestDeck = new Deck(filename);
    }

    public Card drawChanceCard(){
        return chanceDeck.draw();
    }

    public Card drawCommunityChestCard(){
        return communityChestDeck.draw();
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    public ArrayList<Property> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<Property> properties) {
        this.properties = properties;
    }

    public Deck getChanceDeck() {
        return chanceDeck;
    }

    public void setChanceDeck(Deck chanceDeck) {
        this.chanceDeck = chanceDeck;
    }

    public Deck getCommunityChestDeck() {
        return communityChestDeck;
    }

    public void setCommunityChestDeck(Deck communityChestDeck) {
        this.communityChestDeck = communityChestDeck;
    }

    //public int payRent(int playerID, int propertyID){}
    //public Image getTileImage( int tilePos ){}
    //public Image getBoardImage(){}
    //+rollDice(rollDice) : DiceResult
    //+getPlayers() : Player []
    //+getTradeManager() : TradeManager

    public static void main(String[] args) throws IOException { // works!!!
        Board board = new Board();

        //board.initializeProperties("properties.json");

        //board.initializeCommunityChestCardDeck("communityChestCard.json");
        //board.initializeChanceCardDeck("chanceCard.json");
        //board.initializeTiles("tiles.json");

        for (Property p : board.getProperties()) {
            System.out.println("Property type: " + p.getClass().getSimpleName() + " / Details: " + p);
        }

        Player player = new Player(1, "Mehmet", Player.Token.BATTLESHIP, 1);

        System.out.println("Balance: " + player.getBalance());
        System.out.println(board.getProperties().get(7));

        new BuyPropertyAction(board.getProperties().get(7), player).act();

        System.out.println("Balance: " + player.getBalance());
        System.out.println("Properties: " + player.getProperties());
    }
}
