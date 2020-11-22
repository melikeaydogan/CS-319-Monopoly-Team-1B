package entity.dice;

public class DiceResult {

    private final int firstDieResult;
    private final int secondDieResult;
    private final SpeedDieResult speedDieResult;

    // Use this constructor for the speed die mode
    public DiceResult(int firstDieResult, int secondDieResult, SpeedDieResult speedDieResult) {
        this.firstDieResult = firstDieResult;
        this.secondDieResult = secondDieResult;
        this.speedDieResult = speedDieResult;
    }

    // Use this constructor for the normal game mode
    public DiceResult(int firstDieResult, int secondDieResult) {
        this.firstDieResult = firstDieResult;
        this.secondDieResult = secondDieResult;
        speedDieResult = SpeedDieResult.NONE; // SpeedDie mode is disabled
    }

    public int getFirstDieResult() {
        return firstDieResult;
    }

    public int getSecondDieResult() {
        return secondDieResult;
    }

    public SpeedDieResult getSpeedDieResult() {
        return speedDieResult;
    }

    public boolean isDouble() {
        return firstDieResult == secondDieResult;
    }

    public boolean isTriple() {
        int speedDieVal = speedDieResult.getSpeedDieVal();
        return isDouble() && (secondDieResult == speedDieVal);
    }

    /*public static void main(String[] args) {
        for (SpeedDieResult sd : SpeedDieResult.values()) {
            System.out.printf("The value of %s is %d%n", sd, sd.getSpeedDieVal());
        }
    }*/
}
