package entity;

import tile.Tile;
import card.*;

public class Board {
    Tile[] tiles;
    Property[] properties;
    GameMode gameMode;
    Deck chanceDeck;
    Deck communityChestDeck;
    //==Player[] players;
    //==Dice dice;
    //Image boardImage;
    //TradeManager tradeManager;

    public Board(GameMode gameMode){
        //read tiles
        //read properties
        this.gameMode = gameMode;
        //create chanceDeck
        //create communityChestDeck
    }
    public Board(Board savedBoard){
        this.tiles = savedBoard.tiles;
        this.properties = savedBoard.properties;
        this.gamemode = savedBoard.gamemode;
        this.chanceDeck = savedBoard.chanceDeck;
        this.communityChestDeck = savedBoard.communityChestDeck;

    }

    public GameMode getGameMode(){
        return gameMode;
    }
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
    //public int payRent(int playerID, int propertyID){}
    //public Image getTileImage( int tilePos ){}
    //public Image getBoardImage(){}
    //+rollDice(rollDice) : DiceResult
    //+getPlayers() : Player []
    //+getTradeManager() : TradeManager
}
