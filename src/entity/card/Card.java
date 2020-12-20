package entity.card;


public class Card {
    int id;
    String instructions;


    public Card(){
        id = -1;
        instructions = "";
    }
    public Card(Card c){
        this.id = c.id;
        this.instructions = c.instructions;
    }
    public Card(int id, String instructions){
        this.id = id;
        this.instructions = instructions;
    }

    public String getInstructions(){
        return instructions;
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String toString() {
        return "ID: " + id + " - Instructions: " + instructions;
    }

}
