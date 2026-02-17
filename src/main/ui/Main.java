package ui;

import javax.swing.*;

import model.Event;
import model.EventLog;

import java.awt.event.*;

// CREDIT: try-catch is based on code from JsonSerializationDemo

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to my project!");
        // BarnApp app = new BarnApp(); (uncomment this to use the old UI)
        createAndShowGUI();
    }

    // Create the GUI and show it.
    public static void createAndShowGUI() {
        // Create and set up the window.
        JFrame frame = new JFrame("BarnManager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add a listener so logged events are printed on close
        frame.addWindowListener(new WindowListener());

        // Create and set up the content pane.
        JComponent newContentPane = new GUI();
        newContentPane.setOpaque(true); // content panes must be opaque
        frame.setContentPane(newContentPane);

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    // EFFECTS: prints all logged events to the console
    private static void printLog(EventLog log) {
        System.out.println("Logged events:");
        for (Event next : log) {
            System.out.println(next.toString());
        }
    }

    // Represents an object that, when added to a window,
    // performs an action when that window closes
    // (printing out all logged events to the console)
    private static class WindowListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            printLog(EventLog.getInstance());
        }
    }
}
