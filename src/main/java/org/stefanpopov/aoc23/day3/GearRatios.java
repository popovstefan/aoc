package org.stefanpopov.aoc23.day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GearRatios {

    public static void main(String[] args) throws IOException {
        String filename = "C:\\Users\\popov\\IdeaProjects\\aoc\\src\\main\\java\\org\\stefanpopov\\aoc23\\day3\\puzzleinput.txt";
        int task2Result = task2(filename);
        System.out.printf("Result of task 2 is [%d]%n", task2Result);
    }

    private static int task2(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        String previousLine = null;
        String currentLine = null;
        String nextLine = null;
        HashMap<Integer, String> partNumberToStar = new HashMap<>();
        while ((line = reader.readLine()) != null) {
            if (previousLine == null && currentLine == null) { // 1st line
                currentLine = line;
                currentLine += ".";
            } else if (previousLine == null) { // 2nd line
                nextLine = line;
                nextLine += ".";
                // traverse current line without previous, with next, this is the first line in the input
                int si = -1;
                int number = 0;
                for (int i = 0; i < currentLine.length(); i++) {
                    if (Character.isDigit(currentLine.charAt(i))) {
                        if (si == -1)
                            si = i;
                        number = 10 * number + Integer.parseInt(String.valueOf(currentLine.charAt(i)));
                    } else {
                        if (si >= 0) {
                            // number has ended, check for symbols around
                            boolean check = false;
                            for (int j = si; j < i; j++) {
                                check |= hasStarAround(previousLine, currentLine, nextLine, j);
                                if (hasStarAround(previousLine, currentLine, nextLine, j)) {
                                    partNumberToStar.put(number, starHash(previousLine, currentLine, nextLine, j));
                                }
                            }
                            if (check) {
                                System.out.printf("has star around [%s] adding [%d]%n", currentLine, number);
                            }
                            // reset start index
                            si = -1;
                            number = 0;
                        }
                    }
                }
                previousLine = currentLine;
                currentLine = nextLine;
            } else {
                // 3rd line onward
                nextLine = line;
                nextLine += ".";
                // traverse current line with previous and next, this is >= 2 line in the input
                int si = -1;
                int number = 0;
                for (int i = 0; i < currentLine.length(); i++) {
                    if (Character.isDigit(currentLine.charAt(i))) {
                        if (si == -1)
                            si = i;
                        number = 10 * number + Integer.parseInt(String.valueOf(currentLine.charAt(i)));
                    } else {
                        if (si >= 0) {
                            // number has ended, check for symbols around
                            boolean check = false;
                            for (int j = si; j < i; j++) {
                                check |= hasStarAround(previousLine, currentLine, nextLine, j);
                                if (hasStarAround(previousLine, currentLine, nextLine, j)) {
                                    partNumberToStar.put(number, starHash(previousLine, currentLine, nextLine, j));
                                }
                            }
                            if (check) {
                                System.out.printf("has star around [%s] adding %d%n", currentLine, number);
                            }
                            // reset start index
                            number = 0;
                            si = -1;
                        }
                    }
                }
                previousLine = currentLine;
                currentLine = nextLine;
            }
        }
        // traverse last (next) line with only current as previous
        int si = -1;
        int number = 0;
        for (int i = 0; i < currentLine.length(); i++) {
            if (Character.isDigit(currentLine.charAt(i))) {
                if (si == -1)
                    si = i;
                number = 10 * number + Integer.parseInt(String.valueOf(currentLine.charAt(i)));
            } else {
                if (si >= 0) {
                    // number has ended, check for symbols around
                    boolean check = false;
                    for (int j = si; j < i; j++) {
                        check |= hasStarAround(previousLine, currentLine, nextLine, j);
                        if (hasStarAround(previousLine, currentLine, nextLine, j)) {
                            partNumberToStar.put(number, starHash(previousLine, currentLine, nextLine, j));
                        }
                    }
                    if (check) {
                        System.out.printf("has star around [%s] adding [[%d]]%n", currentLine, number);
                    }
                    // reset start index
                    si = -1;
                    number = 0;
                }
            }
        }
        // continue map calculations here
        // only values with 2 occurrences exactly
        Set<String> validStars = partNumberToStar
                .values()
                .stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .filter(it -> it.getValue() == 2)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
        System.out.println("There are " + validStars.size() + " valid stars");
        HashMap<String, Integer> preResultStarToRatio = new HashMap<>();
        partNumberToStar
                .entrySet()
                .stream()
                .filter(it -> validStars.contains(it.getValue()))
                .forEach(it -> {
                    preResultStarToRatio.putIfAbsent(it.getValue(), 1);
                    preResultStarToRatio.computeIfPresent(it.getValue(), (star, ratio) -> ratio * it.getKey());
                });
        System.out.printf("preResultStarToRatio size %d%n", preResultStarToRatio.size());
        System.out.printf("numbers to Stars size %d%n", partNumberToStar.size());
        System.out.printf("Unique stars there %d%n", partNumberToStar.values().stream().distinct().toList().size());
        return preResultStarToRatio
                .values()
                .stream()
                .mapToInt(it -> it)
                .sum();
    }

    private static String starHash(String previousLine, String currentLine, String nextLine, int index) {
        if (nextLine != null) {
            if (index > 0)
                if (nextLine.charAt(index - 1) == '*')
                    return nextLine + (index - 1);
            if (nextLine.charAt(index) == '*')
                return nextLine + index;
            if (index < nextLine.length() - 1)
                if (nextLine.charAt(index + 1) == '*')
                    return nextLine + (index + 1);
        }
        if (index > 0)
            if (currentLine.charAt(index - 1) == '*')
                return currentLine + (index - 1);
        if (index < currentLine.length() - 1)
            if (currentLine.charAt(index + 1) == '*')
                return currentLine + (index + 1);
        if (previousLine != null) {
            if (index > 0)
                if (previousLine.charAt(index - 1) == '*')
                    return previousLine + (index - 1);
            if (previousLine.charAt(index) == '*')
                return previousLine + index;
            if (index < previousLine.length() - 1)
                if (previousLine.charAt(index + 1) == '*')
                    return previousLine + (index + 1);
        }
        return "";
    }

    private static int task1(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        String previousLine = null;
        String currentLine = null;
        String nextLine = null;
        int sum = 0;
        while ((line = reader.readLine()) != null) {
            if (previousLine == null && currentLine == null) { // 1st line
                currentLine = line;
                currentLine += ".";
            } else if (previousLine == null) { // 2nd line
                nextLine = line;
                nextLine += ".";
                // todo traverse current line without previous, with next, this is the first line in the input
                int si = -1;
                int number = 0;
                for (int i = 0; i < currentLine.length(); i++) {
                    if (Character.isDigit(currentLine.charAt(i))) {
                        if (si == -1)
                            si = i;
                        number = 10 * number + Integer.parseInt(String.valueOf(currentLine.charAt(i)));
                    } else {
                        if (si >= 0) {
                            // number has ended, check for symbols around
                            boolean check = false;
                            for (int j = si; j < i; j++)
                                check |= hasSymbolAround(previousLine, currentLine, nextLine, j);
                            if (check) {
                                System.out.printf("Cur line [%s] adding [%d]%n", currentLine, number);
                                sum += number;
                            }
                            // reset start index
                            si = -1;
                            number = 0;
                        }
                    }
                }
                previousLine = currentLine;
                currentLine = nextLine;
            } else {
                // 3rd line onward
                nextLine = line;
                nextLine += ".";
                // todo traverse current line with previous and next, this is >= 2 line in the input
                int si = -1;
                int number = 0;
                for (int i = 0; i < currentLine.length(); i++) {
                    if (Character.isDigit(currentLine.charAt(i))) {
                        if (si == -1)
                            si = i;
                        number = 10 * number + Integer.parseInt(String.valueOf(currentLine.charAt(i)));
                    } else {
                        if (si >= 0) {
                            // number has ended, check for symbols around
                            boolean check = false;
                            for (int j = si; j < i; j++)
                                check |= hasSymbolAround(previousLine, currentLine, nextLine, j);
                            if (check) {
                                System.out.printf("Cur lin [%s] adding %d%n", currentLine, number);
                                sum += number;
                            }
                            // reset start index
                            number = 0;
                            si = -1;
                        }
                    }
                }
                previousLine = currentLine;
                currentLine = nextLine;
            }
        }
        // todo traverse last (next) line with only current as previous
        int si = -1;
        int number = 0;
        for (int i = 0; i < currentLine.length(); i++) {
            if (Character.isDigit(currentLine.charAt(i))) {
                if (si == -1)
                    si = i;
                number = 10 * number + Integer.parseInt(String.valueOf(currentLine.charAt(i)));
            } else {
                if (si >= 0) {
                    // number has ended, check for symbols around
                    boolean check = false;
                    for (int j = si; j < i; j++)
                        check |= hasSymbolAround(previousLine, currentLine, null, j);
                    if (check) {
                        System.out.printf("Cur line [%s] adding [[%d]]%n", currentLine, number);
                        sum += number;
                    }
                    // reset start index
                    si = -1;
                    number = 0;
                }
            }
        }
        return sum;
    }

    private static boolean isSymbol(char c) {
        return !(Character.isDigit(c) || c == '.');
    }

    private static boolean hasSymbolAround(String previousLine, String currentLine, String nextLine, int index) {
        boolean result = false;
        if (previousLine != null) {
            if (index > 0)
                result |= isSymbol(previousLine.charAt(index - 1));
            result |= isSymbol(previousLine.charAt(index));
            if (index < previousLine.length() - 1)
                result |= isSymbol(previousLine.charAt(index + 1));
        }
        if (index > 0)
            result |= isSymbol(currentLine.charAt(index - 1));
        if (index < currentLine.length() - 1)
            result |= isSymbol(currentLine.charAt(index + 1));
        if (nextLine != null) {
            if (index > 0)
                result |= isSymbol(nextLine.charAt(index - 1));
            result |= isSymbol(nextLine.charAt(index));
            if (index < nextLine.length() - 1)
                result |= isSymbol(nextLine.charAt(index + 1));
        }
        return result;
    }

    private static boolean hasStarAround(String previousLine, String currentLine, String nextLine, int index) {
        boolean result = false;
        if (previousLine != null) {
            if (index > 0)
                result |= previousLine.charAt(index - 1) == '*';
            result |= previousLine.charAt(index) == '*';
            if (index < previousLine.length() - 1)
                result |= previousLine.charAt(index + 1) == '*';
        }
        if (index > 0)
            result |= currentLine.charAt(index - 1) == '*';
        if (index < currentLine.length() - 1)
            result |= currentLine.charAt(index + 1) == '*';
        if (nextLine != null) {
            if (index > 0)
                result |= nextLine.charAt(index - 1) == '*';
            result |= nextLine.charAt(index) == '*';
            if (index < nextLine.length() - 1)
                result |= nextLine.charAt(index + 1) == '*';
        }
        return result;
    }
}
