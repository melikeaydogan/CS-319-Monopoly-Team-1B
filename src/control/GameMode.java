package control;

public class GameMode {
    private boolean alliance;
    private boolean speedDie;

    public GameMode(boolean alliance, boolean speedDie) {
        this.alliance = alliance;
        this.speedDie = speedDie;
    }

    public boolean isSpeedDie() {
        return speedDie;
    }

    public boolean isAlliance() {
        return alliance;
    }

    public void setSpeedDie(boolean speedDie) {
        this.speedDie = speedDie;
    }

    public void setAlliance(boolean alliance) {
        this.alliance = alliance;
    }
}
