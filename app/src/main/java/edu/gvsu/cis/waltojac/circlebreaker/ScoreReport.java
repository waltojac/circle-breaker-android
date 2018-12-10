package edu.gvsu.cis.waltojac.circlebreaker;

public class ScoreReport {
    public final String username;
    public final String level;

    public ScoreReport(String uname, String level) {
        this.username = uname;
        this.level = level;
    }

    public ScoreReport() {
        this.username = "foo";
        this.level = "1";
    }

    @Override
    public String toString() {
        return username;
    }

}
