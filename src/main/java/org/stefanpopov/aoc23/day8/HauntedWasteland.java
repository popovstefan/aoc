package org.stefanpopov.aoc23.day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Node {
    private final String location;
    private Node left;
    private Node right;

    public Node(String location) {
        this.location = location;
        this.left = null;
        this.right = null;
    }

    public String getLocation() {
        return location;
    }

    public Node getRight() {
        return right;
    }

    public Node getLeft() {
        return left;
    }

    public boolean isStart() {
        return location.endsWith("A");
    }

    public boolean isEnd() {
        return location.endsWith("Z");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(location, node.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "Node{" +
                "location='" + location + '\'' +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}

public class HauntedWasteland {
    public static void main(String[] args) throws IOException {
        String filename = "C:\\Users\\popov\\IdeaProjects\\aoc\\src\\main\\java\\org\\stefanpopov\\aoc23\\day8\\2puzzleinput.txt";
        long task1Result = task1(filename);
        System.out.printf("Result of task 1 is [%d]%n", task1Result);
    }

    private static long task1(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        String directions = "";
        HashMap<String, Node> nodes = new HashMap<>();
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty())
                continue;
            String[] lineParts = line.split("=");
            if (lineParts.length == 1)
                directions = lineParts[0].trim();
            else {
                String location = lineParts[0].trim();
                String nodeInfo = lineParts[1].replace("(", " ");
                nodeInfo = nodeInfo.replace(")", " ");
                nodeInfo = nodeInfo.trim();
                String[] info = nodeInfo.split(",");
                String leftLocation = info[0].trim();
                String rightLocation = info[1].trim();
                Node current = new Node(location);
                Node leftNode = new Node(leftLocation);
                Node rightNode = new Node(rightLocation);
                current.setLeft(leftNode);
                current.setRight(rightNode);
                nodes.putIfAbsent(current.getLocation(), current);
                nodes.computeIfPresent(current.getLocation(), (k, v) -> {
                    v.setRight(rightNode);
                    v.setLeft(leftNode);
                    return v;
                });
                nodes.putIfAbsent(leftNode.getLocation(), leftNode);
                nodes.putIfAbsent(rightNode.getLocation(), rightNode);
            }

        }
        directions = new String(new char[10000]).replace("\0", directions);
        List<Node> startNodes = nodes.entrySet()
                .stream()
                .filter(it -> it.getKey().endsWith("A"))
                .map(Map.Entry::getValue)
                .toList();
        List<Long> lengths = new LinkedList<>();

        for (int k = 0; k < startNodes.size(); k++) {
            long steps = 0;
            Node start = startNodes.get(k);
            for (int i = 0; i < directions.length(); i++) {
                System.out.printf("Node %s%n", start);
                if (directions.charAt(i) == 'R')
                    start = nodes.get(start.getRight().getLocation());
                else
                    start = nodes.get(start.getLeft().getLocation());
                steps++;
                if (start != null && start.getLocation().endsWith("Z")) {
                    break;
                }
            }
            lengths.add(steps);
        }
        return lcm(lengths);
    }

    private static long gcd(long x, long y) {
        return (y == 0) ? x : gcd(y, x % y);
    }

    public static long gcd(long... numbers) {
        return Arrays.stream(numbers).reduce(0, HauntedWasteland::gcd);
    }

    public static long lcm(long... numbers) {
        return Arrays.stream(numbers).reduce(1, (x, y) -> x * (y / gcd(x, y)));
    }

    public static long lcm(List<Long> numbers) {
        return numbers.stream().reduce(1L, (x, y) -> x * (y / gcd(x, y)));
    }
}
