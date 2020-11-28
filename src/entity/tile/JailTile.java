package entity.tile;

import java.util.*;

public class JailTile extends Tile {
    private final ArrayList<Integer> playersInsideId;

    /** Init an empty jail tile with 6 capacity
     *  Jail always has the tileId = -1
     *  Actually, jail tile has no image, all the image belongs to
     *  the visitor tile if we implement the way it is written below.
     *  we will only need the tokens to be put inside a physical boundary
     *  depending on if they are in jail or just visiting
     */
    public JailTile(int tileId) {
        super(tileId);
        playersInsideId = new ArrayList<>(6);
    }

    // Get the saved jailTile
    public JailTile(JailTile savedTile) {
        super( savedTile.getTileId());
        playersInsideId = savedTile.getPlayersInsideId();
    }

    // a helper function that returns the deep copy of playersInsideId
    public ArrayList<Integer> getPlayersInsideId() {
        return new ArrayList<>(playersInsideId);
    }

    // Add the player to the jail if they are not in
    public boolean add(int playerId) {
        return playersInsideId.add(playerId);
    }

    // Remove the player from the jail if they are in
    public boolean remove(int playerId) {
        return playersInsideId.remove(new Integer(playerId));
    }

    // return true if the player is in the jail
    public boolean contains(int playerId) {
        return playersInsideId.contains(playerId);
    }

    public String toString() {
        return "ID: " + super.getTileId() + "- Tile Type: " + this.getClass().getSimpleName();
    }

}



