package com.company;

import com.company.Algorithms.Algorithm;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String start;
        String ziel;

        System.out.print("Start: ");
        start = in.nextLine();

        System.out.print("Ziel: ");
        ziel = in.nextLine();

        Algorithm algorithm = new Algorithm(start, ziel);
        algorithm.start();
        if (algorithm.getShortestPath() == null) {
            System.out.println("Sie haben \"Start\" und \"Ziel\" falsch eingegeben.");
        }
        else {
            System.out.println(algorithm.getShortestPath().show());
        }
    }
}
