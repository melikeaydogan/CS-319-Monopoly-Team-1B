package card;

import control.GameMode;

public class ChanceCard extends Card {
    public ChanceCard(int id, GameMode gameMode){
        super(id, gameMode);
    }
    public ChanceCard(ChanceCard savedCard){
        super(savedCard);
    }
}
