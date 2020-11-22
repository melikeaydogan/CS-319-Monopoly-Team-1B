package entity.card;

import control.GameMode;
import entity.card.Card;

import java.util.*;

public class Deck {
    Queue<Card> cards;
    boolean isChanceDeck;

    public Deck(Card[] cards, boolean isChanceDeck){
        // first, we are going to shuffle cards
        List<Card> cardList = Arrays.asList(cards);
        Collections.shuffle(cardList);
        cardList.toArray(cards);

        // now, cards will be put into a Queue
        this.cards = new LinkedList<Card>(Arrays.asList(cards));

        // finally, assign the isChanceDeck attribute
        this.isChanceDeck = isChanceDeck;
    }

    public Deck(Deck savedDeck){
        this.cards = new LinkedList<Card>(savedDeck.getCards());
        this.isChanceDeck = savedDeck.isChanceDeck;
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

    public boolean isChanceDeck() {
        return isChanceDeck;
    }

    public void setChanceDeck(boolean chanceDeck) {
        isChanceDeck = chanceDeck;
    }

}
