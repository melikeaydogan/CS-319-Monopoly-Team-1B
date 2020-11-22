package entity.dice;

import java.util.Random;

public class Dice {

    private final long GAME_SEED;
    private final Random RANDOM;

    public Dice(long gameSeed) {
        GAME_SEED = gameSeed; // GAME_SEED will be System.currentTimeMillis();
        RANDOM = new Random(gameSeed);
    }

    public Dice(Dice savedDice) {
        GAME_SEED = savedDice.getGameSeed();
        RANDOM = new Random(GAME_SEED);
    }

    public long getGameSeed() {
        return GAME_SEED;
    }

    public DiceResult roll(boolean isSpeedDie) {
        int firstDieResult = RANDOM.nextInt(6) + 1;
        int secondDieResult = RANDOM.nextInt(6) + 1;
        if (isSpeedDie) {
            // Select a random index from the values array
            // use length - 1, then add 1 to the randomly chosen index
            // because we don't want NONE to be an outcome
            int index = RANDOM.nextInt(SpeedDieResult.values().length - 1) + 1;
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
