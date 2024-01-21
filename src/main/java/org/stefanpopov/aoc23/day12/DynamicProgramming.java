package org.stefanpopov.aoc23.day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


class Tree {
    private Node root;

    public Tree(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    public void insert(Node node, String sequence, char lastChar) {
        Node current = root;
        for (int i = 0; i < sequence.length(); i++) {
            if (current == null)
                break;
            else {
                if (sequence.charAt(i) == '#')
                    current = current.getLeft();
                else
                    current = current.getRight();
            }
        }
        if (lastChar == '.')
            current.setRight(node);
        else if (lastChar == '#')
            current.setLeft(node);
        else if (lastChar == '?') {
            current.setRight(Node.of('.', current.getDepth() + 1L));
            current.setLeft(Node.of('#', current.getDepth() + 1L));
        }
    }
}

class Node {
    private char value;
    private long depth;
    private Node left;
    private Node right;
    private List<Node> children;

    private Node(char value, long depth) {
        this.depth = depth;
        this.value = value;
        this.left = null;
        this.right = null;
        this.children = new ArrayList<>();
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public Node getLeft() {
        return left;
    }

    public long getDepth() {
        return depth;
    }

    public char getValue() {
        return value;
    }

    public Node addChild(char value, long depth) {
        Node newChild = new Node(value, depth);
        children.add(newChild);
        return newChild;
    }

    public List<Node> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public static Node of(char value, long depth) {
        return new Node(value, depth);
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
        Node root = Node.of('S', 0);
        while ((line = reader.readLine()) != null) {
            String[] lineParts = line.split("\\s+");
            List<Long> numbers = Arrays.stream(lineParts[1].split(","))
                    .filter(it -> !it.trim().isEmpty())
                    .mapToLong(it -> Long.parseLong(it.trim()))
                    .boxed()
                    .toList();
            String springSequence = lineParts[0].trim();
            int springSize = springSequence.length();
            List<Node> addedChildren = new LinkedList<>();
            for (int i = 0; i < springSize; i++) {
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
