package entity.property;

import java.util.ArrayList;

public class Building extends Property{
    public enum BuildingColor {
        BROWN,
        BLUE,
        PINK,
        ORANGE,
        RED,
        YELLOW,
        GREEN,
        NAVY
    }

    int housePrice;
    int hotelPrice;
    String color;
    int houseCount;
    int hotelCount;

    public Building(String name, int id, int price, ArrayList<Integer> rents, int mortgagePrice,
                    int housePrice, int hotelPrice, String color) {
        super(name, id, price, rents, mortgagePrice);
        this.housePrice = housePrice;
        this.hotelPrice = hotelPrice;
        this.color = color;
        houseCount = 0;
        hotelCount = 0;
    }

    public void addHouse() {
        houseCount++;
    }

    public void addHotel() {
        hotelCount++;
    }

    public int getHouseCount() {
        return houseCount;
    }

    public void setHouseCount(int houseCount) {
        this.houseCount = houseCount;
    }

    public int getHotelCount() {
        return hotelCount;
    }

    public void setHotelCount(int hotelCount) {
        this.hotelCount = hotelCount;
    }

    public int getHousePrice() {
        return housePrice;
    }

    public void setHousePrice(int housePrice) {
        this.housePrice = housePrice;
    }

    public int getHotelPrice() {
        return hotelPrice;
    }

    public void setHotelPrice(int hotelPrice) {
        this.hotelPrice = hotelPrice;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "ID: " + id + " Name: " + name + " Price: " + price + " Color: " + color +
                " House price: " + housePrice + " Hotel price: " + hotelPrice;
    }
}
