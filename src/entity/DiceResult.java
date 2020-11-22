package entity;

public class DiceResult {
    enum SpeedDieResult{
        NONE,
        ONE,
        TWO,
        THREE,
        BUS,
        MR_MONOPOLY
    }

    private final int firstDieResult;
    private final int secondDieResult;
    private final SpeedDieResult speedDieResult;

    public DiceResult(int firstDieResult, int secondDieResult, SpeedDieResult speedDieResult) {
        this.firstDieResult = firstDieResult;
        this.secondDieResult = secondDieResult;
        this.speedDieResult = speedDieResult;
    }

    public DiceResult(int firstDieResult, int secondDieResult) {
        this.firstDieResult = firstDieResult;
        this.secondDieResult = secondDieResult;
        speedDieResult = null; // Speed-Die is not enabled
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
        // ToDo
        return false;
    }
}
