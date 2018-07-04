package MRBS.VED;

import com.opencsv.CSVWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

class OutPanel extends JPanel implements ActionListener {
    private JTextField outFileField;
    private JButton outFileButton;

    private HashMap<String, IDInstance> idMap;
    private ArrayList<String> nameList;

    OutPanel() {
        JLabel outFileLabel = new JLabel("Enter output file name: ");
        outFileField = new JTextField(30);
        outFileButton = new JButton("Done!");
        outFileButton.addActionListener(this);
        this.add(outFileLabel);
        this.add(outFileField);
        this.add(outFileButton);
    }

    void setIdMap(HashMap<String, IDInstance> idMap) {
        this.idMap = idMap;
    }
    void setNameList(ArrayList<String> nameList) {
        this.nameList = nameList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Action for "Collect" data file button
        //Action for final "Done!" button
        if (e.getSource() == outFileButton) {
            if (outFileField.getText().length() < 1) {
                outFileField.setText("Please enter a file name.");
            } else {
                try {
                    String filename = outFileField.getText();
                    CSVWriter writer = new CSVWriter(new FileWriter(filename + ".txt"));
                    String[] outline = new String[(nameList.size()*4) + 1];
                    int pos = 0;
                    outline[pos++] = "";
                    for (int i = 0; i < nameList.size(); i++) {
                        String bucketName = nameList.get(i);
                        outline[pos++] = bucketName + "_N";
                        outline[pos++] = bucketName + "_min";
                        outline[pos++] = bucketName + "_max";
                        outline[pos++] = bucketName + "_mean";
                    }
                    writer.writeNext(outline);
                    for (IDInstance tmp : idMap.values()) {
                        writer.writeNext(tmp.returnOutline(nameList));
                    }
                    writer.close();
                    outFileField.setText("Processing complete!");
                    outFileField.setEditable(false);
                    outFileButton.setVisible(false);
                } catch (IOException i) {}
            }
        }
    }
}
