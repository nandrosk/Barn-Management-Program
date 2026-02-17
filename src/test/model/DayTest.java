package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DayTest {

    Day day;
    Horse fig;
    Horse brio;
    Note note1;
    Note note2;
    ArrayList<String> pastures;

    @BeforeEach
    void runBefore() {
        pastures = new ArrayList<String>();
        pastures.add("P1");
        pastures.add("P2");
        pastures.add("P6");
        pastures.add("Stall");
        day = new Day("Monday", pastures);
        fig = new Horse("Fig", "Naomi", "553-9909");
        brio = new Horse("Brio", "Deanna", "550-4539");
        note1 = new Note("Farrier Appointment for Fig on Monday, leave in stall");
        note2 = new Note("Put Brio in P6, he was inside yesterday");
    }

    @Test
    void testConstructor() {
        assertEquals("Monday", day.getDayOfWeek());
        assertEquals(4, day.getHorsesInPastures().size());
        // not checking every single entry to ensure empty, two is enough i think
        assertTrue((day.getHorsesInPasture(0)).isEmpty());
        assertTrue(day.getHorsesInPasture(1).isEmpty());
        assertEquals(0, day.getNotes().size());
    }

    @Test
    void testOtherConstructor() {
        ArrayList<ArrayList<Horse>> horsesInPastures = new ArrayList<ArrayList<Horse>>();
        ArrayList<Horse> pasture1 = new ArrayList<Horse>();
        pasture1.add(fig);
        horsesInPastures.add(pasture1);
        ArrayList<Note> notes = new ArrayList<Note>();
        notes.add(note1);
        notes.add(note2);

        day = new Day("Monday", pastures, horsesInPastures, notes);
        assertEquals("Monday", day.getDayOfWeek());
        assertEquals(1, day.getHorsesInPastures().size());
        assertEquals(fig, day.getHorsesInPasture(0).get(0));
        assertEquals(2, day.getNotes().size());
        assertEquals(note1, day.getNotes().get(0));
    }

    @Test
    void testFindPastureIndex() {
        assertEquals(0, day.findPastureIndex("P1"));
        assertEquals(3, day.findPastureIndex("Stall"));
    }

    @Test
    void testAddHorseToPasture() {
        day.addHorseToPasture(brio, "P1");
        assertEquals(1, day.getHorsesInPasture(0).size());
        assertEquals(brio, day.getHorsesInPasture(0).get(0));

        // multiple
        day.addHorseToPasture(fig, "P1");
        assertEquals(2, day.getHorsesInPasture(0).size());
        assertEquals(fig, day.getHorsesInPasture(0).get(1));

    }

    @Test
    void testMoveHorse() {
        day.addHorseToPasture(fig, "P2");
        assertEquals(fig, day.getHorsesInPasture(1).get(0));
        day.moveHorse(fig, "P2", "Stall");
        assertEquals(fig, day.getHorsesInPasture(3).get(0));
        assertEquals(0, day.getHorsesInPasture(1).size());

        // multiple
        day.addHorseToPasture(brio, "Stall");
        assertEquals(brio, day.getHorsesInPasture(3).get(1));
        day.moveHorse(fig, "Stall", "P6");
        assertEquals(fig, day.getHorsesInPasture(2).get(0));
        assertEquals(1, day.getHorsesInPasture(3).size());
        assertEquals(brio, day.getHorsesInPasture(3).get(0));

    }

    @Test
    void testRemoveHorse() {
        day.addHorseToPasture(fig, "P6");
        assertEquals(fig, day.getHorsesInPasture(2).get(0));
        day.removeHorse(fig, "P6");
        assertEquals(0, day.getHorsesInPasture(2).size());
    }

    @Test
    void testAddNote() {
        day.addNote(note1);
        assertEquals(1, day.getNotes().size());
        assertEquals(note1, day.getNotes().get(0));

        day.addNote(note2);
        assertEquals(2, day.getNotes().size());
        assertEquals(note2, day.getNotes().get(1));
    }

    @Test
    void testRemoveNote() {
        day.addNote(note1);
        day.addNote(note2);

        day.removeNote(0);
        assertEquals(1, day.getNotes().size());
        assertEquals(note2, day.getNotes().get(0));

        day.removeNote(0);
        assertEquals(0, day.getNotes().size());
    }

    @Test
    void testSetDay() {
        day.setDayOfWeek("Friday");
        assertEquals("Friday", day.getDayOfWeek());
    }

    @Test
    void testToJson() {
        day.addHorseToPasture(fig, "P1");
        JSONObject json = day.toJson();
        assertEquals("Monday", json.get("dayOfWeek"));
        assertEquals(4, json.getJSONArray("horsesInPastures").length());
        assertEquals("[" + fig.toJson() + "]", json.getJSONArray("horsesInPastures").get(0).toString());
        assertTrue(json.getJSONArray("notes").isEmpty());

        // test w/ note added
        day.addNote(note1);
        json = day.toJson();
        JSONObject note = (JSONObject) json.getJSONArray("notes").get(0);
        assertEquals(note1.getNote(), note.get("body"));
    }

}
