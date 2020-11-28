package entity.tile;

public class FreeParkingTail extends Tile {

    FreeParkingTail( int tileId, int amount) {
        super( tileId);
    }

    FreeParkingTail(FreeParkingTail savedTile) {
        super( savedTile.getTileId());
    }


}
