package network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class MonopolyNetwork {

    public static final int PORT = 54555;
    public static String ipAddress = "127.0.0.1";

    public MonopolyNetwork() {
    }

    public static void register (EndPoint endPoint) {
        Kryo kyro = endPoint.getKryo();
        // register all classes that will be serialized here
        kyro.register(control.MonopolyGame.class); // not sure
        kyro.register(control.ActionLog.class);
        kyro.register(control.PlayerController.class);

        kyro.register(entity.card.Card.class);
        kyro.register(entity.card.Deck.class);
        kyro.register(entity.dice.Dice.class);
        kyro.register(entity.dice.DiceResult.class);
        kyro.register(entity.dice.SpeedDieResult.class);
        kyro.register(entity.property.Building.class);
        kyro.register(entity.property.Dorm.class);
        kyro.register(entity.property.Facility.class);
        kyro.register(entity.property.Property.class);

        kyro.register(entity.tile.CardTile.class);
        kyro.register(entity.tile.FreeParkingTile.class);
        kyro.register(entity.tile.GoToJailTile.class);
        kyro.register(entity.tile.JailTile.class);
        kyro.register(entity.tile.PropertyTile.class);
        kyro.register(entity.tile.StartTile.class);
        kyro.register(entity.tile.TaxTile.class);
        kyro.register(entity.tile.Tile.class);
        kyro.register(entity.tile.CardTile.CardType.class);

        kyro.register(entity.Board.class);
        kyro.register(entity.Player.class);

        kyro.register(control.action.Action.class);
        kyro.register(control.action.AddHouseAction.class);
        kyro.register(control.action.AddHotelAction.class);
        kyro.register(control.action.AddMoneyAction.class);
        kyro.register(control.action.BuyPropertyAction.class);
        kyro.register(control.action.DrawChanceCardAction.class);
        kyro.register(control.action.DrawCommunityChestCardAction.class);
        kyro.register(control.action.FreeMoveAction.class);
        kyro.register(control.action.GetOutOfJailAction.class);
        kyro.register(control.action.GoToJailAction.class);
        kyro.register(control.action.MoveAction.class);
        kyro.register(control.action.PassAction.class);
        kyro.register(control.action.RemoveMoneyAction.class);
        kyro.register(control.action.RollDiceAction.class);
        kyro.register(control.action.SellPropertyAction.class);
        kyro.register(control.action.TransferAction.class);

        kyro.register(java.util.HashMap.class);
        kyro.register(java.util.ArrayList.class);
        kyro.register(entity.Player.Token.class);
        kyro.register(java.util.LinkedList.class);
        kyro.register(boolean[].class);
        kyro.register(entity.Player[].class);
    }

}
