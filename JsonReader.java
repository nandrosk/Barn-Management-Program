package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

import model.Barn;
import model.Day;
import model.Horse;
import model.Note;
import model.Schedule;

// CREDIT: Almost all of the code in this class is based
// on code from JsonSerializationDemo

// Represents a reader that reads in a barn from JSON data stored in file
public class JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // CREDIT: all methods above are directly from JsonSerializationDemo

    // EFFECTS: reads barn from file and returns it
    // throws IOException if an error occurs reading data from file
    public Barn read() throws IOException {
        String jsonData = readFile(source);
        JSONObject json = new JSONObject(jsonData);
        String name = json.getString("name");
        ArrayList<String> pastures = parsePastures(json.getJSONArray("pastures"));
        ArrayList<Horse> horses = parseHorses(json.getJSONArray("horses"));
        Schedule schedule = parseDays(json.getJSONArray("days"), pastures);
        Barn barn = new Barn(name, pastures, horses, schedule);
        return barn;
        // stub
    }

    // EFFECTS: parses horses from json and returns them
    private ArrayList<Horse> parseHorses(JSONArray json) {
        ArrayList<Horse> horses = new ArrayList<Horse>();
        for (Object current : json) {
            Horse horse = parseHorse((JSONObject) current);
            horses.add(horse);
        }
        return horses;
    }

    // EFFECTS: parses horse from JSON object and returns it
    private Horse parseHorse(JSONObject jsonHorse) {
        String name = (String) jsonHorse.get("name");
        String owner = (String) jsonHorse.get("owner");
        String ownerPhone = (String) jsonHorse.get("ownerPhone");
        String emerContact = (String) jsonHorse.get("emergencyContactName");
        String emerPhone = (String) jsonHorse.get("emergencyContactPhone");
        String farrier = (String) jsonHorse.get("farrier");
        String vet = (String) jsonHorse.get("vet");
        Horse horse = new Horse(name,owner,ownerPhone,emerContact,emerPhone,farrier,vet);
        return horse;
    }

    // EFFECTS: parses pastures from json and returns them
    private ArrayList<String> parsePastures(JSONArray json) {
        ArrayList<String> pastures = new ArrayList<String>();
        for (Object current : json) {
            pastures.add((String) current);
        }
        return pastures;
    }

    // EFFECTS: parses days from json, adds them to a schedule,
    // and returns the schedule
    private Schedule parseDays(JSONArray json, ArrayList<String> pastures) {
        ArrayList<Day> days = new ArrayList<Day>();
        for (Object current : json) {
            days.add(parseDay((JSONObject) current, pastures));
        }
        Schedule schedule = new Schedule();
        schedule.setDays(days);
        return schedule;
    }

    // EFFECTS: parses a day from json and returns it
    private Day parseDay(JSONObject json, ArrayList<String> pastures) {
        String dayOfWeek = (String) json.get("dayOfWeek");
        ArrayList<ArrayList<Horse>> horsesInPastures = new ArrayList<ArrayList<Horse>>();
        JSONArray jsonHorses = json.getJSONArray("horsesInPastures");
        for (int i = 0; i < jsonHorses.length(); i++) {
            horsesInPastures.add(parseHorses(jsonHorses.getJSONArray(i)));
        }
        ArrayList<Note> notes = parseNotes(json.getJSONArray("notes"));
        Day day = new Day(dayOfWeek, pastures, horsesInPastures, notes);
        return day;
    }

    // EFFECTS: parses notes from jsonand returns them
    private ArrayList<Note> parseNotes(JSONArray json) {
        ArrayList<Note> notes = new ArrayList<Note>();
        for (Object current : json) {
            JSONObject jsonNote = (JSONObject) current;
            Note note = new Note((String) jsonNote.get("body"));
            notes.add(note);
        }
        return notes;
    }

}
