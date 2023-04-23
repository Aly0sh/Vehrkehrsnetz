package com.company.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private Model model;

    public Controller (MainFrame mainFrame) {
        model = new Model(mainFrame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.actionListener(e.getActionCommand());
    }
}
