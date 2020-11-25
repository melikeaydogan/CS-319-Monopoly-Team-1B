package entity.property;

//import control.MonopolyAction;

public abstract class Property {
    abstract int getRent( int diceSum );
    //abstract int getMortgage();
    abstract boolean isMortgaged();
    abstract int makeMortgaged();
    abstract int liftMortgage();
    abstract boolean isOwned();
    abstract boolean changeOwner(int playerID);
}
