package entity.tile;

import java.awt.*;

public class PropertyTile extends Tile {

    private final int propertyId;

    public PropertyTile(int tileId, int propertyId) {
        super(tileId);
        this.propertyId = propertyId;
    }

    public PropertyTile(PropertyTile propertyTile) {
        super(propertyTile.getTileId());
        this.propertyId = propertyTile.getPropertyId();
    }

    public int getPropertyId() {
        return propertyId;
    }

    public String toString() {
        return "ID: " + super.getTileId() + "- Tile Type: " + this.getClass().getSimpleName() + " - Property ID: " + propertyId;
    }

}
