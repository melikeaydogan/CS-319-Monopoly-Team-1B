package entity.property;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import control.MonopolyGame;
import entity.Player;

import java.lang.reflect.Type;
import java.util.ArrayList;

public abstract class Property {

    String name;
    int id;
    int price;
    ArrayList<Integer> rents;
    int mortgagePrice;
    boolean isMortgaged;
    boolean isOwned;
    int ownerId;

    public Property(String name, int id, int price, ArrayList<Integer> rents, int mortgagePrice) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.rents = rents;
        this.mortgagePrice = mortgagePrice;
        isMortgaged = false;
        isOwned = false;
        ownerId = -1;
    }

    public Property() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ArrayList<Integer> getRents() {
        return rents;
    }

    public void setRents(ArrayList<Integer> rents) {
        this.rents = rents;
    }

    public int getMortgagePrice() {
        return mortgagePrice;
    }

    public void setMortgagePrice(int mortgagePrice) {
        this.mortgagePrice = mortgagePrice;
    }

    public boolean isMortgaged() {
        return isMortgaged;
    }

    public void setMortgaged(boolean mortgaged) {
        isMortgaged = mortgaged;
    }

    public boolean isOwned() {
        return isOwned;
    }

    public void setOwned(boolean owned) {
        isOwned = owned;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String toString() {
        return name;
    }

    public static class CustomDeserializer implements JsonDeserializer<Property> {

        public Property deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            if (json == null)
                return null;
            else {
                String type = json.getAsJsonObject().get("type").getAsString();
                switch(type){
                    case "DORM":
                        return context.deserialize(json, Dorm.class);
                    case "FACILITY":
                        return context.deserialize(json, Facility.class);
                    case "BUILDING":
                        return context.deserialize(json, Building.class);
                    default:
                        return null;
                }
            }

        }

    }
}
