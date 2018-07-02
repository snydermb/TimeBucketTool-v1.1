package MRBS.VED;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.HashMap;

final class TimeBucketTool extends JFrame implements ActionListener {

    private JLabel globalPromptLabel;
    private JLabel preDaysLabel, postDaysLabel, intervalLengthLabel, interBeforeLabel, interAfterLabel, interTypeLabel;
    private JLabel enterPreLabel, enterPostLabel, prePostNameLabel;
    private JLabel allPreLabel, allPostLabel, allPreWDateLabel, allPostWDateLabel;
    private JTextField globalRangeA, globalRangeB;
    private JTextField preDaysField, postDaysField, interLengthField, interBeforeField, interAfterField;
    private JTextField enterPreField, enterPostField, preNameField, postNameField;
    private JButton globalButton, perIDButton;
    private JButton nextButton, doneButton, backButton;
    private JButton preDaysButton, postDaysButton, interLengthButton, interBeforeButton, interAfterButton;
    private JButton uniformButton, manualButton;
    private JButton daysButton, weeksButton, monthsButton;
    private JButton enterPreButton, enterPostButton;
    private JCheckBox allPreCheck, allPostCheck, allPreWDateCheck, allPostWDateCheck;
    private JPanel backPanel, globalPanel;
    private JPanel optPanel1, optPanel2, optPanel3, optPanel4, optPanel5, optPanel6, optTypePanel;
    private JPanel optPanel8, optPanel9;
    private JPanel optPanel10, optPanel11;

    private IntroPanel introPanel;
    private DataFilePanel dataFilePanel;
    private OutPanel outPanel;

    private HashMap<String, IDInstance> idMap;
    private int daysPre, daysPost, interType, interLength, numPreIntervals, numPostIntervals, globalBuckets;
    private int[] pre, post;
    private String[] preNames, postNames;
    private boolean[] allPre, allPost;

    TimeBucketTool() {
        //Main collection for storing IDs
        idMap = new HashMap<>();
        //Set basics
        this.setTitle("Supertool v1.0");
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

        //Set option panel 2
        optPanel2 = new JPanel();
        uniformButton = new JButton("Uniformly set?");
        uniformButton.addActionListener(this);
        manualButton = new JButton("Manually set?");
        manualButton.addActionListener(this);
        optPanel2.add(uniformButton);
        optPanel2.add(manualButton);
        optPanel2.setVisible(false);

        //Set interval type panel
        optTypePanel = new JPanel();
        interTypeLabel = new JLabel("Intervals in... ");
        daysButton = new JButton("Days");
        daysButton.addActionListener(this);
        weeksButton = new JButton("Weeks");
        weeksButton.addActionListener(this);
        monthsButton = new JButton("Months");
        monthsButton.addActionListener(this);
        optTypePanel.add(interTypeLabel);
        optTypePanel.add(daysButton);
        optTypePanel.add(weeksButton);
        optTypePanel.add(monthsButton);
        optTypePanel.setVisible(false);

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
        preDaysLabel = new JLabel("Days PRE to begin intervals: ");
        postDaysLabel = new JLabel("Days POST to begin intervals: ");
        intervalLengthLabel = new JLabel("Length of each interval in specified amount: ");
        interBeforeLabel = new JLabel("# of intervals pre: ");
        interAfterLabel = new JLabel("# of intervals post: ");
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
        prePostNameLabel = new JLabel("Name?");
        preNameField = new JTextField(10);
        postNameField = new JTextField(10);
        optPanel8.add(enterPreLabel);
        optPanel8.add(enterPreField);
        optPanel8.add(prePostNameLabel);
        optPanel8.add(preNameField);
        optPanel8.add(enterPreButton);
        optPanel9.add(enterPostLabel);
        optPanel9.add(enterPostField);
        optPanel9.add(prePostNameLabel);
        optPanel9.add(postNameField);
        optPanel9.add(enterPostButton);
        optPanel8.setVisible(false);
        optPanel9.setVisible(false);

        //Set option panel 10/11 (option for all pre/all post buckets)
        optPanel10 = new JPanel();
        optPanel11 = new JPanel();
        allPreLabel = new JLabel("All pre bucket?");
        allPreWDateLabel = new JLabel("Include KeyDate?");
        allPostLabel = new JLabel("All post bucket?");
        allPostWDateLabel = new JLabel("Include KeyDate?");
        allPreCheck = new JCheckBox();
        allPreCheck.addActionListener(this);
        allPreWDateCheck = new JCheckBox();
        allPreWDateCheck.addActionListener(this);
        allPostCheck = new JCheckBox();
        allPostCheck.addActionListener(this);
        allPostWDateCheck = new JCheckBox();
        allPostWDateCheck.addActionListener(this);
        optPanel10.add(allPreLabel);
        optPanel10.add(allPreCheck);
        optPanel10.add(allPreWDateLabel);
        optPanel10.add(allPreWDateCheck);
        optPanel11.add(allPostLabel);
        optPanel11.add(allPostCheck);
        optPanel11.add(allPostWDateLabel);
        optPanel11.add(allPostWDateCheck);
        optPanel10.setVisible(false);
        optPanel11.setVisible(false);

        //Combine panels in center
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.PAGE_AXIS));
        fieldPanel.add(optPanel1);
        fieldPanel.add(globalPanel);
        fieldPanel.add(optPanel2);
        fieldPanel.add(optPanel10);
        fieldPanel.add(optPanel11);
        fieldPanel.add(optPanel3);
        fieldPanel.add(optTypePanel);
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
        dataFilePanel.setIdMap(introPanel.getIdMap());
    }
    void afterDataPanel() {
        this.getContentPane().add(BorderLayout.SOUTH, outPanel);
        outPanel.setGlobalBuckets(globalBuckets);
        outPanel.setPost(post);
        outPanel.setPre(pre);
        outPanel.setIdMap(idMap);
        outPanel.setVisible(true);
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
            if ((!dateA.equals("null") && dateA.length() != 10) || dateA.contains("X")) {
                globalRangeA.setText("Reformat date");
                globalRangeB.setText("");
            } else if ((!dateB.equals("null") && dateB.length() != 10) || dateB.contains("X")) {
                globalRangeA.setText("");
                globalRangeB.setText("Reformat date");
            } else {
                //If both dates are valid, create a new time bucket with the range and add to the global list
                Calendar calA;
                Calendar calB;
                if (dateA.equals("null")) {
                    calA = null;
                } else {
                    calA = stringToCal(dateA);
                }
                if (dateB.equals("null")) {
                    calB = null;
                } else {
                    calB = stringToCal(dateB);
                }
                for (IDInstance tmp : idMap.values()) {
                    tmp.createGlobal(calA, calB);
                }
                globalBuckets++;
                globalRangeA.setText("");
                globalRangeB.setText("");
                //If user hits "next", prompt for next bucket range
                if (e.getSource() == nextButton) {
                    String text = globalPromptLabel.getText();
                    int tmp = Integer.parseInt(text.substring(text.length()-3, text.length()-2));
                    globalPromptLabel.setText("Enter time bucket #" + (tmp+1) + ": ");
                } else {
                    pre = new int[0];
                    post = new int[0];
                    globalPanel.setVisible(false);
                    dataFilePanel.setVisible(true);
                }
            }
        }
        //Action for "Per ID" button
        else if (e.getSource() == perIDButton) {
            optPanel1.setVisible(false);
            optPanel2.setVisible(true);
            backPanel.setVisible(true);
            allPre = new boolean[2];
            allPost = new boolean[2];
        }
        //PER ID OPTION CHOSEN:
        //Action for "Uniformly Set?" button
        else if (e.getSource() == uniformButton) {
            optPanel2.setVisible(false);
            backPanel.setVisible(false);
            optPanel10.setVisible(true);
            optPanel11.setVisible(true);
            optPanel3.setVisible(true);
            optTypePanel.setVisible(true);
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
        //Action for "Days" button
        else if (e.getSource() == daysButton) {
            interType = Calendar.DATE;
            daysButton.setSelected(true);
            weeksButton.setVisible(false);
            monthsButton.setVisible(false);
        }
        //Action for "Weeks" button
        else if (e.getSource() == weeksButton) {
            interType = Calendar.WEEK_OF_YEAR;
            weeksButton.setSelected(true);
            daysButton.setVisible(false);
            monthsButton.setVisible(false);
        }
        //Action for "Months" button
        else if (e.getSource() == monthsButton) {
            interType = Calendar.MONTH;
            monthsButton.setSelected(true);
            daysButton.setVisible(false);
            weeksButton.setVisible(false);
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
        //Action for "Post Intervals - Done" button
        else if (e.getSource() == interAfterButton) {
            String text = interAfterField.getText();
            if (isInteger(text)) {
                numPostIntervals = Integer.parseInt(text);
                post = new int[numPostIntervals];
                postNames = new String[numPostIntervals];
                if (optPanel4.isVisible()) {
                    interAfterButton.setVisible(false);
                    interAfterField.setEditable(false);
                    dataFilePanel.setVisible(true);
                    backPanel.setVisible(false);
                    for (int i = 0; i < pre.length; i++) {
                        pre[i] = interLength;
                    }
                    for (int i = 0; i < post.length; i++) {
                        post[i] = interLength;
                    }
                    for (IDInstance tmp : idMap.values()) {
                        tmp.createBuckets(daysPre, daysPost, interType, pre, post, preNames, postNames, allPre, allPost);
                    }
                } else {
                    optPanel5.setVisible(false);
                    optPanel6.setVisible(false);
                    backPanel.setVisible(false);
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
            optPanel10.setVisible(true);
            optPanel11.setVisible(true);
            optTypePanel.setVisible(true);
            optPanel5.setVisible(true);
            optPanel6.setVisible(true);
        }
        //Action for "Enter pre intervals" button
        else if (e.getSource() == enterPreButton) {
            updateIntervalArray(pre, numPreIntervals, enterPreButton, enterPreLabel, enterPreField);
        }
        //Action for "Enter post intervals" button
        else if (e.getSource() == enterPostButton) {
            if (updateIntervalArray(post, numPostIntervals, enterPostButton, enterPostLabel, enterPostField)) {
                for (IDInstance tmp : idMap.values()) {
                    tmp.createBuckets(daysPre, daysPost, interType, pre, post, preNames, postNames, allPre, allPost);
                }
                dataFilePanel.setVisible(true);
            }
        }
        //Action for "AllPre" check
        else if (e.getSource() == allPreCheck) {
            allPre[0] = allPreCheck.isSelected();
        }
        //Action for "AllPreWDate" check
        else if (e.getSource() == allPreWDateCheck) {
            allPre[1] = allPreWDateCheck.isSelected();
        }
        //Action for "AllPost" check
        else if (e.getSource() == allPostCheck) {
            allPost[0] = allPostCheck.isSelected();
        }
        //Action for "AllPostWDate" check
        else if (e.getSource() == allPostWDateCheck) {
            allPost[1] = allPostWDateCheck.isSelected();
        }
        //Action for "Back" button
        else if (e.getSource() == backButton) {
            //If we're on the global panel, hide that one and then revert back to 1
            if (globalPanel.isVisible()) {
                globalPanel.setVisible(false);
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
    private boolean updateIntervalArray(int[] arr, int numIntervals, JButton button, JLabel label, JTextField field) {
        String text = label.getText();
        String num = field.getText();
        int tmp = Integer.parseInt(text.substring(text.length()-3, text.length()-2));
        arr[tmp-1] = Integer.parseInt(num);
        if (tmp == numIntervals) {
            field.setEditable(false);
            button.setVisible(false);
            return true;
        } else {
            field.setText("");
            if (label == enterPreLabel) {
                label.setText("Enter PRE interval length #" + (tmp+1) + ": ");
            } else {
                label.setText("Enter POST interval length #" + (tmp+1) + ": ");
            }
            return false;
        }
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