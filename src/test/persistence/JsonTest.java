package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import model.Horse;
import model.Note;

// CREDIT: Based heavily on JsonTest in JsonSerializationDemo

public class JsonTest {

    protected void checkHorse(Horse expected, Horse given) {
        assertEquals(expected.getHorseName(), given.getHorseName());
        assertEquals(expected.getOwnerName(), given.getOwnerName());
        assertEquals(expected.getOwnerPhone(), given.getOwnerPhone());
        assertEquals(expected.getEmergencyContactName(), given.getEmergencyContactName());
        assertEquals(expected.getEmergencyConactPhone(), given.getEmergencyConactPhone());
        assertEquals(expected.getFarrier(), given.getFarrier());
        assertEquals(expected.getVet(), given.getVet());
    }

    protected void checkNotes(ArrayList<Note> expected, ArrayList<Note> given) {
        assertEquals(expected.size(), given.size());
        for (int i = 0; i < given.size(); i++) {
            assertEquals(expected.get(i).getNote(), given.get(i).getNote());
        }
    }

}
