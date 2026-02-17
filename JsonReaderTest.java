package persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Barn;
import model.Day;
import model.Horse;
import model.Note;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.ArrayList;

// CREDIT: Based heavily on tests in JsonSerializationDemo

public class JsonReaderTest extends JsonTest {

    private Horse basicFig;
    private Horse basicBrio;
    private Horse fullFig;
    private Horse fullBrio;
    private ArrayList<Note> notes;

    @BeforeEach
    void runBefore() {
        basicFig = new Horse("Fig", "Naomi", "9909");
        basicBrio = new Horse("Brio", "Deanna", "4539");
        fullFig = new Horse("Fig", "Naomi", "9909", "Deanna", "null", "Sean", "null");
        fullBrio = new Horse("Brio", "Deanna", "4539", "null", "9909", "null", "Tacoma Equine");
        Note note = new Note("Farrier appointment for Fig, leave in");
        notes = new ArrayList<Note>();
        notes.add(note);
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Barn barn = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderBasicBarn() {
        JsonReader reader = new JsonReader("./data/testReaderBasicBarn.json");
        try {
            Barn barn = reader.read();
            assertEquals("Epona Stables", barn.getBarnName());

            assertEquals(3, barn.getPastures().size());
            assertEquals("P1", barn.getPastures().get(0));

            assertEquals(2, barn.getHorses().size());
            checkHorse(basicFig, barn.getHorses().get(0));
            checkHorse(basicBrio, barn.getHorses().get(1));

            assertEquals(7, barn.getSchedule().getDays().size());
            Day day = barn.getSchedule().getDay(0);
            assertEquals("M", day.getDayOfWeek());
            assertEquals(barn.getPastures().size(), day.getHorsesInPastures().size());
            assertTrue(day.getNotes().isEmpty());
            assertEquals(0, day.findPastureIndex("P1"));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderBarnFullData() {
        JsonReader reader = new JsonReader("./data/testReaderBarnFullData.json");
        try {
            Barn barn = reader.read();
            assertEquals("Epona Stables", barn.getBarnName());

            assertEquals(3, barn.getPastures().size());
            assertEquals("P1", barn.getPastures().get(0));

            assertEquals(2, barn.getHorses().size());
            checkHorse(fullFig, barn.getHorses().get(0));

            assertEquals(7, barn.getSchedule().getDays().size());
            Day day = barn.getSchedule().getDay(0);
            assertEquals("M", day.getDayOfWeek());
            assertEquals(barn.getPastures().size(), day.getHorsesInPastures().size());
            assertEquals(0, day.findPastureIndex("P1"));

            checkNotes(notes, day.getNotes());

            checkHorse(fullFig, day.getHorsesInPasture(0).get(0));
            checkHorse(fullBrio, day.getHorsesInPasture(0).get(1));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
