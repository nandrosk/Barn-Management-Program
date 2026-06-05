package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Barn;
import model.Day;
import model.Horse;
import model.Schedule;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

// Represents the screen that pops up when viewing a horse's info
public class ScheduleScreen extends JPanel implements ListSelectionListener {

    private GUI gui;
    private Day day;
    private Barn barn;
    
    private JList pastureList;
    private JButton removeButton;

    // MODIFIES: this
    // EFFECTS: creates an info screen for the given horse
    // for the associated GUI
    public ScheduleScreen(int dayOfTheWeek, GUI gui, Barn barn) {
        super(new BorderLayout());
        this.day = barn.getSchedule().getDay(dayOfTheWeek);
        this.gui = gui;
        this.barn = barn;

        // creating the banner image on top
        File file = new File("./data/banner.jpg");
        try {
            BufferedImage image = ImageIO.read(file);
            JLabel banner = new JLabel(new ImageIcon(image));
            JPanel topBanner = new JPanel();
            topBanner.add(banner);
            add(topBanner, BorderLayout.NORTH);
        } catch (IOException e) {
            System.out.println("Info screen banner didn't render correctly");
        }

        //add(createHorsesInPasturePane(), BorderLayout.CENTER);
        add(createButtonPane(), BorderLayout.PAGE_END);
        add(createPasturesPane(), BorderLayout.WEST);
        add(createStallsPane(), BorderLayout.EAST);
    }

    // EFFECTS: creates and returns the pane with the horse's info
    private JScrollPane createHorsesInPasturePane() {
        // adding the horse info to the list to be displayed
        DefaultListModel horsesInPastureListModel = new DefaultListModel();
        //horsesInPastureListModel.addElement(horse.getHorseName());


        // Create the list of horse in selected pasture put it in a scroll pane to display info
        pastureList = new JList(horsesInPastureListModel);
        pastureList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pastureList.setSelectedIndex(0);
        pastureList.addListSelectionListener(this);
        pastureList.setVisibleRowCount(5);
        JScrollPane infoListScrollPane = new JScrollPane(pastureList);
        return infoListScrollPane;
    }

    // EFFECTS: creates and returns the pane with the buttons
    private JPanel createButtonPane() {
        // creating the button and field
        removeButton = new JButton("Remove");
        RemoveListener removeListener = new RemoveListener(removeButton);
        removeButton.setActionCommand("Remove");
        removeButton.addActionListener(removeListener);
        removeButton.setEnabled(false);

        // pane with the button and field
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(removeButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 165, 5, 5));
        return buttonPane;
    }

    // EFFECTS: creates and returns the pane with the pastures
    private JPanel createPasturesPane() {
        // creating the pane displaying the pastures and adding a label
        JPanel pasturesPane = new JPanel();
        pasturesPane.setLayout(new BoxLayout(pasturesPane,
                BoxLayout.Y_AXIS));
        JLabel pasturesLabel = new JLabel("Pastures:");
        pasturesPane.add(pasturesLabel);

        // Create the list of pastures and put it in a scroll pane to display info
        DefaultListModel pastureListModel = new DefaultListModel();
        for (String pasture : barn.getPastures()) {
            pastureListModel.addElement(pasture);
        }
        pastureList = new JList(pastureListModel);
        pastureList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pastureList.setSelectedIndex(0);
        pastureList.addListSelectionListener(this);
        pastureList.setVisibleRowCount(5);
        JScrollPane pastureListScrollPane = new JScrollPane(pastureList);
        
        pasturesPane.add(pastureListScrollPane);

        //pasturesPane.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        return pasturesPane;
    }

    // EFFECTS: creates and returns the pane with the list of horses in stalls 
    // (the default place for horses in the schedule)
    private JPanel createStallsPane() {
        // creating the pane displaying the pastures and adding a label
        JPanel stallsPane = new JPanel();
        stallsPane.setLayout(new BoxLayout(stallsPane,
                BoxLayout.Y_AXIS));
        JLabel stallsLabel = new JLabel("Stalls:");
        stallsPane.add(stallsLabel);

        // Create the list of pastures and put it in a scroll pane to display info
        DefaultListModel stallsListModel = new DefaultListModel();
        for (Horse horse : barn.getHorses()) {
            stallsListModel.addElement(horse.getHorseName());
        }
        JList stallsList = new JList(stallsListModel);
        stallsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        stallsList.setSelectedIndex(0);
        stallsList.addListSelectionListener(this);
        stallsList.setVisibleRowCount(5);
        JScrollPane stallListScrollPane = new JScrollPane(stallsList);
        
        stallsPane.add(stallListScrollPane);

        //stallsPane.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        return stallsPane;
    }


    // This is the object that is responsible carrying out the task associated
    // with the remove button.
    class RemoveListener implements ActionListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public RemoveListener(JButton button) {
            this.button = button;
        }

        // Required by ActionListener.
        // MODIFIES: GUI
        // EFFECTS: Changes the selected piece of information to
        // the information inputted into the field
        public void actionPerformed(ActionEvent e) {
            // TO DO
            // add code here to add functionality for remove button
        }

        // ALL METHODS BELOW DIRECT FROM LISTDEMO

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

    }

    // This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (pastureList.getSelectedIndex() == -1) {
                // No selection, disable fire button.
                removeButton.setEnabled(false);

            } else {
                // Selection, enable the fire button.
                removeButton.setEnabled(true);
            }
        }
    }
}

