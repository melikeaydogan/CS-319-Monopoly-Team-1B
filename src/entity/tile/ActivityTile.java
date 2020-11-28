package entity.tile;

import java.awt.*;

public class ActivityTile extends Tile {
    /**
     * activity denotes the type of activity
     * the tile will force the player to take
     */
    private final Activity activity;

    ActivityTile( int tileId, Activity activity) {
        super(tileId);
        this.activity = activity;
    }

    ActivityTile(ActivityTile savedTile) {
        super( savedTile.getTileId());
        this.activity = savedTile.activity;
    }

    public Activity getActivity() {
        return activity;
    }

}
