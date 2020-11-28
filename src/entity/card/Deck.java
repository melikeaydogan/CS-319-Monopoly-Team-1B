package entity.card;

import entity.card.Card;

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
            Reader reader = Files.newBufferedReader(Paths.get(filename));

            // convert JSON array to list of users
            cardlist = new Gson().fromJson(reader, new TypeToken<List<Card>>() {}.getType());

            // print users
            cardlist.forEach(System.out::println);

            for(int i = 0; i<cardlist.size(); i++){
                System.out.println(cardlist.get(i).id+" "+cardlist.get(i).instructions+" "+cardlist.get(i).cardType);
            }
            // close reader
            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Collections.shuffle(cardlist);//check whether it is null

        // now, cards will be put into a Queue
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

    /*public static void main(String[] args) {
        Deck deck = new Deck("chanceCard.json");

        for ( Card c : deck.getCards() )
            System.out.println(c); // Test GSON Library!!!
    }*///test

}
