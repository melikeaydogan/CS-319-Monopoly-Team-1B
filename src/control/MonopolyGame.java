package control;

import com.sun.org.apache.bcel.internal.generic.GOTO;
import control.action.*;
import entity.Board;
import entity.Player;
import entity.card.Card;
import entity.dice.Dice;
import entity.dice.DiceResult;
import entity.property.Property;
import entity.tile.*;
// import javafx.beans.property.Property;

// how does ui and this communicate?
// UI and UIController class
// UIControl class will have both UI and MonopolyGame
// It will call appropriate functions according to the user input
// how do we bankrupt the player?

import java.util.ArrayList;

public class MonopolyGame {

    Board board;
    int turn;
    static ActionLog actionLog;
    ArrayList<Player> players = new ArrayList<>(6);
    PlayerController playerController;
    // GameMode gameMode;
    boolean gameStarted = false;
    boolean gamePaused = false;
    int doubleCount = 0;
    int moveCount = 0; // sum of dices
    Dice dice;
    DiceResult diceResult;

    public MonopolyGame() {
        board = new Board();
        turn = 0;
        actionLog = new ActionLog();
        playerController = new PlayerController(players);
        dice = new Dice(System.currentTimeMillis());
    }

    public void addPlayer(Player player) {
        playerController.addPlayer(player);
    }

    public void stopGame() {
        gameStarted = false;
    }

    public void startGame() {
        // select the first
        gameStarted = true;
    }

    public DiceResult rollDice() {
        DiceResult diceResult = dice.roll(false); // false for not-speedDie

        if ( diceResult.isDouble() ) {
            doubleCount++;
        }

        moveCount = diceResult.getFirstDieResult() + diceResult.getSecondDieResult();
        return diceResult; // return because ui will show this result
    }

    public void processTurn() {
        Player player = playerController.getActivePlayer();

        if (doubleCount == 3) { // if this is the third double, put player into the jail
            new GoToJailAction(player).act();
            doubleCount = 0;
        }
        else {
            if (!player.isInJail()) {
                new MoveAction(player, moveCount); // try catch? PlayerIsInJailException

                Tile tile = board.getTiles().get(player.getPosition());

                if (tile instanceof PropertyTile) {
                    processPropertyTile((PropertyTile) board.getTiles().get(player.getPosition()));
                }
                else if (tile instanceof JailTile) {
                    // nothing, skip the turn
                }
                else if (tile instanceof StartTile) {
                    new PassAction(player).act();
                }
                else if (tile instanceof TaxTile) {
                    new RemoveMoneyAction(player, 1000).act(); // Luxury tile takes 1M$ from player
                }
                else if (tile instanceof ActivityTile) {
                    ActivityTile activityTile = (ActivityTile) tile;
                    Activity activity = activityTile.getActivity();

                    if (activity == Activity.CHANCE) {
                        processChanceCardTile();
                    }
                    else if (activity == Activity.COMMUNITY_CHEST) {
                        processCommunityChestCardTile();
                    }
                    else if (activity == Activity.FREE_PARK_VISIT) {
                        // do nothing, skip the turn
                    }
                    else if (activity == Activity.GO_TO_JAIL) {
                        new GoToJailAction(player).act();
                    }
                }
            }
        }





        // if tile is PropertyTile --> processPropertyTile()
        // if tile is JailTile --> processJailTile()  visitor
        // if tile is ActivityTile --> if ActivityTile.getActivity() == Activity.GO_TO_JAIL --> processGoToJailTile()
        //                         --> if ActivityTile.getActivity() == Activity.CHANCE --> processChanceCardTile()
        //                         --> if ActivityTile.getActivity() == Activity.COMMUNITY_CHEST --> processCommunityChestCardTile()
        //                         --> if ActivityTile.getActivity() == Activity.FREE_PARK_VISIT --> processFreeParkTile()
        // if tile is StartTile --> processStartTile()
        // if tile is TaxTile --> processTaxTile() maybe rename TaxTile to LuxuryTile

        // if user clicks at end turn --> nextTurn(); this is business of ui controller, not this class
    }

    public void nextTurn() {
        if ( !diceResult.isDouble() || doubleCount == 3 ) {
            playerController.switchToNextPlayer();
            doubleCount = 0;
        }
        turn++;
    }

    public void processPropertyTile(PropertyTile tile) {
        Property property = board.getProperties().get(tile.getPropertyId());

        //if (!properties.contains(property)) {
            // ui.showBuyPropertyDialog(property); this is business of control object
        //}
    }

    public void buyProperty(PropertyTile tile) {
        new BuyPropertyAction(board.getProperties().get(tile.getPropertyId()), getActivePlayer()).act();
    }

    public Card processChanceCardTile() {
        Card card = board.drawChanceCard();
        //ui.showCard(card); this is business of control object
        card.processCard(this);

        return card; // return to ui? Card card = monopolyGame.processChanceCardTile()
        //
    }

    public Card processCommunityChestCardTile() {
        Card card = board.drawCommunityChestCard();
        // ui.showCard(card); this is business of control object
        card.processCard(this);

        return card; // return to ui?
    }

    public boolean isGameOver() {
        ArrayList<Player> players = playerController.getPlayers();

        int bankruptPlayerCount = 0;

        for (Player p : players) {
            if (p.isBankrupt()) {
                bankruptPlayerCount++;
            }
        }

        return bankruptPlayerCount == 3;
    }

    public Player getActivePlayer() {return playerController.getActivePlayer();}

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public static ActionLog getActionLog() {
        return actionLog;
    }

    public void setActionLog(ActionLog actionLog) {
        MonopolyGame.actionLog = actionLog;
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }

    public Dice getDice() {
        return dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }
}
