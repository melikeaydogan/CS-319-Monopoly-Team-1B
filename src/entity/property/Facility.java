package entity.property;

import java.util.ArrayList;

public class Facility extends Property{

    public Facility() {
        super();
    }

    public Facility(String name, int id, int price, ArrayList<Integer> rents, int mortgagePrice) {
        super(name, id, price, rents, mortgagePrice);
    }
}
