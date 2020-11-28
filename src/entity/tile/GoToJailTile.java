package entity.tile;

public class GoToJailTile extends Tile{

    GoToJailTile( int tileId) {
        super( tileId);
    }

    GoToJailTile(FreeParkingTile savedTile) {
        super( savedTile.getTileId());
    }

    public String toString() {
        return "ID: " + super.getTileId() + "- Tile Type: " + this.getClass().getSimpleName();
    }

}
