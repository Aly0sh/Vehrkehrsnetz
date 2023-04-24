package com.company.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Der Klasse für das Abfangen aller Ereignisse von MainFrame
public class Controller implements ActionListener {
    private Model model;

    public Controller (MainFrame mainFrame) {
        model = new Model(mainFrame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.actionListener(e.getActionCommand()); // Übermittlung aller erfassten Aktionen an das Model
    }
}
