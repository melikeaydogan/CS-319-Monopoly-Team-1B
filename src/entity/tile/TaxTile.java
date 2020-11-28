package entity.tile;

import java.awt.*;

public class TaxTile extends Tile {

    /**
     * amount shows the amount of tax tile will force the player to pay
     */
    private final int amount;

    TaxTile(int tileId, int amount) {
        super(tileId);
        this.amount = amount;
    }

    TaxTile(TaxTile savedTile) {
        super( savedTile.getTileId());
        this.amount = savedTile.amount;
    }

    public int getAmount() {
        return amount;
    }

    public String toString() {
        return "ID: " + super.getTileId() + "- Tile Type: " + this.getClass().getSimpleName() + " - Tax Amount: " + amount;
    }
}
