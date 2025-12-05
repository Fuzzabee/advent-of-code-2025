/***
 * https://adventofcode.com/2025/day/3
 ***/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

class Range {
    public long minimum;
    public long maximum;

    public Range(long min, long max) {
        this.minimum = min;
        this.maximum = max;
    }
}

class CompareByMinRange implements Comparator<Range> {
    public int compare(Range r1, Range r2) {
        return Long.compare(r1.minimum, r2.minimum);
    }
}

class Constants {
    public static final String PUZZLE_INPUT = "PuzzleInput.txt";
    public static final String TEST_INPUT = "TestInput.txt";
}

public class Cafeteria {
    public static void main(String[] args) {
//        ArrayList<String> input = readFile(Constants.TEST_INPUT);
        ArrayList<String> input = readFile(Constants.PUZZLE_INPUT);

        long part1StartTime = System.currentTimeMillis();
        int numFreshIngredients = findNumberFreshIngredients(input);
        long part1EndTime = System.currentTimeMillis();
        long part1Time = (part1EndTime - part1StartTime);
        System.out.printf("Number of ID's for Part 1: %d\n", numFreshIngredients);
        System.out.printf("Part 1 run time: %dms\n\n", part1Time);

        long part2StartTime = System.currentTimeMillis();
        long numTotalIDs = findTotalOfRanges(input);
        long part2EndTime = System.currentTimeMillis();
        long part2Time = (part2EndTime - part2StartTime);
        System.out.printf("Number of ingredient ID's for Part 2: %d\n", numTotalIDs);
        System.out.printf("Part 1 run time: %dms\n\n", part2Time);
    }

    public static int findNumberFreshIngredients(ArrayList<String> ingredientDatabase) {
        ArrayList<Range> ranges = getRangesFromDatabase(ingredientDatabase);
        ArrayList<Long> productIDs = getIDsFromDatabase(ingredientDatabase);

        int numValidIDs = 0;

        for (Long id : productIDs) {
            for (Range range : ranges) {
                if (id >= range.minimum && id <= range.maximum) {
                    numValidIDs++;
                    break;
                }
            }
        }

        return numValidIDs;
    }

    public static long findTotalOfRanges(ArrayList<String> ingredientDatabase) {
        ArrayList<Range> ranges = getRangesFromDatabase(ingredientDatabase);

        long sum = 0;
        for (Range r : ranges) {
            sum += r.maximum - r.minimum + 1;
        }

        return sum;
    }

    public static ArrayList<Long> getIDsFromDatabase(ArrayList<String> db) {
        ArrayList<Long> ids = new ArrayList<>();

        boolean isPastRanges = false;
        for (String id : db) {
            if (id.isBlank()) {
                isPastRanges = true;
                continue;
            }

            if (isPastRanges) {
                ids.add(Long.parseLong(id));
            }
        }

        return ids;
    }

    public static ArrayList<Range> getRangesFromDatabase(ArrayList<String> db) {
        ArrayList<Range> ranges = new ArrayList<>();
        for (String range : db) {
            if (range.isBlank()) {
                break;
            }

            String[] splitRange = range.split("-");
            long min = Long.parseLong(splitRange[0]);
            long max = Long.parseLong(splitRange[1]);
            ranges.add(new Range(min, max));
        }

        ranges.sort(new CompareByMinRange());
        ranges = pruneRanges(ranges);
        return ranges;
    }

    public static ArrayList<Range> pruneRanges(ArrayList<Range> ranges) {
        ArrayList<Range> pruned = new ArrayList<>();

        for (int i = 0; i < ranges.size(); i++) {
            Range curRange = ranges.get(i);
            long newMax = ranges.get(i).maximum;
            for (int j = i + 1; j < ranges.size(); j++) {
                if (ranges.get(j).minimum <= newMax) {
                    newMax = Math.max(ranges.get(j).maximum, newMax);
                    i = j;
                } else {
                    break;
                }
            }
            if (newMax != curRange.maximum) {
                curRange.maximum = newMax;
            }
            pruned.add(curRange);
        }

        ranges.clear();

        return pruned;
    }

    public static ArrayList<String> readFile(String filename) {
        File file = new File(filename);
        ArrayList<String> lines = new ArrayList<>();

        try (Scanner fileReader = new Scanner(file)) {
            while (fileReader.hasNextLine()){
                lines.add(fileReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        return lines;
    }
}