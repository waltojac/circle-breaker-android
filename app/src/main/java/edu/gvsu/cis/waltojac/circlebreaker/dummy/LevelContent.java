package edu.gvsu.cis.waltojac.circlebreaker.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LevelContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<LevelItem> ITEMS = new ArrayList<LevelItem>();

//    private static final int COUNT = 25;
//
//    static {
//        // Add some sample items.
//        for (int i = 1; i <= COUNT; i++) {
//            addItem(createDummyItem(i));
//        }
//    }

    public static void addItem(LevelItem item) {
        ITEMS.add(item);
    }

    public static void clear() {
        ITEMS.clear();
    }

//    private static String makeDetails(int position) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("Details about Item: ").append(position);
//        for (int i = 0; i < position; i++) {
//            builder.append("\nMore details information here.");
//        }
//        return builder.toString();
//    }


    public static class LevelItem {
        public final String id;


        public LevelItem(String id) {
            this.id = id;

        }

        @Override
        public String toString() {
            return id;
        }
    }
}
