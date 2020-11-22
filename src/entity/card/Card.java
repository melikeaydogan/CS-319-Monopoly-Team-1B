package card;

import control.MonopolyAction;
import control.GameMode;

public class Card {
    int id;
    String instructions;
    MonopolyAction action;
    public Card(){
        id = -1;
        instructions = "";
        //action???
    }
    public Card(Card c){
        this.id = c.id;
        this.instructions = c.instructions;
        this.action = c.action;
    }
    public Card(int id, GameMode gamemode){

    }
    public String getInstructions(){
        return instructions;
    }
    /*public Image getImage(){
        return image;
    }*///which image
    public int getId(){
        return id;
    }
    MonopolyAction getAction(){
        return action;
    }
}
