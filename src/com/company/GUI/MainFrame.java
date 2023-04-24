package com.company.GUI;

import javax.swing.*;
import java.awt.*;

// Viewer für die Hauptseite
public class MainFrame {

    private JFrame frame; // Hauptfenster
    private JPanel form; // Panel für alle Eingabe- und Ausgabefelder
    private JPanel tNetwork; // Panel für die Graph Visualisation
    private JPanel buttons; // Panel für Schaltflächen
    private JLabel startStation; // Das Label des Textfeldes für die Startstation
    private JLabel endStation; // Das Label des Textfeldes für die Zielstation
    private JLabel result; // Das Label für die Ausgabe
    private JTextField startStationText; // "TextField" für die Eingabe der Startstation
    private JTextField endStationText; // "TextField" für die Eingabe der Zielstation
    private JButton submit; // "Absenden"-Taste
    private JButton reset; // "Zurücksetzen"-Taste
    private DefaultListModel<String> listModel; // ListModel von der Ausgabe
    private JList<String> resultList; // JList von der Ausgabe
    private JScrollPane resultBox; // ScrollPane von der Ausgabe
    private GraphVisualization graphVisualization; // Visualisierung des Graphen

    public MainFrame() {
        frame = new JFrame("Der kürzeste Weg finden");

        frame.setSize(900,400);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        prepare();

        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Die Initialisierung aller GUI-Komponenten
    private void prepare() {
        Controller controller = new Controller(this); // Übertragung MainFrame zur Verfolgung aller Aktionen
        form = new JPanel();
        form.setBorder(BorderFactory.createTitledBorder("Form"));
        form.setLayout(null);
        form.setBounds(5, 0, 250, 360);

        startStation = new JLabel("Start:");
        startStation.setBounds(10, 20, 200, 20);
        form.add(startStation);

        startStationText = new JTextField();
        startStationText.setBounds(10, 40, 230, 24);
        form.add(startStationText);

        endStation = new JLabel("Ziel:");
        endStation.setBounds(10, 70, 200, 20);
        form.add(endStation);

        endStationText = new JTextField();
        endStationText.setBounds(10, 90, 230, 24);
        form.add(endStationText);

        buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2, 5, 10));

        submit = new JButton("Absenden");
        submit.addActionListener(controller);
        submit.setActionCommand("submit");
        reset = new JButton("Zurücksetzen");
        reset.addActionListener(controller);
        reset.setActionCommand("reset");
        buttons.add(submit);
        buttons.add(reset);
        buttons.setBounds(10, 120, 230, 24);
        form.add(buttons);

        result = new JLabel("Ergebnis:");
        result.setBounds(10, 150, 200, 20);
        form.add(result);

        listModel = new DefaultListModel<>();
        resultList = new JList<>(listModel);
        resultBox = new JScrollPane(resultList);
        resultBox.setBounds(15, 170, 230, 180);
        frame.add(resultBox);

        frame.getContentPane().add(form);

        tNetwork = new JPanel();
        graphVisualization = new GraphVisualization(this);
        tNetwork.setBorder(BorderFactory.createTitledBorder("Verkehrsnetz"));
        tNetwork.setBounds(260, 0, 620, 360);
        tNetwork.add(graphVisualization.getVv());
        frame.getContentPane().add(tNetwork);
    }

    public DefaultListModel<String> getListModel() {
        return listModel;
    }

    public String getStartStation() {
        return startStationText.getText();
    }

    public String getEndStation() {
        return endStationText.getText();
    }

    public void setStartStation(String station) {
        startStationText.setText(station);
    }

    public void setEndStation(String station) {
        endStationText.setText(station);
    }

    // Der Methode zum Löschen des Textfelds für Start- und Zielstationen
    public void reset() {
        startStationText.setText("");
        endStationText.setText("");
    }

    public GraphVisualization getGraphVisualization() {
        return graphVisualization;
    }
}
