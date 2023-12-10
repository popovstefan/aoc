package org.stefanpopov.aoc23.day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Tree<T> {
    private Node<T> root;

    public Tree(T rootData) {
        root = new Node<T>();
        root.data = rootData;
        root.children = new ArrayList<Node<T>>();
    }

    public static class Node<T> {
        private T data;
        private Node<T> parent;
        private List<Node<T>> children;
    }
}

class Pipe {
    private int i;
    private int j;
    private char pipe;

    public Pipe(int i, int j, char pipe) {
        this.i = i;
        this.j = j;
        this.pipe = pipe;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public char getPipe() {
        return pipe;
    }

    public void setPipe(char pipe) {
        this.pipe = pipe;
    }
}

public class PipeMaze {

    public static void main(String[] args) throws IOException {
        String filename = "C:\\Users\\popov\\IdeaProjects\\aoc\\src\\main\\java\\org\\stefanpopov\\aoc23\\day10\\puzzleinputtest.txt";
        long task1Result = task1(filename);
        System.out.printf("Result of task is [%d]%n", task1Result);
    }

    public static long task1(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        char[][] maze = new char[0][];
        int mazeLength = 0;
        int row = 0;
        while ((line = reader.readLine()) != null) {
            if (mazeLength == 0) {
                // only on the first line
                mazeLength = line.trim().length();
                maze = new char[mazeLength][mazeLength];
            }
            maze[row] = line.trim().toCharArray();
            row++;
        }
        // todo initialise tree structure
        // todo perform bfs
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++)
                System.out.printf("%s ", maze[i][j]);
            System.out.println();
        }
        return Long.MIN_VALUE;
    }
}
