package com.company.GUI;

import com.company.Algorithms.Algorithm;

// Klasse zur Implementierung aller Befehle
public class Model {
    private MainFrame mainFrame;
    private Algorithm algorithm;

    public Model(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void actionListener(String action) {
        // Switch-Anweisung zur Bestimmung des übergebenen Befehls
        switch (action) {
            case "submit":
                algorithm = new Algorithm(mainFrame.getStartStation(), mainFrame.getEndStation()); // Initialisierung der Breitensuche
                algorithm.start();
                if (mainFrame.getStartStation().isEmpty() || mainFrame.getEndStation().isEmpty()) {
                    mainFrame.getListModel().addElement("Sie haben nicht \"Start\" und \"Ziel\" eingegeben."); // Fehlermeldung bei leerem Eingabefeld
                }
                else if (algorithm.getShortestPath() == null) {
                    mainFrame.getListModel().addElement("Sie haben \"Start\" und \"Ziel\" falsch eingegeben."); // Anzeige einer Fehlermeldung, wenn der Pfad nicht gefunden werden konnte
                }
                else {
                    mainFrame.getListModel().addElement("Start: " +  mainFrame.getStartStation());
                    mainFrame.getListModel().addElement("Ziel: " +  mainFrame.getEndStation());
                    mainFrame.getListModel().addElement("");
                    for (String i : algorithm.getShortestPath().getResultAsList()) {
                        mainFrame.getListModel().addElement(i);
                    }
                    mainFrame.reset();
                    mainFrame.getGraphVisualization().setPathsForV(algorithm.getPathsForV()); // Übertragung aller durchlaufener Wege zur Visualisierung des Graphen
                    mainFrame.getGraphVisualization().setMainEdges(algorithm.getShortestPath().getStations()); // Übermittlung des kürzesten Pfads zur Visualisierung des Graphen

                    // Starten von Animationen zur Visualisierung der Graphkanten
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mainFrame.getGraphVisualization().drawEdges();
                        }
                    }).start();
                }
                mainFrame.getListModel().addElement("");
                mainFrame.getListModel().addElement("____________________________________");
                mainFrame.getListModel().addElement("");
                break;
            case "reset":
                mainFrame.reset();
                break;
        }
    }
}
