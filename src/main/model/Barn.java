package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writeable;

// Represents a barn with horses, pastures, and a turnout schedule
public class Barn implements Writeable {

    private String barnName;
    private ArrayList<Horse> horses;
    private ArrayList<String> pastures;
    private Schedule schedule;

    // EFFECTS: constructs a barn with the given name, given pasture list,
    // an empty list of horses, and a
    // default schedule with the corresponding num of pastures
    public Barn(String barnName, ArrayList<String> pastures) {
        this.barnName = barnName;
        this.pastures = pastures;
        horses = new ArrayList<Horse>();
        schedule = new Schedule(pastures);
    }

    // EFFECTS: constructs a barn with the given name, given pasture list,
    // given list of horses, and a given schedule
    public Barn(String barnName, ArrayList<String> pastures, 
                ArrayList<Horse> horses, Schedule schedule) {
        this.barnName = barnName;
        this.pastures = pastures;
        this.horses = horses;
        this.schedule = schedule;
    }

    // MODIFIES: this
    // EFFECTS: adds the given horse to the list of horses
    // and logs the event
    public void addHorse(Horse horse) {
        horses.add(horse);
        EventLog.getInstance().logEvent(new Event("Horse added: " + horse.getHorseName()));
    }

    // MODIFIES: this
    // EFFECTS: removes the given horse from the list of horses
    // returns true if a horse is found and removed, returns false otherwise
    // if a horse is removed, the event is logged
    public boolean removeHorse(String horseName) {
        Horse match = getHorse(horseName);
        if (match != null) {
            EventLog.getInstance().logEvent(new Event("Horse removed: " + horseName));
            horses.remove(match);
            return true;
        } else {
            return false;
        }
    }

    public void setBarnName(String name) {
        this.barnName = name;
    }

    // Getters

    public ArrayList<Horse> getHorses() {
        return horses;
    }

    // EFFECTS: returns the horse with the given name
    // if there isn't one, return null
    public Horse getHorse(String name) {
        for (Horse currentHorse : horses) {
            if (currentHorse.getHorseName().equals(name)) {
                return currentHorse;
            }
        }
        return null;
    }

    public String getBarnName() {
        return barnName;
    }

    public ArrayList<String> getPastures() {
        return pastures;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", barnName);

        JSONArray jsonHorses = new JSONArray();
        for (Horse current : horses) {
            jsonHorses.put(current.toJson());
        }
        json.put("horses", jsonHorses);

        JSONArray jsonPastures = new JSONArray();
        for (String current : pastures) {
            jsonPastures.put(current);
        }
        json.put("pastures", jsonPastures);

        JSONArray jsonArray = new JSONArray();
        for (Day day : schedule.getDays()) {
            jsonArray.put(day.toJson());
        }
        json.put("days", jsonArray);
        return json;
    }

}
