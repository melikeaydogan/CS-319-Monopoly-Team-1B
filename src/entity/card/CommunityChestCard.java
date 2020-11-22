package card;

import control.GameMode;

public class CommunityChestCard extends Card {
    public CommunityChestCard(int id, GameMode gameMode){
        super(id, gameMode);
    }
    public CommunityChestCard(CommunityChestCard savedCard){
        super(savedCard);
    }

}
