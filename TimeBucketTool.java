package MRBS.VED;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

final class TimeBucketTool extends JFrame implements ActionListener {

    private JLabel globalPromptLabel;
    private JLabel enterPreLabel, enterPostLabel;
    private JTextField globalRangeA, globalRangeB;
    private JTextField preDaysField, postDaysField, interLengthField, interBeforeField, interAfterField;
    private JTextField enterPreField, enterPostField, preNameField, postNameField, globalNameField;
    private JButton globalButton, perIDButton;
    private JButton nextButton, doneButton, backButton;
    private JButton preDaysButton, postDaysButton, interLengthButton, interBeforeButton, interAfterButton;
    private JButton uniformButton, manualButton;
    private JButton enterPreButton, enterPostButton;
    private JCheckBox allPreCheck, allPostCheck, allPreWDateCheck, allPostWDateCheck;
    private JPanel backPanel, globalPanel, globalNamePanel;
    private JPanel optPanel1, optPanel2, optPanel3, optPanel4, optPanel5, optPanel6;
    private JPanel optPanel8, optPanel9;
    private JPanel allPrePanel, allPostPanel;

    private IntroPanel introPanel;
    private InterTypePanel interTypePanel;
    private DataFilePanel dataFilePanel;
    private OutPanel outPanel;

    private HashMap<String, IDInstance> idMap;
    private ArrayList<String> nameList;
    private int daysPre, daysPost, interType, interLength, numPreIntervals, numPostIntervals, globalBuckets;
    private int[] pre, post;
    private String[] preNames, postNames;
    private boolean[] allPre = new boolean[2];
    private boolean[] allPost = new boolean[2];

    TimeBucketTool() {
        //Main collection for storing IDs
        idMap = new HashMap<>();

        //Main collection for storing bucket (interval) names
        nameList = new ArrayList<>();

        //Set basics
        this.setTitle("TimeBucketTool v1.1");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,600);

        //Instantiate intro (first) panel
        introPanel = new IntroPanel();

        //Instantiate panel for reading data file (second to last)
        dataFilePanel = new DataFilePanel();
        dataFilePanel.setVisible(false);

        //Instantiate out file name panel (last)
        outPanel = new OutPanel();
        outPanel.setVisible(false);

        //Setup back button panel
        backPanel = new JPanel();
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backPanel.add(backButton);
        backPanel.setVisible(false);

        //Set option panel 1
        optPanel1 = new JPanel();
        globalButton = new JButton("Global?");
        globalButton.addActionListener(this);
        perIDButton = new JButton("Per ID?");
        perIDButton.addActionListener(this);
        optPanel1.add(globalButton);
        optPanel1.add(perIDButton);
        optPanel1.setVisible(false);

        //Set global panel
        globalPanel = new JPanel();
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
        globalPanel.add(globalPromptLabel);
        globalPanel.add(globalRangeA);
        globalPanel.add(toLabel);
        globalPanel.add(globalRangeB);
        globalPanel.add(nextButton);
        globalPanel.add(doneButton);
        globalPanel.setVisible(false);
        globalNamePanel = new JPanel();
        JLabel globalNameLabel = new JLabel("Name?");
        globalNameField = new JTextField(10);
        globalNamePanel.add(globalNameLabel);
        globalNamePanel.add(globalNameField);
        globalNamePanel.setVisible(false);

        //Set option panel 2
        optPanel2 = new JPanel();
        uniformButton = new JButton("Uniform length?");
        uniformButton.addActionListener(this);
        manualButton = new JButton("Variable length?");
        manualButton.addActionListener(this);
        optPanel2.add(uniformButton);
        optPanel2.add(manualButton);
        optPanel2.setVisible(false);

        //Set interval type panel
        interTypePanel = new InterTypePanel();
        interTypePanel.setVisible(false);

        //Set option panels 3,4,5,6, intervalType
        optPanel3 = new JPanel();
        optPanel4 = new JPanel();
        optPanel5 = new JPanel();
        optPanel6 = new JPanel();
        preDaysButton = new JButton("Ok");
        postDaysButton = new JButton("Ok");
        interAfterButton = new JButton("Done!");
        interBeforeButton = new JButton("Ok");
        interLengthButton = new JButton("Ok");
        preDaysButton.addActionListener(this);
        postDaysButton.addActionListener(this);
        interAfterButton.addActionListener(this);
        interBeforeButton.addActionListener(this);
        interLengthButton.addActionListener(this);
        JLabel preDaysLabel = new JLabel("Days PRE to begin intervals: ");
        JLabel postDaysLabel = new JLabel("Days POST to begin intervals: ");
        JLabel intervalLengthLabel = new JLabel("Length of each interval in specified amount: ");
        JLabel interBeforeLabel = new JLabel("# of intervals pre: ");
        JLabel interAfterLabel = new JLabel("# of intervals post: ");
        preDaysField = new JTextField(5);
        postDaysField = new JTextField(5);
        interLengthField = new JTextField(5);
        interBeforeField = new JTextField(5);
        interAfterField = new JTextField(5);
        optPanel3.add(preDaysLabel);
        optPanel3.add(preDaysField);
        optPanel3.add(preDaysButton);
        optPanel3.add(postDaysLabel);
        optPanel3.add(postDaysField);
        optPanel3.add(postDaysButton);
        optPanel4.add(intervalLengthLabel);
        optPanel4.add(interLengthField);
        optPanel4.add(interLengthButton);
        optPanel5.add(interBeforeLabel);
        optPanel5.add(interBeforeField);
        optPanel5.add(interBeforeButton);
        optPanel6.add(interAfterLabel);
        optPanel6.add(interAfterField);
        optPanel6.add(interAfterButton);
        optPanel3.setVisible(false);
        optPanel4.setVisible(false);
        optPanel5.setVisible(false);
        optPanel6.setVisible(false);

        //Set option panels 8,9 (entry for manual pre and post bucket lengths)
        optPanel8 = new JPanel();
        optPanel9 = new JPanel();
        enterPreLabel = new JLabel("Enter PRE interval length #1: ");
        enterPreField = new JTextField(5);
        enterPreButton = new JButton("Ok");
        enterPreButton.addActionListener(this);
        enterPostLabel = new JLabel("Enter POST interval length #1: ");
        enterPostField = new JTextField(5);
        enterPostButton = new JButton("Ok");
        enterPostButton.addActionListener(this);
        JLabel preNameLabel = new JLabel("Name?");
        JLabel postNameLabel = new JLabel("Name?");
        preNameField = new JTextField(10);
        postNameField = new JTextField(10);
        optPanel8.add(enterPreLabel);
        optPanel8.add(enterPreField);
        optPanel8.add(preNameLabel);
        optPanel8.add(preNameField);
        optPanel8.add(enterPreButton);
        optPanel9.add(enterPostLabel);
        optPanel9.add(enterPostField);
        optPanel9.add(postNameLabel);
        optPanel9.add(postNameField);
        optPanel9.add(enterPostButton);
        optPanel8.setVisible(false);
        optPanel9.setVisible(false);

        //Set option panel 10/11 (option for all pre/all post buckets)
        allPrePanel = new JPanel();
        allPostPanel = new JPanel();
        JLabel allPreLabel = new JLabel("All pre bucket?");
        JLabel allPreWDateLabel = new JLabel("Include Dx/CPT date?");
        JLabel allPostLabel = new JLabel("All post bucket?");
        JLabel allPostWDateLabel = new JLabel("Include Dx/CPT date?");
        allPreCheck = new JCheckBox();
        allPreCheck.addActionListener(this);
        allPreWDateCheck = new JCheckBox();
        allPreWDateCheck.addActionListener(this);
        allPreWDateCheck.setEnabled(false);
        allPostCheck = new JCheckBox();
        allPostCheck.addActionListener(this);
        allPostWDateCheck = new JCheckBox();
        allPostWDateCheck.addActionListener(this);
        allPostWDateCheck.setEnabled(false);
        allPrePanel.add(allPreLabel);
        allPrePanel.add(allPreCheck);
        allPrePanel.add(allPreWDateLabel);
        allPrePanel.add(allPreWDateCheck);
        allPostPanel.add(allPostLabel);
        allPostPanel.add(allPostCheck);
        allPostPanel.add(allPostWDateLabel);
        allPostPanel.add(allPostWDateCheck);
        allPrePanel.setVisible(false);
        allPostPanel.setVisible(false);

        //Combine panels in center
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.PAGE_AXIS));
        fieldPanel.add(optPanel1);
        fieldPanel.add(globalPanel);
        fieldPanel.add(globalNamePanel);
        fieldPanel.add(optPanel2);
        fieldPanel.add(allPrePanel);
        fieldPanel.add(allPostPanel);
        fieldPanel.add(optPanel3);
        fieldPanel.add(interTypePanel);
        fieldPanel.add(optPanel4);
        fieldPanel.add(optPanel5);
        fieldPanel.add(optPanel6);
        fieldPanel.add(optPanel8);
        fieldPanel.add(optPanel9);
        fieldPanel.add(backPanel);

        //Add panels to respective areas and set visible
        this.getContentPane().add(BorderLayout.NORTH, introPanel);
        this.getContentPane().add(BorderLayout.CENTER, fieldPanel);
        this.getContentPane().add(BorderLayout.SOUTH, dataFilePanel);
        this.setVisible(true);
    }

    void afterIntro() {
        optPanel1.setVisible(true);
        this.idMap = introPanel.getIdMap();
        dataFilePanel.setIdMap(idMap);
    }
    void afterDataPanel() {
        dataFilePanel.setVisible(false);
        this.remove(dataFilePanel);
        this.getContentPane().add(BorderLayout.SOUTH, outPanel);
        outPanel.setIdMap(idMap);
        outPanel.setNameList(nameList);
        outPanel.setVisible(true);
    }
    void afterInterType(int interType) {
        this.interType = interType;
    }

    //Bulk of the actual code
    @Override
    public void actionPerformed(ActionEvent e) {
        //Action for "Global" button
        if (e.getSource() == globalButton) {
            optPanel1.setVisible(false);
            globalBuckets = 0;
            //And then we open up the global panel where the global fields are kept
            globalPanel.setVisible(true);
            globalNamePanel.setVisible(true);
            //allPrePanel.setVisible(true);
            //allPostPanel.setVisible(true);
            backPanel.setVisible(true);
        }
        //Action for global "Next" and "Done" buttons
        else if (e.getSource() == nextButton || e.getSource() == doneButton) {
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
                globalBuckets++;
                Calendar calA = stringToCal(dateA);
                Calendar calB = stringToCal(dateB);
                String name = globalNameField.getText();
                if (name.equals("")) {
                    name = "GLOBAL_" + globalBuckets;
                }
                nameList.add(name);
                for (IDInstance tmp : idMap.values()) {
                    tmp.createGlobal(name, calA, calB);
                }
                //If user hits "next", prompt for next bucket range
                if (e.getSource() == nextButton) {
                    globalRangeA.setText("");
                    globalRangeB.setText("");
                    globalNameField.setText("");
                    globalPromptLabel.setText("Enter time bucket #" + (globalBuckets+1) + ": ");
                } else {
                    globalPanel.setVisible(false);
                    globalNamePanel.setVisible(false);
                    dataFilePanel.setVisible(true);
                }
            }
        }
        //Action for "Per ID" button
        else if (e.getSource() == perIDButton) {
            optPanel1.setVisible(false);
            optPanel2.setVisible(true);
            backPanel.setVisible(true);
        }
        //PER ID OPTION CHOSEN:
        //Action for "Uniformly Set?" button
        else if (e.getSource() == uniformButton) {
            optPanel2.setVisible(false);
            backPanel.setVisible(false);
            allPrePanel.setVisible(true);
            allPostPanel.setVisible(true);
            optPanel3.setVisible(true);
            interTypePanel.setVisible(true);
            optPanel4.setVisible(true);
            optPanel5.setVisible(true);
            optPanel6.setVisible(true);
        }
        //UNIFORM OPTION CHOSEN:
        //Action for "Pre Days" button
        else if (e.getSource() == preDaysButton) {
            String text = preDaysField.getText();
            if (isInteger(text)) {
                daysPre = Integer.parseInt(text);
                preDaysButton.setVisible(false);
                preDaysField.setEditable(false);
            }
        }
        //Action for "Post Days" button
        else if (e.getSource() == postDaysButton) {
            String text = postDaysField.getText();
            if (isInteger(text)) {
                daysPost = Integer.parseInt(text);
                postDaysButton.setVisible(false);
                postDaysField.setEditable(false);
            }
        }
        //Action for "Interval Length" button
        else if (e.getSource() == interLengthButton) {
            String text = interLengthField.getText();
            if (isInteger(text)) {
                interLength = Integer.parseInt(text);
                interLengthButton.setVisible(false);
                interLengthField.setEditable(false);
            }
        }
        //Action for "Pre Intervals" button
        else if (e.getSource() == interBeforeButton) {
            String text = interBeforeField.getText();
            if (isInteger(text)) {
                numPreIntervals = Integer.parseInt(text);
                pre = new int[numPreIntervals];
                preNames = new String[numPreIntervals];
                interBeforeButton.setVisible(false);
                interBeforeField.setEditable(false);
            }
        }
        //Action for "Post Intervals" button
        else if (e.getSource() == interAfterButton) {
            String text = interAfterField.getText();
            if (isInteger(text)) {
                numPostIntervals = Integer.parseInt(text);
                post = new int[numPostIntervals];
                postNames = new String[numPostIntervals];
                if (optPanel4.isVisible()) {
                    interAfterButton.setVisible(false);
                    interAfterField.setEditable(false);
                    uniformLengths(pre);
                    uniformLengths(post);
                    uniformNames(preNames, "PRE_");
                    uniformNames(postNames, "POST_");
                    for (IDInstance tmp : idMap.values()) {
                        tmp.createBuckets(daysPre, daysPost, interType, pre, post, preNames, postNames, allPre, allPost);
                    }
                    dataFilePanel.setVisible(true);
                } else {
                    optPanel5.setVisible(false);
                    optPanel6.setVisible(false);
                    optPanel8.setVisible(true);
                    optPanel9.setVisible(true);
                }
            }
        }
        //MANUALLY SET OPTION CHOSEN:
        //Action for "Manually set?" button
        else if (e.getSource() == manualButton) {
            optPanel2.setVisible(false);
            backPanel.setVisible(false);
            optPanel3.setVisible(true);
            allPrePanel.setVisible(true);
            allPostPanel.setVisible(true);
            interTypePanel.setVisible(true);
            optPanel5.setVisible(true);
            optPanel6.setVisible(true);
        }
        //Action for "Enter pre intervals" button
        else if (e.getSource() == enterPreButton) {
            updateIntervalArray(pre, preNames, numPreIntervals, enterPreButton, enterPreLabel, enterPreField, preNameField);
        }
        //Action for "Enter post intervals" button
        else if (e.getSource() == enterPostButton) {
            if (updateIntervalArray(post, postNames, numPostIntervals, enterPostButton, enterPostLabel, enterPostField, postNameField)) {
                for (IDInstance tmp : idMap.values()) {
                    tmp.createBuckets(daysPre, daysPost, interType, pre, post, preNames, postNames, allPre, allPost);
                }
                dataFilePanel.setVisible(true);
            }
        }
        //Action for "AllPre" check
        else if (e.getSource() == allPreCheck) {
            nameList.add("ALLPRE");
            allPre[0] = allPreCheck.isSelected();
            allPreCheck.setEnabled(false);
            allPreWDateCheck.setEnabled(true);
        }
        //Action for "AllPreWDate" check
        else if (e.getSource() == allPreWDateCheck) {
            if (nameList.contains("ALLPRE")) {
                nameList.remove("ALLPRE");
            }
            nameList.add("ALLPRE+DX");
            allPre[1] = allPreWDateCheck.isSelected();
            allPreWDateCheck.setEnabled(false);
        }
        //Action for "AllPost" check
        else if (e.getSource() == allPostCheck) {
            nameList.add("ALLPOST");
            allPost[0] = allPostCheck.isSelected();
            allPostCheck.setEnabled(false);
            allPostWDateCheck.setEnabled(true);
        }
        //Action for "AllPostWDate" check
        else if (e.getSource() == allPostWDateCheck) {
            if (nameList.contains("ALLPOST")) {
                nameList.remove("ALLPOST");
            }
            nameList.add("ALLPOST+DX");
            allPost[1] = allPostWDateCheck.isSelected();
            allPostWDateCheck.setEnabled(false);
        }
        //Action for "Back" button
        else if (e.getSource() == backButton) {
            //If we're on the global panel, hide that one and then revert back to 1
            if (globalPanel.isVisible()) {
                globalPanel.setVisible(false);
                globalNamePanel.setVisible(false);
                allPrePanel.setVisible(false);
                allPostPanel.setVisible(false);
                backPanel.setVisible(false);
                optPanel1.setVisible(true);
            }
            //If option panel 2 is up, hide it and go back to 1
            else if (optPanel2.isVisible()) {
                optPanel2.setVisible(false);
                backPanel.setVisible(false);
                optPanel1.setVisible(true);
            }
        }
    }
    private boolean updateIntervalArray(int[] intArr, String[] nameArr, int numIntervals, JButton button, JLabel label,
                                        JTextField numField, JTextField nameField) {
        String text = label.getText();
        String num = numField.getText();
        String name = nameField.getText();
        int tmp = Integer.parseInt(text.substring(text.length()-3, text.length()-2));
        intArr[tmp-1] = Integer.parseInt(num);
        if (name.equals("")) {
            if (nameField == preNameField) {
                name = "PRE_" + tmp;
            } else {
                name = "POST_" + tmp;
            }
        }
        nameArr[tmp-1] = name;
        nameList.add(name);
        if (tmp == numIntervals) {
            numField.setEditable(false);
            nameField.setEditable(false);
            button.setVisible(false);
            return true;
        } else {
            numField.setText("");
            nameField.setText("");
            if (label == enterPreLabel) {
                label.setText("Enter PRE interval length #" + (tmp+1) + ": ");
            } else {
                label.setText("Enter POST interval length #" + (tmp+1) + ": ");
            }
            return false;
        }
    }
    private void uniformLengths(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = interLength;
        }
    }
    private void uniformNames(String[] arr, String prefix) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = prefix + (i+1);
            nameList.add(prefix + (i+1));
        }
    }
    private static boolean dateIsInvalid(String date) {
        return ((!date.equals("null") && date.length() != 10) || date.contains("X"));
    }
    private static boolean isInteger(String cur) {
        try {
            Integer.parseInt(cur);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    static Calendar stringToCal(String date) {
        Calendar tmp;
        if (date.equals("null")) {
            tmp = null;
        } else {
            String[] dateSplt = date.split("/");
            tmp = Calendar.getInstance();
            tmp.clear();
            tmp.set(Integer.parseInt(dateSplt[2]), Integer.parseInt(dateSplt[0])-1, Integer.parseInt(dateSplt[1]));
        }
        return tmp;
    }
}