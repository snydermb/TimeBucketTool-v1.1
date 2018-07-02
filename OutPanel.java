package MRBS.VED;

import com.opencsv.CSVWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

class OutPanel extends JPanel implements ActionListener {
    private JTextField outFileField;
    private JButton outFileButton;

    private int globalBuckets;
    private int[] pre, post;
    private HashMap<String, IDInstance> idMap;

    OutPanel() {
        JLabel outFileLabel = new JLabel("Enter output file name: ");
        outFileField = new JTextField(30);
        outFileButton = new JButton("Done!");
        outFileButton.addActionListener(this);
        this.add(outFileLabel);
        this.add(outFileField);
        this.add(outFileButton);
    }

    void setGlobalBuckets(int globalBuckets) {
        this.globalBuckets = globalBuckets;
    }
    void setPost(int[] post) {
        this.post = post;
    }
    void setPre(int[] pre) {
        this.pre = pre;
    }

    void setIdMap(HashMap<String, IDInstance> idMap) {
        this.idMap = idMap;
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
                    String[] outline;
                    if (globalBuckets > 0) {
                        outline = new String[(globalBuckets*4) + 1];
                        outline[0] = "";
                        for (int i = 1; i < outline.length; i=i+4) {
                            int num = (i/4)+1;
                            outline[i] = "GLOBAL" + num + "_N";
                            outline[i+1] = "GLOBAL" + num + "_min";
                            outline[i+2] = "GLOBAL" + num + "_max";
                            outline[i+3] = "GLOBAL" + num + "_mean";
                        }
                        writer.writeNext(outline);
                    } else {
                        outline = new String[pre.length*4 + post.length*4 + 5];
                        outline[0] = "";
                        outline[1] = "DATE_N";
                        outline[2] = "DATE_min";
                        outline[3] = "DATE_max";
                        outline[4] = "DATE_mean";
                        int pos = 5;
                        for (int i = 0; i < pre.length; i++) {
                            int num = i+1;
                            outline[pos++] = "PRE" + num + "_N";
                            outline[pos++] = "PRE" + num + "_min";
                            outline[pos++] = "PRE" + num + "_max";
                            outline[pos++] = "PRE" + num + "_mean";
                        }
                        for (int i = 0; i < post.length; i++) {
                            int num = i+1;
                            outline[pos++] = "POST" + num + "_N";
                            outline[pos++] = "POST" + num + "_min";
                            outline[pos++] = "POST" + num + "_max";
                            outline[pos++] = "POST" + num + "_mean";
                        }
                        writer.writeNext(outline);
                    }
                    for (IDInstance tmp : idMap.values()) {
                        tmp.updateOutline(outline);
                        writer.writeNext(outline);
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
