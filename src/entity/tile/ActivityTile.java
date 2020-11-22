package entity.tile;

import java.awt.*;

public class ActivityTile extends Tile {
    /**
     * activity denotes the type of activity
     * the tile will force the player to take
     */
    private final Activity activity;

    ActivityTile(Image image, int tileId, Activity activity) {
        super(image, tileId);
        this.activity = activity;
    }

    ActivityTile(ActivityTile savedTile) {
        super(savedTile.getImage(), savedTile.getTileId());
        this.activity = savedTile.activity;
    }

    public Activity getActivity() {
        return activity;
    }

}
