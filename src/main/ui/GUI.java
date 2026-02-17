package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Barn;
import model.Horse;
import model.Note;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// CREDIT: Most of this code is based on the code in ListDemo

// Represents the main GUI
public class GUI extends JPanel implements ListSelectionListener {

    private JList list;
    private DefaultListModel listModel;
    private JList infoList;
    private DefaultListModel infoListModel;

    private static final String addString = "Add Horse";
    private static final String removeString = "Remove Horse";
    private JButton removeButton;
    private JButton addButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton viewInfoButton;
    private JTextField horseName;
    private JTextField ownerName;
    private JTextField ownerNum;
    private Barn barn;
    JScrollPane listScrollPane;

    private static final String JSON_STORE = "./data/barn.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // MODIFIES: this
    // EFFECTS: creates a GUI window
    public GUI() {
        super(new BorderLayout());
        initBasic();
        createButtons();
        createAddObjects();

        // creating the banner image on top
        File file = new File("./data/banner.jpg");
        try {
            BufferedImage image = ImageIO.read(file);
            JLabel banner = new JLabel(new ImageIcon(image));
            JPanel topBanner = new JPanel();
            topBanner.add(banner);
            add(topBanner, BorderLayout.NORTH);
        } catch (IOException e) {
            System.out.println("uh oh");
        }

        createListScrollPane();
        add(listScrollPane, BorderLayout.CENTER);
        add(createButtonPane(), BorderLayout.PAGE_END);
        add(createFieldPane(), BorderLayout.EAST);
    }

    // mostly here for testing and ease of demonstration
    // EFFECTS: Adds some basic info to the barn
    private void initBasic() {
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

    // EFFECTS: creates the buttons and fields needed for adding a horse
    private void createAddObjects() {
        // creating the buttons and fields
        addButton = new JButton(addString);
        AddListener addListener = new AddListener(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);

        horseName = new JTextField(10);
        horseName.addActionListener(addListener);
        horseName.getDocument().addDocumentListener(addListener);

        ownerName = new JTextField(10);
        ownerName.addActionListener(addListener);
        ownerName.getDocument().addDocumentListener(addListener);

        ownerNum = new JTextField(10);
        ownerNum.addActionListener(addListener);
        ownerNum.getDocument().addDocumentListener(addListener);
    }

    // EFFECTS: creates all the other buttons
    private void createButtons() {
        removeButton = new JButton(removeString);
        removeButton.setActionCommand(removeString);
        removeButton.addActionListener(new RemoveListener());

        saveButton = new JButton("Save");
        saveButton.setActionCommand("Save");
        saveButton.addActionListener(new SaveListener());

        loadButton = new JButton("Load");
        loadButton.setActionCommand("Load");
        loadButton.addActionListener(new LoadListener());

        viewInfoButton = new JButton("View Info");
        viewInfoButton.setActionCommand("View Info");
        viewInfoButton.addActionListener(new ViewInfoListener());
    }

    // EFFECTS: creates and returns the pane with the buttons
    private JPanel createButtonPane() {
        // pane with the buttons
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(removeButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(viewInfoButton);
        buttonPane.add(Box.createHorizontalStrut(15));
        buttonPane.add(saveButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(loadButton);
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(addButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return buttonPane;
    }

    // EFFECTS: creates and returns the pane with the fields
    // for info used when adding a new horse
    private JPanel createFieldPane() {
        // pane with fields for adding a horse
        JPanel fieldPane = new JPanel();
        fieldPane.setLayout(new BoxLayout(fieldPane,
                BoxLayout.Y_AXIS));
        JLabel name = new JLabel("Horse name");
        fieldPane.add(name);
        fieldPane.add(horseName);
        JLabel ownerNameLabel = new JLabel("Owner name");
        fieldPane.add(ownerNameLabel);
        fieldPane.add(ownerName);
        JLabel ownerNumLabel = new JLabel("Owner phone");
        fieldPane.add(ownerNumLabel);
        fieldPane.add(ownerNum);
        return fieldPane;
    }

    // EFFECTS: creates and returns the pane with the list of horses
    private void createListScrollPane() {
        // adding the horse names to the list to be displayed
        listModel = new DefaultListModel();
        for (Horse cur : barn.getHorses()) {
            listModel.addElement(cur.getHorseName());
        }

        // Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        listScrollPane = new JScrollPane(list);
    }

    // MODIFIES: this
    // EFFECTS: updates the displayed list of horses for when info is loaded
    private void load() {
        listModel.clear();
        for (Horse cur : barn.getHorses()) {
            listModel.addElement(cur.getHorseName());
        }

    }

    // MODIFIES: this
    // EFFECTS: updates a horse's name in the display list
    public void nameUpdated(String oldName, String newName) {
        listModel.set(listModel.indexOf(oldName), newName);
    }

    // EFFECTS: creates an info screen for the selected horse and shows it
    public void viewInfo(Horse horse) {
        // Create and set up the window.
        JFrame frame = new JFrame("BarnManager");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create and set up the content pane.
        JComponent newContentPane = new InfoScreen(horse, this);
        newContentPane.setOpaque(true); // content panes must be opaque
        frame.setContentPane(newContentPane);

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    // The object responsible for carrying out the task associated with
    // the remove horse button
    class RemoveListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: removes the selected horse from the barn
        public void actionPerformed(ActionEvent e) {
            // This method can be called only if
            // there's a valid selection
            // so go ahead and remove whatever's selected.
            int index = list.getSelectedIndex();
            barn.removeHorse((String) listModel.getElementAt(index));
            listModel.remove(index);

            int size = listModel.getSize();

            if (size == 0) { // Nobody's left, disable firing.
                removeButton.setEnabled(false);
            } else { // Select an index.
                if (index == listModel.getSize()) {
                    // removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    // The object responsible for carrying out the task associated with
    // the view info button
    class ViewInfoListener implements ActionListener {
        // EFFECTS: opens a new window with the horse's info and
        // functionality for changing it
        public void actionPerformed(ActionEvent e) {
            viewInfo(barn.getHorse((String) list.getSelectedValue()));
        }
    }

    // The object responsible for carrying out the task associated with
    // the save button
    class SaveListener implements ActionListener {
        // MODIFIES: GUI
        // EFFECTS: saves info
        public void actionPerformed(ActionEvent e) {
            jsonWriter = new JsonWriter(JSON_STORE);
            try {
                jsonWriter.open();
                jsonWriter.write(barn);
                jsonWriter.close();
                System.out.println("Saved " + barn.getBarnName() + " to " + JSON_STORE);
            } catch (FileNotFoundException exception) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    // The object responsible for carrying out the task associated with
    // the load button
    class LoadListener implements ActionListener {
        // MODIFIES: GUI
        // EFFECTS: loads info
        public void actionPerformed(ActionEvent e) {
            jsonReader = new JsonReader(JSON_STORE);
            try {
                barn = jsonReader.read();
                load();
                System.out.println("Loaded " + barn.getBarnName() + " from " + JSON_STORE);
            } catch (IOException exception) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }

    // The object responsible for carrying out the task associated with
    // the add horse button
    // This listener is shared by the text field and the hire button.
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        // Required by ActionListener.
        // MODIFIES: gui
        // EFFECTS: adds a horse to the barn with given info, taken from the fields
        // adds the new horse's name to be displayed in the list
        public void actionPerformed(ActionEvent e) {
            listModel.addElement(horseName.getText());
            barn.addHorse(new Horse(horseName.getText(), ownerName.getText(), ownerNum.getText()));

            // Reset the text fields.
            horseName.requestFocusInWindow();
            horseName.setText("");

            ownerName.requestFocusInWindow();
            ownerName.setText("");

            ownerNum.requestFocusInWindow();
            ownerNum.setText("");
        }

        // ALL METHODS BELOW DIRECT FROM LISTDEMO

        // Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        // Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        // Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    // This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                // No selection, disable fire button.
                removeButton.setEnabled(false);

            } else {
                // Selection, enable the fire button.
                removeButton.setEnabled(true);
            }
        }
    }

}

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * - Neither the name of Oracle or the names of its
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */