package com.company;

import com.company.Algorithms.Algorithm;
import com.company.GUI.MainFrame;

public class Main {

    public static void main(String[] args) {

// выполняем алгоритм

        Algorithm algorithm = new Algorithm("Paxtakor - Alisher Navoiy", "Pushkin");

        algorithm.start();

        algorithm.getShortestPath();

        MainFrame mainFrame = new MainFrame();
    }
}
