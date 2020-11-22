package entity.card;

import control.MonopolyAction;
import control.GameMode;

public class Card {
    int id;
    String instructions;
    MonopolyAction action;
    public Card(){
        id = -1;
        instructions = "";
        action = null;
    }
    public Card(Card c){
        this.id = c.id;
        this.instructions = c.instructions;
        this.action = c.action;
    }
    public Card(int id, String instructions, MonopolyAction action){
        this.id = id;
        this.instructions = instructions;
        this.action = action;
    }
    public String getInstructions(){
        return instructions;
    }

    public int getId(){
        return id;
    }

    MonopolyAction getAction(){
        return action;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setAction(MonopolyAction action) {
        this.action = action;
    }
}
