package entity.tile;

import java.awt.*;

public class PropertyTile extends Tile {

    private final int propertyId;

    public PropertyTile(Image image, int propertyId) {
        super(image);
        this.propertyId = propertyId;
    }

    public PropertyTile(PropertyTile propertyTile) {
        super(propertyTile.getImage());
        this.propertyId = propertyTile.getPropertyId();
    }

    public int getPropertyId() {
        return propertyId;
    }

}
