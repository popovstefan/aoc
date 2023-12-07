package org.stefanpopov.aoc23.day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Card {
    private final int number;
    private final Set<Integer> numbersYouHave;
    private final Set<Integer> winningNumbers;

    public Card(int number, Set<Integer> numbersYouHave, Set<Integer> winningNumbers) {
        this.number = number;
        this.numbersYouHave = numbersYouHave;
        this.winningNumbers = winningNumbers;
    }

    public int getMatching() {
        Set<Integer> theNumbersYouHave = new HashSet<>(numbersYouHave);
        theNumbersYouHave.retainAll(winningNumbers);
        return theNumbersYouHave.size();
    }

    @Override
    public String toString() {
        return String.format("Card{number=%d,numbersYouHave=%s,winningNumbers=%s}", number, numbersYouHave, winningNumbers);
    }

    public Integer getNumber() {
        return number;
    }
}

public class Scratchcards {
    public static void main(String[] args) throws IOException {
        String filename = "C:\\Users\\popov\\IdeaProjects\\aoc\\src\\main\\java\\org\\stefanpopov\\aoc23\\day4\\puzzleinput.txt";
        int task1Result = task1(filename);
        System.out.printf("Result of task 2 is [%d]%n", task1Result);
    }

    private static int task1(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        TreeMap<Integer, Map.Entry<Card, Integer>> numberToCards = new TreeMap<>();
        while ((line = reader.readLine()) != null) {
            String[] lineParts = line.split(":");
            int cardNumber = Integer.parseInt(lineParts[0].split("\\s+")[1]);
            String[] numberParts = lineParts[1].split("\\|");
            Set<Integer> theNumbersYouHave = new HashSet<>();
            String[] numbersYouHave = numberParts[0].split("\\s+");
            for (String numberYouHave : numbersYouHave) {
                if (numberYouHave.trim().length() >= 1)
                    theNumbersYouHave.add(Integer.parseInt(numberYouHave.trim()));
            }
            Set<Integer> theWinningNumbers = new HashSet<>();
            String[] winningNumbers = numberParts[1].split("\\s+");
            for (String winningNumber : winningNumbers) {
                if (winningNumber.trim().length() >= 1)
                    theWinningNumbers.add(Integer.parseInt(winningNumber.trim()));
            }
            Card card = new Card(cardNumber, theNumbersYouHave, theWinningNumbers);
            numberToCards.putIfAbsent(cardNumber, new AbstractMap.SimpleEntry<>(card, 1));
        }

//        numberToCards.forEach((k,v)-> System.out.printf("%s %s%n", k, v));

        for (Map.Entry<Integer, Map.Entry<Card, Integer>> entry : numberToCards.entrySet()) {
            Map.Entry<Card, Integer> map = entry.getValue();
            System.out.printf("Card %s with value %d%n", map.getKey(), map.getValue());
            for (int i = 1; i <= map.getValue(); i++) {
                // for each card here
                int m = map.getKey().getMatching();
                System.out.printf("Matching %d%n", m);
                for (int j = 0; j < m; j++) {
                    Map.Entry<Card, Integer> oldEntry = numberToCards.get(map.getKey().getNumber() + j + 1);
                    if (oldEntry == null)
                        break;
                    oldEntry.setValue(oldEntry.getValue() + 1); // add a card
                    System.out.printf("Putting %s at %d%n", oldEntry, map.getKey().getNumber() + j + 1);
                    numberToCards.put(map.getKey().getNumber() + j + 1, oldEntry);
                }
            }
        }
        System.out.printf("Number to cards %s%n", numberToCards);
        return numberToCards
                .values()
                .stream()
                .map(Map.Entry::getValue)
                .mapToInt(it -> it)
                .sum();
    }
}
