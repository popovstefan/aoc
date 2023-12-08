package org.stefanpopov.aoc23.day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IfYouGiveASeedAFertilizer {

    public static void main(String[] args) throws IOException {
        String filename = "C:\\Users\\popov\\IdeaProjects\\aoc\\src\\main\\java\\org\\stefanpopov\\aoc23\\day4\\puzzleinput.txt";
        long task1Result = task(filename);
        System.out.printf("Result of task 2 is [%d]%n", task1Result);
    }

    public static long task(String filename) throws IOException {
        long result = Long.MIN_VALUE;
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            result++;
        }
        return result;
    }
}
