package entity.tile;

import java.awt.*;

public class PropertyTile extends Tile {

    private final int propertyId;

    public PropertyTile(Image image, int tileId, int propertyId) {
        super(image, tileId);
        this.propertyId = propertyId;
    }

    public PropertyTile(PropertyTile propertyTile) {
        super(propertyTile.getImage(), propertyTile.getTileId());
        this.propertyId = propertyTile.getPropertyId();
    }

    public int getPropertyId() {
        return propertyId;
    }

}
