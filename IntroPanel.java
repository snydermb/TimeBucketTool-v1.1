package MRBS.VED;

import com.opencsv.CSVReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.util.*;

class IntroPanel extends JPanel implements ActionListener {
    private JLabel idFileLabel;
    private JTextField idFileField;
    private JButton idFileButton;
    private HashMap<String, IDInstance> idMap;

    IntroPanel() {
        idFileLabel = new JLabel("Enter ID file location: ");
        idFileField = new JTextField(30);
        idFileButton = new JButton("Collect");
        idFileButton.addActionListener(this);
        this.add(idFileLabel);
        this.add(idFileField);
        this.add(idFileButton);
        idMap = new HashMap<>();
    }

    HashMap<String, IDInstance> getIdMap() {
        return idMap;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Action to read in ID file
        if (e.getSource() == idFileButton) {
            String fileName = idFileField.getText();
            CSVReader reader;
            //read in file if possible and create IDInstances in our map for all IDs within file
            try {
                reader = new CSVReader(new FileReader(fileName));
                reader.readNext();
                String[] line;
                int count = 0;
                while ((line = reader.readNext()) != null) {
                    idMap.put(line[0].trim(), new IDInstance(line[0].trim(), line[1].trim()));
                    count++;
                }
                idFileButton.setVisible(false);
                idFileField.setText("Collected " + count + " IDs.");
                idFileField.setEditable(false);
                idFileLabel.setVisible(false);
                JPanel panel = (JPanel)this.getParent();
                TimeBucketTool mTool = (TimeBucketTool) panel.getParent().getParent().getParent();
                mTool.afterIntro();
            } catch (Exception i) {
                idFileField.setText("File not found. Try again");
            }
        }
    }
}
