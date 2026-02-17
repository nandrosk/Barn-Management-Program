package model;

import java.util.ArrayList;

// Represents a turnout board schedule for a week
public class Schedule {

    private ArrayList<Day> days;

    // EFFECTS: constructs a schedule with a day object for each day of the week,
    // with the given number of pastures (to be passed to day objects)
    public Schedule(ArrayList<String> pastures) {
        days = new ArrayList<Day>();
        for (int i = 0; i < 7; i++) {
            String day;
            // case
            if (i == 0) {
                day = "M";
            } else if (i == 1) {
                day = "T";
            } else if (i == 2) {
                day = "W";
            } else if (i == 3) {
                day = "Th";
            } else if (i == 4) {
                day = "F";
            } else if (i == 5) {
                day = "Sat";
            } else {
                day = "Sun";
            }
            days.add(new Day(day, pastures));
        }
    }

    // EFFECTS: constructs a schedule with empty days
    // (for use by the reader)
    public Schedule() {
        this.days = null;
    }

    public void setDays(ArrayList<Day> days) {
        this.days = days;
    }

    // Getters

    // REQUIRES: index >= 0
    // EFFECTS: returns the day in this.days at the given index
    public Day getDay(int index) {
        return days.get(index);
    }

    // mostly for testing purposes
    public ArrayList<Day> getDays() {
        return days;
    }

}
