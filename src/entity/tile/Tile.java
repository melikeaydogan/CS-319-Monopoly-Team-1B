package entity.tile;

import java.awt.*;

public class Tile {

    private final Image image;
    private final int tileId;

    public Tile() {
        image = null;
        tileId = 0;
    }

    public Tile(Image image, int tileId) {
        this.image = image;
        this.tileId = tileId;
    }

    public Image getImage() {
        return image;
    }

    public int getTileId() {
        return tileId;
    }

    // ToDo add tileId to the parent Tile class,
    //  so that we have tileId necessary for freeMove(int tile) functionality
}
