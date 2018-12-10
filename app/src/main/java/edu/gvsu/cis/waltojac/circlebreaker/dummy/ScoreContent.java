package edu.gvsu.cis.waltojac.circlebreaker.dummy;

import java.util.ArrayList;
import java.util.List;

public class ScoreContent {
    public static final List<ScoreItem> ITEMS = new ArrayList<ScoreItem>();

    public static void addItem(ScoreItem item) {
        ITEMS.add(item);
    }

    public static void clear() {
        ITEMS.clear();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class ScoreItem {
        public final String id;
        public final String username;
        public final String level;

        public ScoreItem(String id, String uname, String level) {
            this.id = id;
            this.username = uname;
            this.level = level;
        }

        public ScoreItem() {
            this.id = "1";
            this.username = "foo";
            this.level = "10";
        }

        @Override
        public String toString() {
            return username;
        }
    }
}
