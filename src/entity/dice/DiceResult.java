package entity.dice;

public class DiceResult {

    private final int firstDieResult;
    private final int secondDieResult;
    private final SpeedDieResult speedDieResult;

    public DiceResult() { // without this, program disconnects!!!!
        firstDieResult = -1;
        secondDieResult = -1;
        speedDieResult = SpeedDieResult.NONE;
    }

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

    /**
     * @return -1 if the speed die is a MrMonopoly, -2 if a Bus, else sum of all dice
     */
    public int getValue() {
        if (speedDieResult.isMrMonopoly() )
            return -1;
        else if (speedDieResult.isBus() )
            return -2;
        else
            return firstDieResult + secondDieResult + speedDieResult.getSpeedDieVal();
    }

    public boolean isDouble() {
        // Rolling a triple is excluded from rolling a double
        if (isTriple())
            return false;
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
