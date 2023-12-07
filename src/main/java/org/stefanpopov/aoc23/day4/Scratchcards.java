package org.stefanpopov.aoc23.day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

class Card {
    private int number;
    private Set<Integer> numbersYouHave;
    private Set<Integer> winningNumbers;

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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Set<Integer> getNumbersYouHave() {
        return numbersYouHave;
    }

    public void setNumbersYouHave(Set<Integer> numbersYouHave) {
        this.numbersYouHave = numbersYouHave;
    }

    public Set<Integer> getWinningNumbers() {
        return winningNumbers;
    }

    public void setWinningNumbers(Set<Integer> winningNumbers) {
        this.winningNumbers = winningNumbers;
    }

    @Override
    public String toString() {
        return String.format("Card{number=%d,numbersYouHave=%s,winningNumbers=%s}", number, numbersYouHave, winningNumbers);
    }
}

public class Scratchcards {
    public static void main(String[] args) throws IOException {
        String filename = "C:\\Users\\popov\\IdeaProjects\\aoc\\src\\main\\java\\org\\stefanpopov\\aoc23\\day4\\puzzleinputtest.txt";
        int task1Result = task1(filename);
        System.out.printf("Result of task 2 is [%d]%n", task1Result);
    }

    private static int task1(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        Card[] cards = new Card[1000];
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
            cards[cardNumber] = card;
        }

        // process cards
        List<Card> cardList = new LinkedList<>();
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] != null) {
                int m = cards[i].getMatching();
                for (int j = i; j < Math.min(m + i, cards.length); j++) {
                    if (cards[j] != null) {
                        cardList.add(cards[j]);
                        System.out.printf("ADding carad %d%n", cards[j].getNumber());
                    }
                }
            }
        }
        System.out.printf("Card list sisze %d%n", cardList.size());
        // another processing
        List<Card> cardList1 = new LinkedList<>();
        for (int i = 0; i < cardList.size(); i++) {
            Card card = cardList.get(i);
            for (int j = card.getNumber(); j < cards.length; j++) {

            }
        }
        System.out.println(cardList.stream().filter(it -> it.getNumber() == 4).count());
        int s = 0;
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] != null)
                s++;
        }
        return cardList1.size() + cardList.size() + s;
    }
}
