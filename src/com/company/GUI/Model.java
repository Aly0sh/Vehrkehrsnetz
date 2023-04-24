package com.company.GUI;

import com.company.Algorithms.Algorithm;

public class Model {
    private MainFrame mainFrame;
    private Algorithm algorithm;

    public Model(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void actionListener(String action) {
        switch (action) {
            case "submit":
                algorithm = new Algorithm(mainFrame.getStartStation(), mainFrame.getEndStation());
                algorithm.start();
                if (mainFrame.getStartStation().isEmpty() || mainFrame.getEndStation().isEmpty()) {
                    mainFrame.getListModel().addElement("Sie haben nicht \"Start\" und \"Ziel\" eingegeben.");
                }
                else if (algorithm.getShortestPath() == null) {
                    mainFrame.getListModel().addElement("Sie haben \"Start\" und \"Ziel\" falsch eingegeben.");
                }
                else {
                    mainFrame.getListModel().addElement("Start: " +  mainFrame.getStartStation());
                    mainFrame.getListModel().addElement("Ziel: " +  mainFrame.getEndStation());
                    mainFrame.getListModel().addElement("");
                    for (String i : algorithm.getShortestPath().getResultAsList()) {
                        mainFrame.getListModel().addElement(i);
                    }
                    mainFrame.reset();
                    mainFrame.getGraphVisualization().setPathsForV(algorithm.getPathsForV());
                    mainFrame.getGraphVisualization().setMainEdges(algorithm.getShortestPath().getStations());
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
