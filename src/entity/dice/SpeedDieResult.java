package entity.dice;

public enum SpeedDieResult {
    NONE(0), // means speed die mode is disabled
    ONE(1),
    TWO(2),
    THREE(3),
    BUS(0),
    MR_MONOPOLY_1(0), // there are 2 Mr. Monopoly Surfaces
    MR_MONOPOLY_2(0)
    ;
    // Add values for the speed die
    private final int SPEED_DIE_VAL;

    SpeedDieResult(int speedDieVal) {
        this.SPEED_DIE_VAL = speedDieVal;
    }

    public int getSpeedDieVal() {
        return SPEED_DIE_VAL;
    }

    public boolean isMrMonopoly() {
        return (this == MR_MONOPOLY_1) || (this == MR_MONOPOLY_2);
    }

    public boolean isBus() {
        return this == BUS;
    }

    public String toString() {
        switch (this) {
            case ONE: return "1";
            case TWO: return "2";
            case THREE: return "3";
            case BUS: return "Bus";
            case MR_MONOPOLY_1: return "Mr. Monopoly";
            case MR_MONOPOLY_2: return "Mr. Monopoly";
            default: return "";
        }
    }

}
