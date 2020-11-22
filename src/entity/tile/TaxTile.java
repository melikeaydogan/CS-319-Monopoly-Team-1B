package entity.tile;

import java.awt.*;

public class TaxTile extends Tile {

    /**
     * amount shows the amount of tax tile will force the player to pay
     */
    private final int amount;

    TaxTile(Image image, int tileId, int amount) {
        super(image, tileId);
        this.amount = amount;
    }

    TaxTile(TaxTile savedTile) {
        super(savedTile.getImage(), savedTile.getTileId());
        this.amount = savedTile.amount;
    }

    public int getAmount() {
        return amount;
    }

}
