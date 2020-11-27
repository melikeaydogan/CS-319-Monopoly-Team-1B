package entity;

import entity.card.Card;
import entity.card.ChanceCard;
import entity.card.Deck;
import entity.property.Property;
import entity.tile.Tile;
//import tile.Tile;
//import card.*;

import java.util.ArrayList;

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

    public Board(ArrayList<Tile> tiles, ArrayList<Property> properties, Deck chanceDeck, Deck communityChestDeck) {
        this.tiles = tiles;
        this.properties = properties;
        this.chanceDeck = chanceDeck;
        this.communityChestDeck = communityChestDeck;
    }

    public Board() {

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
}
