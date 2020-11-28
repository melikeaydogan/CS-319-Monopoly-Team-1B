package entity.tile;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import entity.property.Building;
//import entity.property.Dorm;
import entity.property.Facility;
import entity.property.Property;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import entity.property.Building;
import entity.property.Facility;
import entity.property.Property;

import java.awt.*;
import java.lang.reflect.Type;

public class Tile {

    private final int tileId;

    public Tile() {
        tileId = 0;
    }

    public Tile( int tileId) {
        this.tileId = tileId;
    }

    public int getTileId() {
        return tileId;
    }

    public static class CustomTileDeserializer implements JsonDeserializer<Tile> {

        public Tile deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            if (json == null)
                return null;
            else {
                String type = json.getAsJsonObject().get("type").getAsString();
                switch(type){
                    case "START":
                        return context.deserialize(json, StartTile.class);
                    case "PROPERTY":
                        return context.deserialize(json, PropertyTile.class);
                    case "CARD":
                        return context.deserialize(json, ActivityTile.class);
                    case "TAX":
                        return context.deserialize(json, TaxTile.class);
                    case "JAIL":
                        return context.deserialize(json, JailTile.class);
                    default:
                        return null;
                }
            }

        }

    }

    public static class CustomDeserializer implements JsonDeserializer<Tile> {

        public Tile deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            if (json == null)
                return null;
            else {
                String type = json.getAsJsonObject().get("type").getAsString();
                switch(type){
                    case "START":
                        return context.deserialize(json, StartTile.class);
                    case "PROPERTY":
                        return context.deserialize(json, PropertyTile.class);
                    case "CARD":
                        return context.deserialize(json, CardTile.class);
                    case "TAX":
                        return context.deserialize(json, TaxTile.class);
                    case "JAIL":
                        return context.deserialize(json, JailTile.class);
                    case "FREE_PARKING":
                        return context.deserialize(json, FreeParkingTile.class);
                    case "GO_TO_JAIL":
                        return context.deserialize(json, GoToJailTile.class);
                    default:
                        return null;
                }
            }

        }

    }
}
