package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HorseTest {
    
    Horse fig;
    Horse brio;

    @BeforeEach
    void runBefore() {
        fig = new Horse("Fig", "Naomi", "553-9909");
        brio = new Horse("Brio", "Deanna", "550-4539");
    }

    @Test
    void testConstructor() {
        assertEquals("Fig", fig.getHorseName());
        assertEquals("Naomi", fig.getOwnerName());
        assertEquals("553-9909", fig.getOwnerPhone());
        assertEquals("null", fig.getEmergencyContactName());
        assertEquals("null", fig.getEmergencyConactPhone());
        assertEquals("null", fig.getFarrier());
        assertEquals("null", fig.getVet());
    }

    @Test
    void testOtherConstructor() {
        fig = new Horse("Fig", "Naomi", "553-9909", "Deanna", "206-550", "Sean", "Tacoma Equine");
        assertEquals("Fig", fig.getHorseName());
        assertEquals("Naomi", fig.getOwnerName());
        assertEquals("553-9909", fig.getOwnerPhone());
        assertEquals("Deanna", fig.getEmergencyContactName());
        assertEquals("206-550", fig.getEmergencyConactPhone());
        assertEquals("Sean", fig.getFarrier());
        assertEquals("Tacoma Equine", fig.getVet());
    }

    //for efficiency, I'll be testing all the simple setter methods here
    @Test 
    void testSetters() {
        fig.setHorseName("Mr. Pudding");
        fig.setOwnerName("Timothy");
        fig.setOwnerPhone("425-553-9909");
        fig.setEmergencyContactName("Deanna");
        fig.setEmergencyContactPhone("550-4539");
        fig.setFarrier("Sean");
        fig.setVet("Tacoma Equine");

        assertEquals("Mr. Pudding", fig.getHorseName());
        assertEquals("Timothy", fig.getOwnerName());
        assertEquals("425-553-9909", fig.getOwnerPhone());
        assertEquals("Deanna", fig.getEmergencyContactName());
        assertEquals("550-4539", fig.getEmergencyConactPhone());
        assertEquals("Sean", fig.getFarrier());
        assertEquals("Tacoma Equine", fig.getVet());
    }

    @Test
    void testToString() {
        String output = "\nName: " + fig.getHorseName() + "\nOwner: " + fig.getOwnerName()
                + "\nOwner phone: " + fig.getOwnerPhone() + "\nEmergency contact: " + fig.getEmergencyContactName()
                + "\nEmergency phone: " + fig.getEmergencyConactPhone() + "\nFarrer: " + fig.getFarrier()
                + "\nVet: " + fig.getVet();
        assertEquals(output, fig.toString());
    }

    @Test
    void testToJson() {
        JSONObject json = fig.toJson();
        // won't test every field, three should be enough since they're all
        // the same format
        assertEquals("Fig", json.get("name"));
        assertEquals("Naomi", json.get("owner"));
        assertEquals("null", json.get("vet"));
    }

}