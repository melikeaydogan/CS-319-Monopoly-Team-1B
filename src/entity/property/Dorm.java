package entity.property;

import java.util.ArrayList;

public class Dorm extends Property {

    public Dorm() {
        super();
    }

    public Dorm(String name, int id, int price, ArrayList<Integer> rents, int mortgagePrice) {
        super(name, id, price, rents, mortgagePrice);
    }

}
