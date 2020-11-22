package entity;

import java.util.Random;

public class Dice {

    private final long gameSeed;
    private final Random random;

    public Dice(long gameSeed) {
        this.gameSeed = gameSeed; // gameSeed will be System.currentTimeMillis();
        random = new Random(gameSeed);
    }

    public Dice(Dice savedDice) {
        gameSeed = savedDice.getGameSeed();
        random = new Random(gameSeed);
    }

    public long getGameSeed() {
        return gameSeed;
    }

    public DiceResult roll(boolean isSpeedDie) {
        if (isSpeedDie) {
            // ToDo
            return null;
        }
        else {
            int firstDieResult = random.nextInt(6) + 1;
            int secondDieResult = random.nextInt(6) + 1;
            return new DiceResult(firstDieResult, secondDieResult);
        }
    }

/*    public static void main(String[] args) {
        Dice dice = new Dice(System.currentTimeMillis());
        for ( int i = 0; i < 12; i++ ) {
            DiceResult diceResult = dice.roll(false);
            System.out.println("Dice 1: " + diceResult.getFirstDieResult() + " - Dice 2: " + diceResult.getSecondDieResult());
        }
    }*/

}
