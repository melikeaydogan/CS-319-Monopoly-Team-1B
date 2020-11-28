package entity.tile;

import entity.card.Card;

import java.awt.*;

public class CardTile extends Tile {
    public enum CardType {
        CHANCE_CARD,
        COMMUNITY_CHEST_CARD
    }
    CardType cardType;

    CardTile( int tileId, CardType cardType) {
        super( tileId);
        this.cardType = cardType;
    }

    CardTile(CardTile savedTile) {
        super( savedTile.getTileId());
        this.cardType = savedTile.cardType;
    }

    public CardType CardType() {
        return cardType;
    }

}
