package entity.property;

//import control.MonopolyAction;

import control.MonopolyGame;
import entity.Player;

public abstract class Property {

    abstract int getRent(Player player);
    //abstract int getMortgage();
    abstract boolean isMortgaged();
    abstract int makeMortgaged();
    abstract int liftMortgage();
    abstract boolean isOwned();
    abstract boolean changeOwner(int playerID);
}
