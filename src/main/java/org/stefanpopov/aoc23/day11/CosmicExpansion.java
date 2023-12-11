package org.stefanpopov.aoc23.day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CosmicExpansion {

    public static void main(String[] args) throws IOException {
        String filename = "C:\\Users\\popov\\IdeaProjects\\aoc\\src\\main\\java\\org\\stefanpopov\\aoc23\\day11\\puzzleinputtest.txt";
        long task1Result = task1(filename);
        System.out.printf("Result of task is [%d]%n", task1Result);
    }

    public static long task1(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
        }
        return -1;
    }
}
