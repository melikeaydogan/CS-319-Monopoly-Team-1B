package entity.tile;

import entity.Player;
import java.awt.*;

public class StartTile extends Tile{
    private final int salary;

    public StartTile(Image image, int salary) {
        super(image);
        this.salary = salary;
    }

    public StartTile(StartTile startTile) {
        super(startTile.getImage());
        salary = startTile.getSalary();
    }

    public int getSalary() {
        return salary;
    }

    public void giveSalary(Player player) {
        player.giveMoney(salary);
    }

    // ToDo remove duplicate methods from the class diagram --> getImage()
}
