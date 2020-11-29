package entity.dice;

public enum SpeedDieResult {
    NONE(0), // means speed die mode is disabled
    ONE(1),
    TWO(2),
    THREE(3),
    BUS(0),
    MR_MONOPOLY(0)
    ;
    // Add values for the speed die
    private final int SPEED_DIE_VAL;

    SpeedDieResult(int speedDieVal) {
        this.SPEED_DIE_VAL = speedDieVal;
    }

    public int getSpeedDieVal() {
        return SPEED_DIE_VAL;
    }

}
