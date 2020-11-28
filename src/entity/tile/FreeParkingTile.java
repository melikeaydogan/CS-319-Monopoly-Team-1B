package entity.tile;

public class FreeParkingTile extends Tile {

    FreeParkingTile(int tileId) {
        super( tileId);
    }

    FreeParkingTile(FreeParkingTile savedTile) {
        super( savedTile.getTileId());
    }

}
