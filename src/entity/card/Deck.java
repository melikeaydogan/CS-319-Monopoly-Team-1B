package card;

import control.GameMode;

import java.util.Random;

public class Deck {
    Card[] cards;
    boolean isChanceDeck;
    int gameSeed;
    private Random random;

    public Deck(GameMode gameMode, boolean isChanceDeck){
        //to do
    }
    public Deck(Deck savedDeck){
        this.cards = savedDeck.cards;
        this.isChanceDeck = savedDeck.isChanceDeck;
        this.gameSeed = savedDeck.gameSeed;
        this.random = savedDeck.random;
    }
    /*Card draw(){
        //??

    }*/
    void shuffle(){
        int size = cards.length;
        Card[] tmp = new Card[cards.length];
        int[] order = new int[cards.length];
        for(int i = 0; i<cards.length; i++) {
            order[i] = -1;
        }
        for(int i = 0; i<cards.length; i++) {
            int r = random.nextInt(cards.length);
            while( order[r] != -1 ){
                r++;
                if( r == cards.length ){
                    r = 0;
                }
            }
            order[r] = i;
            tmp[r] = cards[i];
        }
        cards = tmp;
    }
    boolean getIsChanceDeck(){
        return isChanceDeck;
    }
    /*void setIsChanceDeck( boolean isChanceDeck){
        this.isChanceDeck = isChanceDeck;
    }*/
}
