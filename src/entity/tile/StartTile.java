package entity.tile;

import entity.Player;
import java.awt.*;

public class StartTile extends Tile{
    private final int salary;

    public StartTile( int tileId, int salary) {
        super(tileId);
        this.salary = salary;
    }

    public StartTile(StartTile startTile) {
        super(0);
        salary = startTile.getSalary();
    }

    public int getSalary() {
        return salary;
    }

    public void giveSalary(Player player) {
        player.addMoney(salary);
    }

}
