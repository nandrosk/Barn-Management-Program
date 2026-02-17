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

public class JsonWriterTest extends JsonTest {

    Barn basicBarn;
    Barn fullBarn;
    Horse fig;
    Horse brio;
    Note note;

    @BeforeEach
    void runBefore() {
        ArrayList<String> pastures = new ArrayList<String>();
        pastures.add("P1");
        pastures.add("P6");
        pastures.add("Stall");
        basicBarn = new Barn("Epona Stables", pastures);
        fig = new Horse("Fig", "Naomi", "553-9909");
        brio = new Horse("Brio", "Deanna", "550-4539");
        basicBarn.addHorse(fig);
        basicBarn.addHorse(brio);

        fullBarn = new Barn("Epona Stables", pastures);
        fullBarn.addHorse(fig);
        fullBarn.addHorse(brio);
        note = new Note("Farrier appointment for Fig");
        fullBarn.getSchedule().getDay(0).addNote(note);
        fullBarn.getSchedule().getDay(0).addHorseToPasture(fig, "P1");
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterBasicBarn() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterBasicBarn.json");
            writer.open();
            writer.write(basicBarn);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterBasicBarn.json");
            basicBarn = reader.read();
            assertEquals("Epona Stables", basicBarn.getBarnName());
            assertEquals(3, basicBarn.getPastures().size());
            assertEquals(2, basicBarn.getHorses().size());
            checkHorse(fig, basicBarn.getHorses().get(0));
            checkHorse(brio, basicBarn.getHorses().get(1));

            assertEquals(7, basicBarn.getSchedule().getDays().size());
            Day day = basicBarn.getSchedule().getDay(0);
            assertEquals("M", day.getDayOfWeek());
            assertEquals(3, day.getHorsesInPastures().size());
            assertTrue(day.getNotes().isEmpty());
            assertEquals(0, day.findPastureIndex("P1"));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterBarnFullData() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterBarnFullData.json");
            writer.open();
            writer.write(fullBarn);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterBarnFullData.json");
            fullBarn = reader.read();
            assertEquals("Epona Stables", fullBarn.getBarnName());
            assertEquals(3, fullBarn.getPastures().size());
            assertEquals(2, fullBarn.getHorses().size());
            checkHorse(fig, fullBarn.getHorses().get(0));
            checkHorse(brio, fullBarn.getHorses().get(1));

            assertEquals(7, fullBarn.getSchedule().getDays().size());
            Day day = fullBarn.getSchedule().getDay(0);
            assertEquals("M", day.getDayOfWeek());
            assertEquals(3, day.getHorsesInPastures().size());
            assertEquals(note.getNote(), day.getNotes().get(0).getNote());
            assertEquals(0, day.findPastureIndex("P1"));
            checkHorse(fig, day.getHorsesInPasture(0).get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
