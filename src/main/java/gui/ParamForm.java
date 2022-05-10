package gui;

import graphs.GraphFactory;
import people.AppSettings;
import people.Person;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ParamForm {

    public JPanel panel;
    public ParamForm() {
        panel = new JPanel();
        panel.setLayout( new GridLayout(5,2) );
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        peopleCountBox = new JComboBox(peopleCountOption);
        startInfectionDurationBox = new JComboBox(startInfectionDurationOption);
        infectionDurationBox = new JComboBox(infectionDurationOption);
        recoveryDurationBox = new JComboBox(recoveryDurationOption);

        panel.add(peopleCountLabel);
        panel.add(peopleCountBox);

        panel.add(startInfectionDurationLabel);
        panel.add(startInfectionDurationBox);

        panel.add(infectionDurationLabel);
        panel.add(infectionDurationBox);

        panel.add(recoveryDurationLabel);
        panel.add(recoveryDurationBox);

        panel.add(runBtn);
    }

    private JLabel peopleCountLabel = new JLabel("People count");
    public JComboBox peopleCountBox;
    private String[] peopleCountOption = {
            "100", "200", "300", "500"
    };

    private JLabel startInfectionDurationLabel = new JLabel("Duration before infection begins");
    public JComboBox startInfectionDurationBox;
    private String[] startInfectionDurationOption = {
            "0","5","10","20"
    };

    private JLabel infectionDurationLabel = new JLabel("Duration of infection");
    public JComboBox infectionDurationBox;
    private String[] infectionDurationOption = {
            "10","20","30","50","100"
    };

    private JLabel recoveryDurationLabel = new JLabel("Duration of recovery");
    public JComboBox recoveryDurationBox;
    private String[] recoveryDurationOption = {
            "20","50","100","200","500"
    };

    private JButton runBtn = new JButton("Run");

    public void setRunAction(ActionListener listener) {
        this.runBtn.addActionListener(listener);
    }
}
