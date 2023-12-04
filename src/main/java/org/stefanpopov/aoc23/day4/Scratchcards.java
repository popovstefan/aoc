package org.stefanpopov.aoc23.day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Scratchcards {
    public static void main(String[] args) throws IOException {
        String filename = "C:\\Users\\popov\\IdeaProjects\\aoc\\src\\main\\java\\org\\stefanpopov\\aoc23\\day4\\puzzleinput.txt";
        int task1Result = task1(filename);
        System.out.printf("Result of task 2 is [%d]%n", task1Result);
    }

    private static int task1(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        int sum = 0;
        while ((line = reader.readLine()) != null) {
            String[] lineParts = line.split(":");
            String[] numberParts = lineParts[1].split("\\|");
            Set<Integer> theNumbersYouHave = new HashSet<>();
            String[] numbersYouHave = numberParts[0].split(" ");
            for (String numberYouHave : numbersYouHave) {
                if (numberYouHave.trim().length() >= 1)
                    theNumbersYouHave.add(Integer.parseInt(numberYouHave.trim()));
            }
            Set<Integer> theWinningNumbers = new HashSet<>();
            String[] winningNumbers = numberParts[1].split(" ");
            for (String winningNumber : winningNumbers) {
                if (winningNumber.trim().length() >= 1)
                    theWinningNumbers.add(Integer.parseInt(winningNumber.trim()));
            }
            theNumbersYouHave.retainAll(theWinningNumbers);
            System.out.println(theNumbersYouHave);
            sum += Math.pow(2, theNumbersYouHave.size() - 1);
        }
        return sum;
    }
}
