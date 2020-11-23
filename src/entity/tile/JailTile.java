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
    public JailTile() {
        super(null, -1);
        playersInsideId = new ArrayList<>();
    }

    // Get the saved jailTile
    public JailTile(JailTile savedTile) {
        super(savedTile.getImage(), savedTile.getTileId());
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

}

/*
 ToDo add all the changes to the class diagram
 maybe create a separate Jail class
 ---> the JailTile is also an ActionTile for a player that goes through it
      as it involves visiting, which is same as free park.
 the problem is it is not supposed to be an action tile.
 --
 if we were to implement a Jail class, it wold be eventually necessary for it to be a Tile
 --
 if we were to have 2 different tiles called visitor and jail,
 TLDR bad solution.
 we would need the move(int amount) method to count visitor and jail tiles as 1 tile.
 also gui would look like the token is going into the jail thou the token needs to move from
 visitor into the next property. This solution creates one more problem involving gui, i.e. skipping
 the unnecessary move of the token.
 === solution ===
 lets implement the JailTile class like we are doing now
 BUT add a property to the board class: JailTile jail
 AND don't include the jail tile inside the Tile[] tiles in the Board class
*/




