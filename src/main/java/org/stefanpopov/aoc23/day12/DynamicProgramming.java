package org.stefanpopov.aoc23.day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

class Node<T> {
    private T value;
    private long depth;
    private Node<T> left;
    private Node<T> right;
    private List<Node<T>> children;

    private Node(T value, long depth) {
        this.depth = depth;
        this.value = value;
        this.left = null;
        this.right = null;
        this.children = new ArrayList<>();
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    public Node<T> getLeft() {
        return left;
    }

    public long getDepth() {
        return depth;
    }

    public T getValue() {
        return value;
    }

    public Node<T> addChild(T value, long depth) {
        Node newChild = new Node<>(value, depth);
        children.add(newChild);
        return newChild;
    }

    public List<Node<T>> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public static <T> Node<T> of(T value, long depth) {
        return new Node<>(value, depth);
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                "depth=" + depth +
                ", children=" + children +
                '}' + System.lineSeparator();
    }
}

public class DynamicProgramming {
    public static void main(String[] args) throws IOException {
        String filename = "C:\\Users\\popov\\IdeaProjects\\aoc\\src\\main\\java\\org\\stefanpopov\\aoc23\\day12\\puzzleinputtest.txt";
        long task1Result = task1(filename);
        System.out.printf("Result of task is [%d]%n", task1Result);
    }

    public static long task1(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        Node<Character> root = Node.of('S', 0);
        while ((line = reader.readLine()) != null) {
            String[] lineParts = line.split("\\s+");
            List<Long> numbers = Arrays.stream(lineParts[1].split(","))
                    .filter(it -> !it.trim().isEmpty())
                    .mapToLong(it -> Long.parseLong(it.trim()))
                    .boxed()
                    .toList();
            String springSequence = lineParts[0].trim();
            int springSize = springSequence.length();
            for (int i = 0 + 1; i < springSize; i++) {
                if (isValidPath(numbers, springSequence.substring(0, i))) {
                    // add child, yeah? 
                }
            }
            // todo build tree with lineParts[0]
        }
        return -1;
    }

    public static boolean isValidPath(List<Long> numbers, String subString) {
        return true; // todo
    }
}
