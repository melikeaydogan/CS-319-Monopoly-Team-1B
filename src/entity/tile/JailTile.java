package entity.tile;

public class JailTile {
    private int[] playersInsideId;

    public JailTile() {
        playersInsideId = new int[6]; // max 6 players
    }

    public JailTile(JailTile jailTile) {
        playersInsideId = jailTile.getPlayersInsideId().clone();
    }

    public int[] getPlayersInsideId() {
        return playersInsideId;
    }

    public void setPlayersInsideId(int[] playersInsideId) {
        this.playersInsideId = playersInsideId;
    }

}

//ToDo maybe create a separate Jail class??
