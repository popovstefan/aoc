package org.stefanpopov.aoc23.day10;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Tree<T> {
    private T value;
    private long depth;
    private List<Tree<T>> children;

    private Tree(T value, long depth) {
        this.depth = depth;
        this.value = value;
        this.children = new ArrayList<>();
    }

    public long getDepth() {
        return depth;
    }

    public T getValue() {
        return value;
    }

    public Tree<T> addChild(T value, long depth) {
        Tree<T> newChild = new Tree<>(value, depth);
        children.add(newChild);
        return newChild;
    }

    public List<Tree<T>> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public static <T> Tree<T> of(T value, long depth) {
        return new Tree<>(value, depth);
    }

    @Override
    public String toString() {
        return "Tree{" +
                "value=" + value +
                "depth=" + depth +
                ", children=" + children +
                '}' + System.lineSeparator();
    }
}

class Pipe {
    private static final char GROUND = '.';
    private static final char VERTICAL_PIPE = '|';
    private static final char HORIZONTAL_PIPE = '-';
    private static final char NE_BEND = 'L';
    private static final char NW_BEND = 'J';
    private static final char SW_BEND = '7';
    private static final char SE_BEND = 'F';
    private static final char START = 'S';
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pipe pipe1 = (Pipe) o;
        return i == pipe1.i && j == pipe1.j;
    }

    private boolean isValid(char someChar) {
        return someChar == VERTICAL_PIPE
                || someChar == HORIZONTAL_PIPE
                || someChar == NE_BEND
                || someChar == NW_BEND
                || someChar == SW_BEND
                || someChar == SE_BEND
                || someChar == START;
    }

    public List<Pipe> init(char[][] maze) {
        // assume you are at start, look around everywhere
        List<Pipe> result = new ArrayList<>(Collections.emptyList());
        if (i > 0) {
            // top row, left-to-right
//            if (j > 0 && this.isValid(maze[i - 1][j - 1]))
//                result.add(new Pipe(i - 1, j - 1, maze[i - 1][j - 1]));
            if (this.isValid(maze[i - 1][j]))
                result.add(new Pipe(i - 1, j, maze[i - 1][j]));
//            if (j < maze[i - 1].length - 1 && this.isValid(maze[i - 1][j + 1]))
//                result.add(new Pipe(i - 1, j + 1, maze[i - 1][j + 1]));
        }
        // current row, left then right
        if (j > 0 && this.isValid(maze[i][j - 1]))
            result.add(new Pipe(i, j - 1, maze[i][j - 1]));
        if (j < maze[i].length - 1 && this.isValid(maze[i][j + 1]))
            result.add(new Pipe(i, j + 1, maze[i][j + 1]));

        if (i < maze.length - 1) {
            // bottom row, left-to-right
//            if (j > 0 && this.isValid(maze[i + 1][j - 1]))
//                result.add(new Pipe(i + 1, j - 1, maze[i + 1][j - 1]));
            if (this.isValid(maze[i + 1][j]))
                result.add(new Pipe(i + 1, j, maze[i + 1][j]));
//            if (j < maze[i + 1].length - 1 && this.isValid(maze[i + 1][j + 1]))
//                result.add(new Pipe(i + 1, j + 1, maze[i + 1][j + 1]));
        }
        return result;
    }

    public List<Pipe> lookAround(char[][] maze) {
        if (pipe == START)
            return init(maze);
        List<Pipe> result = new ArrayList<>(Collections.emptyList());
        if (pipe != GROUND) {
            if (pipe == VERTICAL_PIPE) {
                // check up and down
                if (i > 0 && this.isValid(maze[i - 1][j])) {
                    result.add(new Pipe(i - 1, j, maze[i - 1][j]));
                }
                if (i < maze.length - 1 && this.isValid(maze[i + 1][j])) {
                    result.add(new Pipe(i + 1, j, maze[i + 1][j]));
                }
            } else if (pipe == HORIZONTAL_PIPE) {
                // check left-right
                if (j > 0 && this.isValid(maze[i][j - 1])) {
                    result.add(new Pipe(i, j - 1, maze[i][j - 1]));
                }
                if (j < maze[i].length - 1 && this.isValid(maze[i][j + 1])) {
                    result.add(new Pipe(i, j + 1, maze[i][j + 1]));
                }
            } else if (pipe == NE_BEND) {
                // check top-right
                if (i > 0 && this.isValid(maze[i - 1][j])) {
                    result.add(new Pipe(i - 1, j, maze[i - 1][j]));
                }
                if (j < maze[i].length - 1 && this.isValid(maze[i][j + 1])) {
                    result.add(new Pipe(i, j + 1, maze[i][j + 1]));
                }
            } else if (pipe == NW_BEND) {
                // check top-left
                if (i > 0 && this.isValid(maze[i - 1][j])) {
                    result.add(new Pipe(i - 1, j, maze[i - 1][j]));
                }
                if (j > 0 && this.isValid(maze[i][j - 1])) {
                    result.add(new Pipe(i, j - 1, maze[i][j - 1]));
                }
            } else if (pipe == SW_BEND) {
                // check bottom-left
                if (i < maze.length - 1 && this.isValid(maze[i + 1][j])) {
                    result.add(new Pipe(i + 1, j, maze[i + 1][j]));
                }
                if (j > 0 && this.isValid(maze[i][j - 1])) {
                    result.add(new Pipe(i, j - 1, maze[i][j - 1]));
                }
            } else if (pipe == SE_BEND) {
                // check bottom-right
                if (i < maze.length - 1 && this.isValid(maze[i + 1][j])) {
                    result.add(new Pipe(i + 1, j, maze[i + 1][j]));
                }
                if (j < maze[i].length - 1 && this.isValid(maze[i][j + 1])) {
                    result.add(new Pipe(i, j + 1, maze[i][j + 1]));
                }
            } else {
                System.out.printf("%nWhat? '%s'%n", pipe);
                return Collections.emptyList();
            }
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }

    @Override
    public String toString() {
        return "Pipe{" +
                "i=" + i +
                ", j=" + j +
                ", pipe=" + pipe +
                '}';
    }
}

public class PipeMaze {
    private static final char START = 'S';

    public static void main(String[] args) throws IOException {
        String filename = "C:\\Users\\popov\\IdeaProjects\\aoc\\src\\main\\java\\org\\stefanpopov\\aoc23\\day10\\puzzleinput.txt";
        long task1Result = task1(filename);
        System.out.printf("Result of task is [%d]%n", task1Result);
    }

    public static void addChildren(Tree<Pipe> treeNode, @NotNull List<Pipe> children, char[][] maze, Set<Pipe> pipes) {
        Queue<Tree<Pipe>> queue = new ArrayDeque<>();
        queue.add(treeNode);
        if (!children.isEmpty()) {
            children.stream()
                    .filter(it -> !pipes.contains(it))
                    .forEach(it -> {
                        pipes.add(it);
                        treeNode.addChild(it, treeNode.getDepth() + 1L);
                    });
            treeNode.getChildren().forEach(it -> addChildren(it, it.getValue().lookAround(maze), maze, pipes));
        }
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
        System.out.println(mazeLength);
        // initialise tree structure
        Tree<Pipe> root = null;
        Pipe start = null;
        Set<Pipe> pipes = new HashSet<>();
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                System.out.printf("%s ", maze[i][j]);
                if (maze[i][j] == START && root == null) {
                    start = new Pipe(i, j, maze[i][j]);
                    root = Tree.of(start, 0L);
                    Queue<Tree<Pipe>> queue = new ArrayDeque<>();
                    queue.add(root);
                    pipes.add(start);
                    while (!queue.isEmpty()) {
                        // get current node
                        Tree<Pipe> currentNode = queue.remove();
                        // add its children
                        List<Pipe> theChildren = currentNode.getValue().lookAround(maze);
                        for (Pipe child : theChildren) {
                            if (!pipes.contains(child)) {
                                currentNode.addChild(child, currentNode.getDepth() + 1);
                                if (!start.equals(child))
                                    pipes.add(child);
                            }
                        }
                        // queue them
                        queue.addAll(currentNode.getChildren());
                    }
                }
            }
            System.out.println();
        }
//        System.out.println(root);
        System.out.println();
        // perform bfs
        search(start, root, 0, true);
        return Long.MIN_VALUE;
    }

    public static <T> Optional<Tree<T>> search(T value, Tree<T> root, int pathLength, boolean first) {
        Queue<Tree<T>> queue = new ArrayDeque<>();
        queue.add(root);
        long maxDepth = -1;

        Tree<T> currentNode;
        while (!queue.isEmpty()) {
            currentNode = queue.remove();
            System.out.printf("Visited node with value: {%s} and path {%d} and depth {%d} %n", currentNode.getValue(), pathLength, currentNode.getDepth());
            maxDepth = Math.max(maxDepth, currentNode.getDepth());
            if (currentNode.getValue().equals(value)) {
                if (first) {
                    first = false;
                    pathLength++;
                    queue.addAll(currentNode.getChildren());
                } else {
                    System.out.printf("Max depth [%d]%n", maxDepth);
                    System.out.printf("Final path length {%d}%n", pathLength);
                    return Optional.of(currentNode);
                }
            } else {
                pathLength++;
                queue.addAll(currentNode.getChildren());
            }
        }
//        System.out.printf("Final path length {%d}%n", pathLength);
        System.out.printf("Max depth [%d]%n", maxDepth);
        return Optional.empty();
    }
}
