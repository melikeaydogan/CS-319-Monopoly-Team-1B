package entity.property;

public class Dorm extends Property {
    /*
    int id;
    boolean isOwned;
    String name;
    int ownerID;
    int buildingCost;
    int mortgageAmount;
    int purchasePrice;
    int colorID;

          */
    public Dorm(){
    }
    public int getRent( int diceSum );
    //abstract int getMortgage();
    public boolean isMortgaged();
    public int makeMortgaged();
    public int payMortgage();
    public boolean isOwned();
    public boolean changeOwner(int playerID);
}
