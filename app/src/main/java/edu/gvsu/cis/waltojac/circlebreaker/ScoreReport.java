package edu.gvsu.cis.waltojac.circlebreaker;

public class ScoreReport {
    public final String displayName;
    public final String email;
    public final String level;

    public ScoreReport(String uname, String email, String level) {
        this.displayName = uname;
        this.email = email;
        this.level = level;
    }

    public ScoreReport() {
        this.displayName = "foo";
        this.email = "foo";
        this.level = "1";
    }

    @Override
    public String toString() {
        return displayName;
    }

}
