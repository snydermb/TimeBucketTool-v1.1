package MRBS.VED;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

class GlobalPanel extends JPanel implements ActionListener {
    private JTextField globalRangeA, globalRangeB, globalNameField;
    private JButton nextButton, doneButton;
    private JLabel globalPromptLabel;
    private JPanel backPanel;

    private HashMap<String, IDInstance> idMap;
    private ArrayList<String> globalNames;

    GlobalPanel(JPanel backPanel) {
        this.backPanel = backPanel;
        globalNames = new ArrayList<>();
        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        doneButton = new JButton("Done");
        doneButton.addActionListener(this);
        globalPromptLabel = new JLabel("Enter time bucket #1: ");
        JLabel toLabel = new JLabel("to");
        globalRangeA = new JTextField(10);
        globalRangeA.setText("XX/XX/XXXX");
        globalRangeB = new JTextField(10);
        globalRangeB.setText("XX/XX/XXXX");
        this.add(globalPromptLabel);
        this.add(globalRangeA);
        this.add(toLabel);
        this.add(globalRangeB);
        this.add(nextButton);
        this.add(doneButton);
        this.setVisible(false);
        JPanel globalNamePanel = new JPanel();
        JLabel globalNameLabel = new JLabel("Name?");
        globalNameField = new JTextField(10);
        globalNamePanel.add(globalNameLabel);
        globalNamePanel.add(globalNameField);
        globalNamePanel.setVisible(true);
        this.add(BorderLayout.SOUTH, globalNamePanel);
    }

    void setIdMap(HashMap<String, IDInstance> idMap) { this.idMap = idMap; }
    ArrayList<String> getGlobalNames() { return globalNames; }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextButton || e.getSource() == doneButton) {
            if (backPanel.isVisible()) {
                backPanel.setVisible(false);
            }
            //Pull and trim both dates from the text fields
            String dateA = globalRangeA.getText().trim();
            String dateB = globalRangeB.getText().trim();
            //Simple check to ensure correct 10-digit date format - will increase measures here later
            if (dateIsInvalid(dateA) || dateIsInvalid(dateB)) {
                if (dateIsInvalid(dateA)) {
                    globalRangeA.setText("Reformat date");
                }
                if (dateIsInvalid(dateB)) {
                    globalRangeB.setText("Reformat date");
                }
            } else {
                //If both dates are valid, create a new time bucket with the range and add to the global list
                Calendar calA = TimeBucketTool.stringToCal(dateA);
                Calendar calB = TimeBucketTool.stringToCal(dateB);
                String name = globalNameField.getText();
                if (name.equals("")) {
                    name = "GLOBAL_" + (globalNames.size()+1);
                }
                globalNames.add(name);
                for (IDInstance tmp : idMap.values()) {
                    tmp.createGlobal(name, calA, calB);
                }
                //If user hits "next", prompt for next bucket range
                if (e.getSource() == nextButton) {
                    globalRangeA.setText("");
                    globalRangeB.setText("");
                    globalNameField.setText("");
                    globalPromptLabel.setText("Enter time bucket #" + (globalNames.size()+1) + ": ");
                } else {
                    this.setVisible(false);
                    JPanel panel = (JPanel)this.getParent();
                    TimeBucketTool mTool = (TimeBucketTool) panel.getParent().getParent().getParent().getParent();
                    mTool.afterGlobalPanel();
                }
            }
        }
    }
    private static boolean dateIsInvalid(String date) {
        return ((!date.equals("null") && date.length() != 10) || date.contains("X"));
    }
}
