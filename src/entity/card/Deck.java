package entity.card;

//import control.GameMode;
import entity.card.Card;

import java.util.*;

public class Deck {
    Queue<Card> cards;

    public Deck(Card[] cards){
        // first, we are going to shuffle cards
        List<Card> cardList = Arrays.asList(cards);
        Collections.shuffle(cardList);
        cardList.toArray(cards);

        // now, cards will be put into a Queue
        this.cards = new LinkedList<Card>(Arrays.asList(cards));

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

    public static void main(String[] args) {
        ArrayList<ChanceCard> chanceCards = new ArrayList<>();
        chanceCards.add(new ChanceCard(1, "You won 200$ from the lottery"));
        chanceCards.add(new ChanceCard(2, "You went to jail because of theft!"));

        Deck deck = new Deck(chanceCards.toArray(new ChanceCard[0]));

        for ( Card c : deck.getCards() )
            System.out.println(c); // Test GSON Library!!!
    }

}
