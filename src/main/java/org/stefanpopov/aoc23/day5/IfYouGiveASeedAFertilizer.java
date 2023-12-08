package org.stefanpopov.aoc23.day5;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class IfYouGiveASeedAFertilizer {

    record Destination(long start, long increment) {
    }

    private static List<Destination> seeds = new LinkedList<>();
    private static TreeMap<Long, Destination> seedToSoilMap = new TreeMap<>();
    private static TreeMap<Long, Destination> seedToFertilizerMap = new TreeMap<>();
    private static TreeMap<Long, Destination> seedToWaterMap = new TreeMap<>();
    private static TreeMap<Long, Destination> seedToLightMap = new TreeMap<>();
    private static TreeMap<Long, Destination> seedToTemperatureMap = new TreeMap<>();
    private static TreeMap<Long, Destination> seedToHumidityMap = new TreeMap<>();
    private static TreeMap<Long, Destination> seedToLocationMap = new TreeMap<>();

    public static void main(String[] args) {
        String filename = "C:\\Users\\popov\\IdeaProjects\\aoc\\src\\main\\java\\org\\stefanpopov\\aoc23\\day5\\puzzleinput.txt";
        long taskResult = task(filename);
        System.out.printf("Result of task 2 is [%d]%n", taskResult);
    }

    public static long task(String filename) {
        TreeMap<Long, String> inputMap = new TreeMap<>();
        long index = 0;
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("seeds:")) {
                    addSeeds(line);
                } else if ((index == 0 && line.isEmpty()) || line.contains("map")) {
                    continue;
                } else {
                    inputMap.put(index, line);
                    index++;
                }
            }
            scanner.close();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.printf("Lol %s%n", fileNotFoundException);
        }
        convertSeedToMap(inputMap);
        return findMinLocation();
    }

    private static void convertSeedToMap(TreeMap<Long, String> inputMap) {
        List<TreeMap<Long, Destination>> maps = List.of(
                seedToSoilMap,
                seedToFertilizerMap,
                seedToWaterMap,
                seedToLightMap,
                seedToTemperatureMap,
                seedToHumidityMap,
                seedToLocationMap
        );
        long i = 0;
        for (TreeMap<Long, Destination> map : maps) {
            String line = inputMap.get(i);
            while (!line.isEmpty()) {
                addRangeToMap(line, map);
                i++;
                if (i >= inputMap.size())
                    break;
                line = inputMap.get(i);
            }
            i++;
        }
    }

    private static void addRangeToMap(@NotNull String line, @NotNull TreeMap<Long, Destination> map) {
        String[] parts = line.split("\\s+");
        long destinationRangeStart = Long.parseLong(parts[0]);
        long sourceRangeStart = Long.parseLong(parts[1]);
        long rangeLength = Long.parseLong(parts[2]);
        Destination destination = new Destination(destinationRangeStart, rangeLength);
        map.put(sourceRangeStart, destination);
    }

    private static void addSeeds(@NotNull String line) {
        String[] parts = line.replace("seeds: ", "").split("\\s+");
        for (int i = 0; i < parts.length; i++) {
            long start = Long.parseLong(parts[i].trim());
            long end = Long.parseLong(parts[++i].trim());
            seeds.add(new Destination(start, end));
        }
    }

    public static long findMinLocation() {
        long result = Long.MAX_VALUE;
        for (Destination seed : seeds) {
            for (long i = 0; i < seed.increment; i++) {
                long soil = getMapValue(seedToSoilMap, seed.start + i);
                long fertilizer = getMapValue(seedToFertilizerMap, soil);
                long water = getMapValue(seedToWaterMap, fertilizer);
                long light = getMapValue(seedToLightMap, water);
                long temperature = getMapValue(seedToTemperatureMap, light);
                long humidity = getMapValue(seedToHumidityMap, temperature);
                long location = getMapValue(seedToLocationMap, humidity);
                result = Math.min(result, location);
            }
        }
        return result;
    }

    private static long getMapValue(@NotNull TreeMap<Long, Destination> seedToSoilMap, long seed) {
        long result = seed;
        Map.Entry<Long, Destination> entry = seedToSoilMap.floorEntry(seed);
        if (entry == null)
            return result;
        long seedKey = entry.getKey();
        Destination destination = entry.getValue();

        if (seed == seedKey)
            result = destination.start;
        else if (seed <= seedKey + destination.increment) {
            long difference = seed - seedKey;
            result = destination.start + difference;
        }
        return result;
    }
}
