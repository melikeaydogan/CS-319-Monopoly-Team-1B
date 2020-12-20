package entity.card;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Deck {
    Queue<Card> cards;

    public Deck(String filename){
        List<Card> cardlist = null;
        try {
            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    ClassLoader.getSystemClassLoader()
                            .getResourceAsStream("entity/json/" + filename)));

            // convert JSON array to list of cards
            cardlist = new Gson().fromJson(br, new TypeToken<List<Card>>() {}.getType());

            // close reader
            br.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // shuffle cards in the beginning
        if ( cardlist != null )
                Collections.shuffle(cardlist);

        // cards will be put into a LinkedList
        this.cards = new LinkedList<Card>(cardlist);
    }

    public Deck(Deck savedDeck){
        this.cards = new LinkedList<Card>(savedDeck.getCards());
    }

    public Card draw() {
        Card drawedCard = cards.remove(); // draw the card and save it
        cards.add(drawedCard); // add the saved card to the end
        return drawedCard; // return the card
    }

    public Queue<Card> getCards() {
        return cards;
    }

    public void setCards(Queue<Card> cards) {
        this.cards = cards;
    }

    // test function
    public static void main(String[] args) {
        Deck chanceCardDeck = new Deck("chanceCard.json");
        Deck communityChestChardDeck = new Deck("communityChestCard.json");

    }

}
