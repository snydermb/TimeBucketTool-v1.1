package MRBS.VED;

import com.opencsv.CSVReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

class DataFilePanel extends JPanel implements ActionListener {
    private JTextField dataFileField;
    private JButton dataFileButton;
    private HashMap<String, IDInstance> idMap;

    DataFilePanel() {
        JLabel dataFileLabel = new JLabel("Enter data location: ");
        dataFileField = new JTextField(30);
        dataFileButton = new JButton("Collect");
        dataFileButton.addActionListener(this);
        this.add(dataFileLabel);
        this.add(dataFileField);
        this.add(dataFileButton);
    }
    void setIdMap(HashMap<String, IDInstance> map) {
        idMap = map;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dataFileButton) {
            String fileName = dataFileField.getText();
            CSVReader reader;
            try {
                reader = new CSVReader(new FileReader(fileName));
                reader.readNext();
                String[] line;
                while ((line = reader.readNext()) != null) {
                    String id = line[0].trim();
                    if (idMap.containsKey(id)) {
                        idMap.get(id).updateBuckets(line[1].trim(), line[2].trim());
                    }
                }
                this.setVisible(false);
                JPanel panel = (JPanel)this.getParent();
                TimeBucketTool mTool = (TimeBucketTool) panel.getParent().getParent().getParent();
                mTool.afterDataPanel();
            } catch (IOException i) {
                dataFileField.setText("File not found. Try again");
            }
        }
    }
}
