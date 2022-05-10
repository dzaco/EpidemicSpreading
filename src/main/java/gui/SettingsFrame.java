package gui;

import people.AppSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class SettingsFrame extends JFrame implements ActionListener {

    private ParamForm paramForm;
    public SettingsFrame()
    {
        setTitle("AppSettings Form");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        paramForm = new ParamForm();
        paramForm.setRunAction(this);

        getContentPane()
                .add(paramForm.panel);
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var settings = AppSettings.get_instance();

        var peopleCount = Integer.parseInt(Objects.requireNonNull(this.paramForm.peopleCountBox.getSelectedItem()).toString());
        var startInfectionDuration = Integer.parseInt(Objects.requireNonNull(this.paramForm.startInfectionDurationBox.getSelectedItem()).toString());
        var infectionDuration = Integer.parseInt(Objects.requireNonNull(this.paramForm.infectionDurationBox.getSelectedItem()).toString());
        var recoveryDuration = Integer.parseInt(Objects.requireNonNull(this.paramForm.recoveryDurationBox.getSelectedItem()).toString());

        settings.Params.n = peopleCount;
        settings.Params.dstart = startInfectionDuration;
        settings.Params.dinfectious = infectionDuration;
        settings.Params.drecovered = recoveryDuration;

        this.setVisible(false);
    }
}
