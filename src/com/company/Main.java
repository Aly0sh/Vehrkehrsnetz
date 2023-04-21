package com.company;

import com.company.Algorithms.Algorithm;

public class Main {

    public static void main(String[] args) {

        Algorithm algorithm = new Algorithm("Toshkent", "Pushkin");
        algorithm.start();
        algorithm.getShortestPath().show();

    }
}
