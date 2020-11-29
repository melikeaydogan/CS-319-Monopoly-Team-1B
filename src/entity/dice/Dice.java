package entity.dice;

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
        int firstDieResult = random.nextInt(6) + 1;
        int secondDieResult = random.nextInt(6) + 1;
        if (isSpeedDie) {
            // Select a random index from the values array
            // use length - 1, then add 1 to the randomly chosen index
            // because we don't want NONE to be an outcome
            int index = random.nextInt(SpeedDieResult.values().length - 1) + 1;
            SpeedDieResult speedDieResult = SpeedDieResult.values()[index];
            return new DiceResult(firstDieResult, secondDieResult, speedDieResult);
        }
        else
            return new DiceResult(firstDieResult, secondDieResult);
    }

    /*
    // Remove the starred comments to test the dice roll mechanism
    public static void main(String[] args) {
        Dice dice = new Dice(System.currentTimeMillis());
        for ( int i = 0; i < 12; i++ ) {
            DiceResult diceResult = dice.roll(false);
            System.out.println("Dice 1: "
                    + diceResult.getFirstDieResult()
                    + " - Dice 2: " + diceResult.getSecondDieResult());
        }

        for ( int i = 0; i < 36; i++ ) {
            DiceResult diceResult = dice.roll(true);
            System.out.println("Dice 1: "
                    + diceResult.getFirstDieResult()
                    + " - Dice 2: " + diceResult.getSecondDieResult()
                    + " - Speed Die: " + diceResult.getSpeedDieResult());
        }
    }*/

}
