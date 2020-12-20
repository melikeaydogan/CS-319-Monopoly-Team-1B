package control;

import entity.Player;

import java.util.ArrayList;
import java.util.Iterator;

// First initialize players in the model, then pass the ArrayList to the controller
public class PlayerController {

    private static ArrayList<Player> players; // Initial capacity of the ArrayList --> 6
    private Iterator<Player> playerIterator;
    private Player activePlayer = null;
    private int activePlayerIndex = 0;
    private int maxId;

    public PlayerController(ArrayList<Player> players) {
        this.players = new ArrayList<>(players);

        maxId = -1;
        for (Player p : players) {
            if (p.getPlayerId() > maxId)
                maxId = p.getPlayerId();
        }
        maxId++; // don't skip the player with the max id
    }

    public PlayerController(PlayerController playerController) {
        players = new ArrayList<>(playerController.getPlayers());
        activePlayer = playerController.getActivePlayer();
        activePlayerIndex = playerController.getActivePlayerIndex();
    }

    public PlayerController() {

    }

    public Player getActivePlayer() {
        if ( players.size() > 0)
            return getById(activePlayerIndex);
        return null;
    }

    public static Player getById(int id) {
        for (Player p : players)
            if (p.getPlayerId() == id)
                return p;
        return null;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
        activePlayerIndex = activePlayer.getPlayerId();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getActivePlayerIndex() {
        return activePlayerIndex;
    }

    public void setActivePlayerIndex(int activePlayerIndex) {
        this.activePlayerIndex = activePlayerIndex;
        activePlayer = getById(activePlayerIndex);
    }

    public Player switchToNextPlayer() {
        if (players.size() > 0) { // if player is bankrupt, skip him
            do {
                activePlayerIndex = (activePlayerIndex + 1) % maxId;
            } while (getById(activePlayerIndex) == null || getById(activePlayerIndex).isBankrupt());
            return getById(activePlayerIndex);
        }
        return null;
    }

    public boolean addPlayer(Player player) {
        if (players.size() < 6 ) {
            players.add(player);
            return true;
        }
        else
            return false;
    }

    @Override
    public String toString() {
        return "Players in the controller: " + players;
    }

}
