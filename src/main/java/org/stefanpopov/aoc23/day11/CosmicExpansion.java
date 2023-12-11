package org.stefanpopov.aoc23.day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

class Galaxy {
    private final int i;
    private final int j;

    public Galaxy(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    @Override
    public String toString() {
        return "Galaxy{" +
                "i=" + i +
                ", j=" + j +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Galaxy galaxy = (Galaxy) o;
        return i == galaxy.i && j == galaxy.j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }
}

public class CosmicExpansion {

    public static void main(String[] args) throws IOException {
        String filename = "C:\\Users\\popov\\IdeaProjects\\aoc\\src\\main\\java\\org\\stefanpopov\\aoc23\\day11\\puzzleinput.txt";
        long task1Result = task1(filename);
        System.out.printf("Result of task is [%d]%n", task1Result);
    }

    public static long task1(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        char[][] universe = new char[0][];
        int universeLength = 0;
        int row = 0;
        while ((line = reader.readLine()) != null) {
            if (universeLength == 0) {
                // only on the first line
                universeLength = line.trim().length();
                universe = new char[universeLength][universeLength];
            }
            universe[row] = line.trim().toCharArray();
            row++;
        }
        // expand universe
        List<Integer> rowsToExpand = new LinkedList<>();
        List<Integer> colsToExpand = new LinkedList<>();
        for (int i = 0; i < universeLength; i++) {
            int galaxiesInRow = 0;
            for (int j = 0; j < universeLength; j++) {
                if (universe[i][j] == '#')
                    galaxiesInRow++;
                System.out.printf("%s ", universe[i][j]);
            }
            if (galaxiesInRow == 0)
                // expand row
                rowsToExpand.add(i);
            System.out.println();
        }
        char[][] transposed = transpose(universe);
        for (int i = 0; i < transposed.length; i++) {
            int galaxiesInColumn = 0;
            for (int j = 0; j < transposed[i].length; j++) {
                if (transposed[i][j] == '#')
                    galaxiesInColumn++;
                System.out.printf("%s ", transposed[i][j]);
            }
            if (galaxiesInColumn == 0)
                // expand row
                colsToExpand.add(i);
            System.out.println();
        }
        // initialise galaxies
        List<Galaxy> galaxyList = new LinkedList<>();
        for (int i = 0; i < universeLength; i++) {
            for (int j = 0; j < universeLength; j++) {
                if (universe[i][j] == '#')
                    galaxyList.add(new Galaxy(i, j));
            }
        }

        // traverse universe
        long result = 0;
        for (int i = 0; i < galaxyList.size() - 1; i++) {
            for (int j = i + 1; j < galaxyList.size(); j++) {
                Galaxy x = galaxyList.get(i);
                Galaxy y = galaxyList.get(j);
                long rowDiff = Math.abs(x.getI() - y.getI()) + (expandedBetween(rowsToExpand, x.getI(), y.getI()));
                long colDiff = Math.abs(x.getJ() - y.getJ()) +  (expandedBetween(colsToExpand, x.getJ(), y.getJ()));
                System.out.printf("Distance between %s and %s is %d%n", x, y, colDiff);
                result += rowDiff + colDiff;
            }
        }
        return result;
    }

    public static long expandedBetween(List<Integer> rowsToExpand, int x, int y) {
        int bigger = Math.max(x, y);
        int smaller = Math.min(x, y);
        return rowsToExpand
                .stream()
                .filter(it -> smaller < it && it < bigger)
                .count() * 999_999;
    }

    public static char[][] transpose(char[][] universe) {
        char[][] expanded = new char[universe[0].length][universe.length];
        for (int i = 0; i < universe.length; i++)
            for (int j = 0; j < universe[i].length; j++)
                expanded[j][i] = universe[i][j];
        return expanded;
    }

}
