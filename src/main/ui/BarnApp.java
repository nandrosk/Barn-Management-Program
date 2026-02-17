package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import model.Barn;
import model.Horse;
import model.Note;
import persistence.JsonReader;
import persistence.JsonWriter;

//CREDIT: bascially every method is based off the methods in the teller project
// as well as the flashcard reviewer and how they deal with user input

// Represents the barn application as a whole
public class BarnApp {

    private Barn barn;
    private Scanner input;
    private static final String JSON_STORE = "./data/barn.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public BarnApp() {
        runApp();
    }

    // EFFECTS: initializes app with preset data
    // I want to allow the user to add this but using this will make
    // testing easier
    public void init() {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        ArrayList<String> pastures = new ArrayList<String>();
        pastures.add("P1");
        pastures.add("P6");
        pastures.add("Stall");
        barn = new Barn("Epona Stables LLC", pastures);
        barn.addHorse(new Horse("Fig", "Naomi", "553-9909"));
        barn.addHorse(new Horse("Brio", "Deanna", "550-4539"));
        Note note = new Note("Farrier appointment for Fig");
        barn.getSchedule().getDay(0).addNote(note);
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runApp() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        saveBeforeQuit();
    }

    // MODIFIES: this
    // EFFECTS: prompts the user to save before quitting
    private void saveBeforeQuit() {
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            System.out.println("\nWould you like to save before quitting? (y/n)");
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("y")) {
                save();
                System.out.println("\nGoodbye!");
                keepGoing = false;
            } else if (command.equals("n")) {
                System.out.println("\nGoodbye!");
                keepGoing = false;
            } else {
                System.out.println("Selection not valid...");
            }
        }
    }

    // CREDIT TO THE TELLER PROJECT
    // EFFECTS: displays the menu options
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\th -> View the horses in the barn");
        System.out.println("\tt-> View a day's turnout schedule");
        System.out.println("\tu -> Update schedule");
        System.out.println("\tn -> View a day's notes");
        System.out.println("\ts -> save work room to file");
        System.out.println("\tl -> load work room from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("h")) {
            viewHorsesInBarn();
        } else if (command.equals("t")) {
            viewTurnoutOnDay();
        } else if (command.equals("u")) {
            updateSchedule();
        } else if (command.equals("n")) {
            viewNotes();
        } else if (command.equals("s")) {
            save();
        } else if (command.equals("l")) {
            load();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: allows the user to update the barn schedule
    // add, move, or remove a horse
    private void updateSchedule() {
        // stub
    }

    // // MODIFIES: this
    // // EFFECTS: allows the user to update the info of the given horse
    private void updateHorse(Horse horse) {
        printUpdateOptions();

        String command = input.next();
        while (!command.equals("q")) {
            handleUpdateHorse(command, horse);
            printUpdateOptions();
            command = input.next();
        }

    }

    // EFFECTS: prints out the command options for updateHorse
    private void printUpdateOptions() {
        System.out.println("\tSelect from:");
        System.out.println("\tname -> Update horse name");
        System.out.println("\towner -> Update owner name");
        System.out.println("\towner phone -> Update owner phone");
        System.out.println("\temer -> Update emergency contact");
        System.out.println("\temer phone -> Update emergency contact phone");
        System.out.println("\tfarrier -> Update farrier");
        System.out.println("\tvet -> Update vet");
        System.out.println("\tq -> Quit to previous menu");
    }

    // EFFECTS: handles commands for updateHorse
    private void handleUpdateHorse(String command, Horse horse) {
        System.out.println("\tEnter the new info");
        String newInfo = input.next();
        if (command.equals("name")) {
            horse.setHorseName(newInfo);
        } else if (command.equals("owner")) {
            horse.setOwnerName(newInfo);
        } else if (command.equals("owner phone")) {
            horse.setOwnerPhone(newInfo);
        } else if (command.equals("emer")) {
            horse.setEmergencyContactName(newInfo);
        } else if (command.equals("emer phone")) {
            horse.setEmergencyContactPhone(newInfo);
        } else if (command.equals("farrier")) {
            horse.setFarrier(newInfo);
        } else if (command.equals("vet")) {
            horse.setVet(newInfo);
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: prints out the list of horses in the barn and
    // lets the user choose to add/remove a horse or
    // view a specfic horse's info
    private void viewHorsesInBarn() {
        ArrayList<Horse> horses = barn.getHorses();
        System.out.println("Horses at " + barn.getBarnName() + ":");
        for (Horse currentHorse : horses) {
            System.out.println(currentHorse.getHorseName());
        }
        System.out.println("-------");

        printViewHorsesCommands();

        String command = input.next();
        command = command.toLowerCase();
        while (!command.equals("q")) {
            handleViewHorsesCommands(command);
            printViewHorsesCommands();
            command = input.next();
        }

    }

    // EFFECTS: prints the command options ofr viewHorsesInBarn
    private void printViewHorsesCommands() {
        System.out.println("\tSelect from:");
        System.out.println("\tadd -> Add a horse");
        System.out.println("\trem -> Remove a horse");
        System.out.println("\tview -> View a horse's info");
        System.out.println("\tq -> Quit to menu");
    }

    // EFFECTS: handles the commands for the viewHorsesInBarn method
    // longer name needed for naming continuity
    @SuppressWarnings("methodlength")
    private void handleViewHorsesCommands(String command) {

        if (command.equals("add")) {
            System.out.println("\tPlease enter the horse's name");
            String horseName = input.next();
            System.out.println("\tPlease enter the owner's name");
            String ownerName = input.next();
            System.out.println("\tPlease enter the owner's phone");
            String ownerPhone = input.next();
            barn.addHorse(new Horse(horseName, ownerName, ownerPhone));
            System.out.println("\t" + horseName + " has been added!");
        } else if (command.equals("rem")) {
            System.out.println("\tPlease enter the horse's name");
            String horseName = input.next();
            if (barn.removeHorse(horseName)) {
                System.out.println("\t" + horseName + " has been removed!");
            } else {
                System.out.println("\tThat horse doesn't exist, please try again");
            }
        } else if (command.equals("view")) {
            System.out.println("\tPlease enter the horse's name");
            String horseName = input.next();
            if (barn.getHorse(horseName) != null) {
                viewHorseInfo(horseName);
                updateHorse(barn.getHorse(horseName));
            } else {
                System.out.println("\tThat horse doesn't exist, please try again");
            }
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: print out the horses in pastures on a given day
    private void viewTurnoutOnDay() {
        // stub
    }

    // EFFECTS: prints info about a given horse
    private void viewHorseInfo(String horseName) {
        System.out.println(barn.getHorse(horseName).toString());
    }

    // EFFECTS: prints out the notes for a given day
    private void viewNotes() {
        // which day would you like to view the notes for?
        System.out.println("\tWhich day would you like to view the notes for?");
        System.out.println("\tPlease enter a number from 0 to 6 (0 = Monday)");
        int day = input.nextInt();

        if (day < 7 && day >= 0) {
            ArrayList<Note> notes = barn.getSchedule().getDay(day).getNotes();
            System.out.println("\nNotes for " + barn.getSchedule().getDay(day).getDayOfWeek() + ":\n");
            for (int i = 0; i < notes.size(); i++) {
                System.out.println("Note " + i + ":");
                System.out.println(notes.get(i).toString());
            }
            // TO DO: go into add/remove/update branch here
        } else {
            System.out.println("Invalid input");
            viewNotes();
        }
    }

    // MODIFIES: this
    // EFFECTS: add, remove, or update a note on a given day
    private void updateNotes() {
        // add
        // rem
        // update
        // which note to update?
        //
    }

    // CREDIT: the following methods are based
    // heavily on code in JsonSerializationDemo

    // EFFECTS: saves the barn to file
    public void save() {
        try {
            jsonWriter.open();
            jsonWriter.write(barn);
            jsonWriter.close();
            System.out.println("Saved " + barn.getBarnName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads barn from file
    public void load() {
        try {
            barn = jsonReader.read();
            System.out.println("Loaded " + barn.getBarnName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
