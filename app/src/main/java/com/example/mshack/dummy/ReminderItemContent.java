package com.example.mshack.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ReminderItemContent
{

    /**
     * An array of sample (dummy) items.
     */
    public static final List<ReminderItem> ITEMS = new ArrayList<ReminderItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, ReminderItem> ITEM_MAP = new HashMap<String, ReminderItem>();

    private static final int COUNT = 25;

    /*static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++)
        {
            addItem(createDummyItem(i));
        }
    }*/

    private static void addItem(ReminderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /*private static ReminderItem createDummyItem(int position)
    {
        return new ReminderItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }*/

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class ReminderItem {
        public final String id;
        public final String latitude;
        public final String longitude;
        public final String place;
        public final String radius;
        public final String reminderTitle;
        public final String reminderText;
        public final String timeStamp;

        public ReminderItem(String id, String latitude, String longitude, String place, String radius, String reminderTitle,
                            String reminderText, String timeStamp) {
            this.id = id;
            this.latitude = latitude;
            this.longitude = longitude;
            this.place = place;
            this.radius = radius;
            this.reminderTitle = reminderTitle;
            this.reminderText = reminderText;
            this.timeStamp = timeStamp;
        }

        /*@Override
        public String toString() {
            return content;
        }*/
    }
}
