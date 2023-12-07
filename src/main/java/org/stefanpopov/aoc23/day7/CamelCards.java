package org.stefanpopov.aoc23.day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


class Hand implements Comparable<Hand> {
    private int id;
    private String hand;
    private int bid;

    public Hand(int id, String hand, int bid) {
        this.id = id;
        this.hand = hand;
        this.bid = bid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hand hand = (Hand) o;
        return id == hand.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getBid() {
        return bid;
    }

    private int oneType(String myHand) {
        HashMap<Character, Integer> charToCount = new HashMap<>();
        for (int i = 0; i < myHand.length(); i++) {
            charToCount.putIfAbsent(myHand.charAt(i), 0);
            charToCount.computeIfPresent(myHand.charAt(i), (k, v) -> v + 1);
        }
        if (charToCount.size() == 1)
            return 7; // five of a kind
        else if (charToCount.size() == 2) {
            if (charToCount.values().stream().mapToInt(it -> it).max().getAsInt() == 4)
                return 6; // four of a kind
            else
                return 5; // full house
        } else if (charToCount.size() == 3) {
            if (charToCount.values().stream().mapToInt(it -> it).max().getAsInt() == 3)
                return 4; // three of a kind
            else
                return 3; // two pair
        } else if (charToCount.size() == 4)
            return 2; // one pair
        else if (charToCount.size() == 5)
            return 1; // high card
        return 0;
    }

    public Set<String> combinate(String word) {
        // Will hold all the combinations of word
        Set<String> combinations = new HashSet<>();

        // The word is a combination (could be ignored if empty, though)
        combinations.add(word);

        // Iterate on each word's characters
        for (int i = 0; i < word.toCharArray().length; i++) {
            char character = word.toCharArray()[i];

            // If the character should be replaced...
            if (character == 'J') {

                // ... we split the word in two at the character's position & pay attention not be exceed word's length
                String firstWordPart = word.substring(0, i);
                boolean isWordEnd = i + 1 >= word.length();
                String secondWordPart = isWordEnd ? "" : word.substring(i + 1);

                // Here is the trick: we compute all combinations of the second word part...
                Set<String> allCombinationsOfSecondPart = combinate(secondWordPart);

                // ... and we append each of them to the first word part one by one
                String s = "23456789TQKA";
                for (String string : allCombinationsOfSecondPart) {
                    for (int j = 0; j < s.length(); j++) {
                        String combination = firstWordPart + s.charAt(j) + string;
                        combinations.add(combination);
                    }
                }
            }
        }
        return combinations;
    }

    public int type() {
        int count = 0;
        for (int i = 0; i < hand.length(); i++)
            if (hand.charAt(i) == 'J')
                count++;
        if (count == 0)
            return oneType(hand);
        else
            return combinate(hand)
                    .stream()
                    .mapToInt(this::oneType)
                    .max()
                    .getAsInt();
    }

    private int getIndex(char c) {
        String s = "J23456789TQKA";
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) == c)
                return i;
        return -1;
    }


    @Override
    public int compareTo(Hand that) {
        if (this.type() == that.type()) {
            for (int i = 0; i < this.hand.length(); i++) {
                int diff = Integer.compare(this.getIndex(this.hand.charAt(i)), that.getIndex(that.hand.charAt(i)));
                if (diff != 0)
                    return diff;
            }
            return 0;
        } else
            return Integer.compare(this.type(), that.type());
    }

    @Override
    public String toString() {
        return "Hand{" +
                "hand='" + hand + '\'' +
                ", bid=" + bid +
                ", type=" + type() +
                '}';
    }
}

public class CamelCards {
    public static void main(String[] args) throws IOException {
        String filename = "C:\\Users\\popov\\IdeaProjects\\aoc\\src\\main\\java\\org\\stefanpopov\\aoc23\\day7\\puzzleinput.txt";
        int task1Result = task1(filename);
        System.out.printf("Result of task 1 is [%d]%n", task1Result);
    }

    private static int task1(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        int id = 0;
        List<Hand> hands = new LinkedList<>();
        while ((line = reader.readLine()) != null) {
            String[] lineParts = line.split("\\s+");
            hands.add(new Hand(id, lineParts[0].trim(), Integer.parseInt(lineParts[1].trim())));
            id++;
        }
        Collections.sort(hands);
        HashMap<Hand, Integer> map = new HashMap<>();
        int rank = 0;
        int lastType = -2;
        for (Hand hand : hands) {
            if (hand.type() != lastType) {
                rank++;
            }
            map.put(hand, rank);
        }
        map
                .forEach((k, v) -> System.out.printf("%s=%d%n", k, v));
        return map.entrySet()
                .stream()
                .mapToInt(it -> it.getKey().getBid() * it.getValue())
                .sum();
    }
}