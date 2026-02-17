package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ScheduleTest {
    
    Schedule schedule;
    ArrayList<String> pastures;

    @BeforeEach
    void runBefore() {
        pastures = new ArrayList<String>();
        schedule = new Schedule(pastures);
    }

    @Test
    void testConstructor() {
        assertEquals(7, schedule.getDays().size());
        assertEquals("M", schedule.getDay(0).getDayOfWeek());
        assertEquals("T", schedule.getDay(1).getDayOfWeek());
        assertEquals("Th", schedule.getDay(3).getDayOfWeek());
        //etc.

        assertEquals(pastures.size(), schedule.getDay(0).getHorsesInPastures().size());
        assertEquals(pastures.size(), schedule.getDay(1).getHorsesInPastures().size());
        //etc.
    }

    @Test
    void testOtherConstructor() {
        Schedule other = new Schedule();
        assertEquals(null, other.getDays());
    }

    @Test
    void testSetDays() {
        assertFalse(schedule.getDays().isEmpty());
        ArrayList<Day> days = new ArrayList<Day>();
        schedule.setDays(days);
        assertTrue(schedule.getDays().isEmpty());
    }

}