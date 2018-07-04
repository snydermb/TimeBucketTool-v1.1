package MRBS.VED;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

class InterTypePanel extends JPanel implements ActionListener {
    private JButton daysButton, weeksButton, monthsButton;

    InterTypePanel() {
        JLabel interTypeLabel = new JLabel("Intervals in... ");
        daysButton = new JButton("Days");
        daysButton.addActionListener(this);
        weeksButton = new JButton("Weeks");
        weeksButton.addActionListener(this);
        monthsButton = new JButton("Months");
        monthsButton.addActionListener(this);
        this.add(interTypeLabel);
        this.add(daysButton);
        this.add(weeksButton);
        this.add(monthsButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int interType = 0;
        if (e.getSource() == daysButton) {
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
        JPanel panel = (JPanel)this.getParent();
        TimeBucketTool mTool = (TimeBucketTool) panel.getParent().getParent().getParent();
        mTool.afterInterType(interType);
    }
}
