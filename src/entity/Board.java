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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Board {
    ArrayList<Tile> tiles;
    static ArrayList<Property> properties;
    Deck chanceDeck;
    Deck communityChestDeck;

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
        System.out.println("Board initialization complete!");
    }

    public Board(Board savedBoard){
        this.tiles = savedBoard.tiles;
        this.properties = savedBoard.getProperties();
        this.chanceDeck = savedBoard.chanceDeck;
        this.communityChestDeck = savedBoard.communityChestDeck;
    }

    public void initializeTiles(String fileName) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Tile.class, new Tile.CustomDeserializer());
        Gson customGson = builder.create();

        // create reader
        BufferedReader br = new BufferedReader(new InputStreamReader(
                ClassLoader.getSystemClassLoader()
                        .getResourceAsStream("entity/json/" + fileName)));

        // convert JSON array to list of tiles
        tiles = customGson.fromJson(br, new TypeToken<List<Tile>>() {}.getType());

        //close the reader
        br.close();

    }

    public void initializeProperties(String fileName) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Property.class, new Property.CustomDeserializer());
        Gson customGson = builder.create();

        // create reader
        BufferedReader br = new BufferedReader(new InputStreamReader(
                ClassLoader.getSystemClassLoader()
                        .getResourceAsStream("entity/json/" + fileName)));

        // convert JSON array to list of properties
        properties = customGson.fromJson(br, new TypeToken<List<Property>>() {}.getType());

        // close the reader
        br.close();

    }

    public void initializeChanceCardDeck(String filename) {
        chanceDeck = new Deck(filename);
    }

    public static Property getPropertyById(int id) {
        for (Property p : properties)
            if (p.getId() == id)
                return p;

        // if there are no property with the given id return null
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

    /**
     * Checks if there is any property left in the bank
     * @return true if all properties are owned by any player
     */
    public boolean isAllOwned() {
        for (Property p : properties) {
            if (!p.isOwned())
                return false;
        }
        return true;
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

}
