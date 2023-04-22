package com.company;

import com.company.Algorithms.Algorithm;

public class Main {

    public static void main(String[] args) {

        Algorithm algorithm = new Algorithm("Paxtakor - Alisher Navoiy", "Ming Urik - Oybek");
        algorithm.start();
        System.out.println(algorithm.getShortestPath().show());


    }
}
