package entity.card;

//import control.GameMode;
import control.MonopolyGame;
import control.action.Action;
import control.action.FreeMoveAction;
import control.action.GoToJailAction;
import control.action.TakeAction;
import entity.Player;
import com.google.gson.Gson;

public class ChanceCard extends Card {
    public ChanceCard(int id, String instructions){
        super(id, instructions);
    }
    public ChanceCard(ChanceCard savedCard){
        super(savedCard);
    }

    @Override
    public void processCard(MonopolyGame monopolyGame) { // hardcode chance cards to this section, remove action from constructor
        Player activePlayer = monopolyGame.getPlayerController().getActivePlayer();
        switch (id) {
            case 1:
                new TakeAction(activePlayer, 200);
                break;
            case 2:
                new GoToJailAction(activePlayer);
                break;
            case 3:
                new FreeMoveAction(activePlayer, 0);
                break;
        }
    }


    // card 1 --> You won 200$ from lottery.
    // card 2 --> You got kicked to jail because of theft.
    // card 3 --> Go to the beginning point.
}
