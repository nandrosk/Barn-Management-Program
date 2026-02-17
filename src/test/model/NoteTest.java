package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NoteTest {
    
    String noteBody;
    Note note;

    @BeforeEach
    void runBefore() {
        noteBody = "Farrier Appointment for Fig, leave in stall";
        note = new Note(noteBody);
    }

    @Test
    void testConstructor() {
        assertEquals(noteBody, note.getNote());
    }

    @Test
    void testSetBody() {
        assertEquals(noteBody, note.getNote());
        note.setBody("Put Fig in P6");
        assertEquals("Put Fig in P6", note.getNote());

    }

    @Test
    void testToString() {
        String output = "-------\n" + note.getNote();
        assertEquals(output, note.toString());
    }

    @Test
    void testToJson() {
        JSONObject json = note.toJson();
        assertEquals(noteBody, json.get("body"));
    }

}