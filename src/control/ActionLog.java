package control;

import java.util.ArrayList;

public class ActionLog {

    private static ActionLog log_instance = null; // singleton

    private final int MAX_LOG = Integer.MAX_VALUE;
    ArrayList<String> log;
    private int numActions;

    private ActionLog() {
        log = new ArrayList<>();
        numActions = 0;
    }

    public static ActionLog getInstance() {
        if (log_instance == null) {
            log_instance = new ActionLog();
        }

        return log_instance;
    }

    public void addMessage(String s) {
        if ( numActions < MAX_LOG ) {
            log.add(s);
            numActions++;
        }
        System.out.println(s);
    }

    public ArrayList<String> getLog() {
        return log;
    }

    public void setLog(ArrayList<String> log) {
        this.log = log;
    }

    public int getNumActions() {
        return log.size();
    }

    public void setNumActions(int numActions) {
        this.numActions = numActions;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for( String s : log ) {
            result.append(s);
        }
        return result.toString();
    }

    public static void main(String[] args) {
        ActionLog actionLog = new ActionLog();
        actionLog.addMessage("Mehmet throws dice and gets 4,6");
        System.out.println(actionLog);
    }
}
