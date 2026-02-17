package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writeable;

// Represents a day of the week on a turnout board, 
// containing information about which pastures the horses are in today
// and the notes for the day
public class Day implements Writeable {

    private String dayOfWeek;
    private ArrayList<ArrayList<Horse>> horsesInPastures;
    private ArrayList<Note> notes;
    private ArrayList<String> pastures;

    // EFFECTS: constructs a day object with the given day of the week,
    // with an empty list of notes
    // horsesInPastures is constructed with size pastures, each index containing
    // an empty list of horses
    public Day(String dayOfWeek, ArrayList<String> pastures) {
        this.dayOfWeek = dayOfWeek;
        this.pastures = pastures;
        notes = new ArrayList<Note>();
        horsesInPastures = new ArrayList<ArrayList<Horse>>();
        for (int i = 0; i < pastures.size(); i++) {
            horsesInPastures.add(new ArrayList<Horse>());
        }
    }

    // EFFECTS: constructs a day object with the given arguments
    public Day(String dayOfWeek, ArrayList<String> pastures,
            ArrayList<ArrayList<Horse>> horsesInPastures,
            ArrayList<Note> notes) {
        this.dayOfWeek = dayOfWeek;
        this.pastures = pastures;
        this.horsesInPastures = horsesInPastures;
        this.notes = notes;
    }

    // EFFECTS: returns the index corresponding to the given pasture
    // if pasture doesn't exist, returns -1
    public int findPastureIndex(String pasture) {
        return pastures.indexOf(pasture);
    }

    // REQUIRES: pastures given actually exists
    // MODIFIES: this
    // EFFECTS: adds the given horse to the given pasture
    public void addHorseToPasture(Horse horse, String pasture) {
        int index = findPastureIndex(pasture);
        horsesInPastures.get(index).add(horse);
    }

    // REQUIRES: both pastures given actually exist
    // MODIFIES: this
    // EFFECTS: moves the given horse from one pasture to another
    public void moveHorse(Horse horse, String pastureFrom, String pastureTo) {
        int indexFrom = findPastureIndex(pastureFrom);
        int indexTo = findPastureIndex(pastureTo);
        horsesInPastures.get(indexFrom).remove(horse);
        horsesInPastures.get(indexTo).add(horse);

    }

    // might never be used, here if I need it
    // REQUIRES: pastures given actually exists
    // MODIFIES: this
    // EFFECTS: removes the given horse from a pasture
    public void removeHorse(Horse horse, String pasture) {
        int index = findPastureIndex(pasture);
        horsesInPastures.get(index).remove(horse);
    }

    // MODIFIES: this
    // EFFECTS: adds the given note to the list
    public void addNote(Note note) {
        notes.add(note);
    }

    // REQUIRES: index >= 0
    // MODIFIES: this
    // EFFECTS: removes the note at the given index
    public void removeNote(int index) {
        notes.remove(index);
    }

    // likely won't be used, but here if needed :)
    public void setDayOfWeek(String day) {
        this.dayOfWeek = day;
    }

    // Getters

    // REQUIRES: index >= 0
    // EFFECTS: returns the list of horses in the pasture given by the index
    // (indexes correspond to list of pastures that the Barn has)
    // (i.e. index 0 is the list of horses in the pasture at index 0 in the pastures
    // list)
    public ArrayList<Horse> getHorsesInPasture(int index) {
        return horsesInPastures.get(index);
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public ArrayList<ArrayList<Horse>> getHorsesInPastures() {
        return horsesInPastures;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("dayOfWeek", dayOfWeek);

        JSONArray jsonArrayOuter = new JSONArray();
        for (ArrayList<Horse> currentList : horsesInPastures) {
            JSONArray jsonArrayInner = new JSONArray();
            for (Horse currentHorse : currentList) {
                jsonArrayInner.put(currentHorse.toJson());
            }
            jsonArrayOuter.put(jsonArrayInner);
        }
        json.put("horsesInPastures", jsonArrayOuter);

        JSONArray jsonNotes = new JSONArray();
        for (Note current : notes) {
            jsonNotes.put(current.toJson());
        }
        json.put("notes", jsonNotes);

        return json;
    }

}
