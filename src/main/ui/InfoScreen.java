package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Horse;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

// CREDIT: Most of this code is based on the code in ListDemo

// Represents the screen that pops up when viewing a horse's info
public class InfoScreen extends JPanel implements ListSelectionListener {

    private JList infoList;
    private DefaultListModel infoListModel;
    private GUI gui;

    private JButton changeInfoButton;
    private JTextField infoField;
    private Horse horse;

    // MODIFIES: this
    // EFFECTS: creates an info screen for the given horse
    // for the associated GUI
    public InfoScreen(Horse horse, GUI gui) {
        super(new BorderLayout());
        this.horse = horse;
        this.gui = gui;

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

        add(createScrollPane(), BorderLayout.CENTER);
        add(createButtonPane(), BorderLayout.PAGE_END);
        add(createLabelPane(), BorderLayout.WEST);
    }

    // EFFECTS: creates and returns the pane with the horse's info
    private JScrollPane createScrollPane() {
        // adding the horse info to the list to be displayed
        infoListModel = new DefaultListModel();
        infoListModel.addElement(horse.getHorseName());
        infoListModel.addElement(horse.getOwnerName());
        infoListModel.addElement(horse.getOwnerPhone());
        infoListModel.addElement(horse.getEmergencyContactName());
        infoListModel.addElement(horse.getEmergencyConactPhone());
        infoListModel.addElement(horse.getFarrier());
        infoListModel.addElement(horse.getVet());

        // Create the list and put it in a scroll pane to display info
        infoList = new JList(infoListModel);
        infoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        infoList.setSelectedIndex(0);
        infoList.addListSelectionListener(this);
        infoList.setVisibleRowCount(5);
        JScrollPane infoListScrollPane = new JScrollPane(infoList);
        return infoListScrollPane;
    }

    // EFFECTS: creates and returns the pane with the button and field
    private JPanel createButtonPane() {
        // creating the button and field
        changeInfoButton = new JButton("Change Info");
        ChangeInfoListener changeInfoListener = new ChangeInfoListener(changeInfoButton);
        changeInfoButton.setActionCommand("Change Info");
        changeInfoButton.addActionListener(changeInfoListener);
        changeInfoButton.setEnabled(false);

        infoField = new JTextField(10);
        infoField.addActionListener(changeInfoListener);
        infoField.getDocument().addDocumentListener(changeInfoListener);

        // pane with the button and field
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(infoField);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(changeInfoButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 165, 5, 5));
        return buttonPane;
    }

    // EFFECTS: creates and returns the pane with the labels for 
    // each piece of info
    private JPanel createLabelPane() {
        // creating the pane with all the field labels
        ArrayList<JLabel> labels = new ArrayList<JLabel>();
        labels.add(new JLabel("Horse Name:"));
        labels.add(new JLabel("Owner Name:"));
        labels.add(new JLabel("Owner Phone:"));
        labels.add(new JLabel("Emergency Contact Name:"));
        labels.add(new JLabel("Emergency Contact Phone:"));
        labels.add(new JLabel("Farrier:"));
        labels.add(new JLabel("Vet:"));
        JPanel labelPane = new JPanel();
        labelPane.setLayout(new BoxLayout(labelPane,
                BoxLayout.Y_AXIS));
        for (JLabel curr : labels) {
            labelPane.add(curr);
        }
        labelPane.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        return labelPane;
    }

    // Is the object that is responsible carrying out the task associated
    // with the change info button.
    // This listener is shared by the text field and the change info button.
    class ChangeInfoListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public ChangeInfoListener(JButton button) {
            this.button = button;
        }

        // Required by ActionListener.
        // MODIFIES: GUI
        // EFFECTS: Changes the selected piece of information to
        // the information inputted into the field
        public void actionPerformed(ActionEvent e) {
            int index = infoList.getSelectedIndex();
            String oldName = (String) infoList.getSelectedValue();
            String newInfo = infoField.getText();

            infoListModel.set(index, newInfo);
            if (index == 0) {
                horse.setHorseName(newInfo);
                gui.nameUpdated(oldName, newInfo);
            } else if (index == 1) {
                horse.setOwnerName(newInfo);
            } else if (index == 2) {
                horse.setOwnerPhone(newInfo);
            } else if (index == 3) {
                horse.setEmergencyContactName(newInfo);
            } else if (index == 4) {
                horse.setEmergencyContactPhone(newInfo);
            } else if (index == 5) {
                horse.setFarrier(newInfo);
            } else {
                horse.setVet(newInfo);
            }

            // Reset the text field
            infoField.requestFocusInWindow();
            infoField.setText("");
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

            if (infoList.getSelectedIndex() == -1) {
                // No selection, disable fire button.
                changeInfoButton.setEnabled(false);

            } else {
                // Selection, enable the fire button.
                changeInfoButton.setEnabled(true);
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