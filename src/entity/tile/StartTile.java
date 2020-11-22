package entity.tile;

import entity.Player;
import java.awt.*;

public class StartTile extends Tile{
    private final int salary;

    // start tile always has the tileId 0.
    public StartTile(Image image, int salary) {
        super(image, 0);
        this.salary = salary;
    }

    public StartTile(StartTile startTile) {
        super(startTile.getImage(), 0);
        salary = startTile.getSalary();
    }

    public int getSalary() {
        return salary;
    }

    public void giveSalary(Player player) {
        player.giveMoney(salary);
    }

    // ToDo remove duplicate methods from the class diagram --> getImage()
    // ToDo add Image params to the  first constructor
}
