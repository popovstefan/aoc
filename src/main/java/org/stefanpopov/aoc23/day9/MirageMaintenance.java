package org.stefanpopov.aoc23.day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

class Sequence {
    private List<Long> values;

    public Sequence(List<Long> values) {
        this.values = values;
    }

    public void setValues(List<Long> values) {
        this.values = values;
    }

    public List<Long> getValues() {
        return values;
    }

    public boolean isComputable() {
        return values.size() > 1 && values.stream().anyMatch(it -> it != 0L);
    }

    public List<Long> computeDifference() {
        if (isComputable()) {
            List<Long> result = new LinkedList<>();
            for (int i = 0; i < values.size() - 1; i++)
                result.add(values.get(i + 1) - values.get(i));
            return result;
        } else
            return Collections.singletonList(0L);
    }

    @Override
    public String toString() {
        return "Sequence{" +
                "values=" + values +
                '}';
    }
}
// 1785149582
// 1772145794
// 1772145754
public class MirageMaintenance {
    public static void main(String[] args) throws IOException {
        String filename = "C:\\Users\\popov\\IdeaProjects\\aoc\\src\\main\\java\\org\\stefanpopov\\aoc23\\day9\\puzzleinput.txt";
        long task1Result = task1(filename);
        System.out.printf("Result of task 1 is [%d]%n", task1Result);
    }

    public static long task1(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        Map<Sequence, List<Sequence>> map = new HashMap<>();
        while ((line = reader.readLine()) != null) {
            List<Long> values = Arrays.stream(line.split("\\s+"))
                    .mapToLong(it -> Long.parseLong(it.trim()))
                    .boxed()
                    .collect(Collectors.toList());
            Collections.reverse(values);
            Sequence sequence = new Sequence(values);
            Sequence iterableSequence = new Sequence(values);
            List<Sequence> differences = new LinkedList<>();
            differences.add(sequence);
            while (iterableSequence.isComputable()) {
                differences.add(new Sequence(iterableSequence.computeDifference()));
                iterableSequence.setValues(iterableSequence.computeDifference());
            }
            map.put(sequence, differences);
        }
        map.entrySet().forEach(it -> System.out.printf("%s to %s%n", it.getKey(), it.getValue()));
        map.values()
                .stream()
                .mapToLong(it -> it.stream()
                        .mapToLong(sequence -> sequence.getValues().get(sequence.getValues().size() - 1))
                        .sum()
                )
                .forEach(it -> System.out.printf("val %d%n", it));
        return map.values()
                .stream()
                .mapToLong(it -> it.stream()
                        .mapToLong(sequence -> sequence.getValues().get(sequence.getValues().size() - 1))
                        .sum()
                ).sum();
    }
}
