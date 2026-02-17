package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import persistence.JsonTest;

public class BarnTest extends JsonTest {

    Barn barn;
    Horse fig;
    Horse brio;
    ArrayList<String> pastures;

    @BeforeEach
    void runBefore() {
        pastures = new ArrayList<String>();
        pastures.add("P1");
        pastures.add("P6");
        pastures.add("Stall");
        barn = new Barn("Epona Stables", pastures);
        fig = new Horse("Fig", "Naomi", "553-9909");
        brio = new Horse("Brio", "Deanna", "550-4539");
        EventLog.getInstance().clear();
    }

    @Test
    void testConstructor() {
        assertEquals("Epona Stables", barn.getBarnName());
        assertEquals(0, barn.getHorses().size());
        assertEquals(3, barn.getPastures().size());
        assertEquals("P1", barn.getPastures().get(0));
        Schedule schedule = new Schedule(barn.getPastures());
        assertEquals(schedule.getDay(0).getDayOfWeek(), barn.getSchedule().getDay(0).getDayOfWeek());
    }

    @Test
    void testOtherConstructor() {
        ArrayList<Horse> horses = new ArrayList<Horse>();
        horses.add(fig);
        horses.add(brio);
        Schedule schedule = new Schedule(pastures);
        barn = new Barn("Epona Stables", pastures, horses, schedule);
        assertEquals("Epona Stables", barn.getBarnName());
        assertEquals(2, barn.getHorses().size());
        assertEquals(3, barn.getPastures().size());
        assertEquals("P1", barn.getPastures().get(0));
        assertEquals(fig, barn.getHorses().get(0));
        assertEquals(schedule.getDay(0).getDayOfWeek(), barn.getSchedule().getDay(0).getDayOfWeek());
    }

    @Test
    void testAddHorse() {
        barn.addHorse(fig);
        assertEquals(1, barn.getHorses().size());
        assertEquals(fig, barn.getHorses().get(0));
        assertEquals(fig, barn.getHorse("Fig"));

        barn.addHorse(brio);
        assertEquals(2, barn.getHorses().size());
        assertEquals(brio, barn.getHorses().get(1));
        assertEquals(brio, barn.getHorse("Brio"));

        Iterator<Event> iterator = EventLog.getInstance().iterator(); // to move past the "cleared" message
        iterator.next();
        assertEquals("Horse added: Fig", iterator.next().getDescription());
        assertEquals("Horse added: Brio", iterator.next().getDescription());
    }

    @Test
    void testRemoveHorse() {
        barn.addHorse(fig);
        barn.addHorse(brio);

        // case: horse does exist
        assertTrue(barn.removeHorse("Fig"));
        assertEquals(1, barn.getHorses().size());
        assertEquals(brio, barn.getHorses().get(0));

        // needed to move past the "cleared" and 2 "added" event log msgs
        Iterator<Event> iterator = EventLog.getInstance().iterator();
        iterator.next();
        iterator.next();
        iterator.next();
        assertEquals("Horse removed: Fig", iterator.next().getDescription());

        // case: horse doesn't exist
        assertFalse(barn.removeHorse("Livvy"));
        assertEquals(1, barn.getHorses().size());
        assertEquals(brio, barn.getHorses().get(0));
    }

    @Test
    void testSetBarnName() {
        barn.setBarnName("Willow Tree");
        assertEquals("Willow Tree", barn.getBarnName());
    }

    @Test
    void testGetHorseSpecialCase() {
        assertEquals(null, barn.getHorse("Livvy"));
    }

    @Test
    void testToJson() {
        barn.addHorse(fig);
        barn.addHorse(brio);
        JSONObject json = barn.toJson();
        System.out.println(json);
        assertEquals("Epona Stables", json.get("name"));
        assertEquals(3, json.getJSONArray("pastures").length());
        assertEquals("P1", json.getJSONArray("pastures").get(0));
        assertEquals(2, json.getJSONArray("horses").length());
        assertEquals("Fig", ((JSONObject) json.getJSONArray("horses").get(0)).get("name"));
        assertEquals("M", json.getJSONArray("days").getJSONObject(0).get("dayOfWeek"));
        assertEquals(3, json.getJSONArray("days").getJSONObject(0).getJSONArray("horsesInPastures").length());
    }

}
