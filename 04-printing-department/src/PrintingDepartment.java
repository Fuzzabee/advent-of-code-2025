/***
 * https://adventofcode.com/2025/day/3
 ***/

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

class Constants {
    public static final String PUZZLE_INPUT = "PuzzleInput.txt";
    public static final String TEST_INPUT = "TestInput.txt";
}

public class PrintingDepartment {
    public static void main(String[] args) {
//        ArrayList<ArrayList<Character>> floorMap = readFile(Constants.TEST_INPUT);
        ArrayList<ArrayList<Character>> floorMap = readFile(Constants.PUZZLE_INPUT);

        long part1StartTime = System.currentTimeMillis();
        int numAccessibleRollsPart1 = findNumberOfAccessibleRollsPart1(floorMap, false);
        long part1EndTime = System.currentTimeMillis();
        long part1Time = (part1EndTime - part1StartTime);
        System.out.printf("Number of accessible rolls for Part 1: %d\n", numAccessibleRollsPart1);
        System.out.printf("Part 1 run time: %dms\n\n", part1Time);

        long part2StartTime = System.currentTimeMillis();
        int numAccessibleRollsPart2 = findNumberOfAccessibleRollsPart2(floorMap);
        long part2EndTime = System.currentTimeMillis();
        long part2Time = (part2EndTime - part2StartTime);
        System.out.printf("Number of accessible rolls for Part 2: %d\n", numAccessibleRollsPart2);
        System.out.printf("Part 2 run time: %dms\n\n", part2Time);
    }

    public static int findNumberOfAccessibleRollsPart1(ArrayList<ArrayList<Character>> floorMap, boolean part2) {
        int numRolls = 0;
        for (int row = 0; row < floorMap.size(); row++) {
            for (int col = 0; col < floorMap.get(row).size(); col++) {
                if (floorMap.get(row).get(col) != '@') {
                    continue;
                }

                int left = col - 1;
                int right = col + 1;
                int up = row - 1;
                int down = row + 1;

                boolean upIsValid = up >= 0;
                boolean downIsValid = down <= floorMap.size() - 1;
                boolean leftIsValid = left >= 0;
                boolean rightIsValid = right <= floorMap.get(row).size() - 1;

                int rollsSurrounding = 0;
                if (leftIsValid && upIsValid && floorMap.get(up).get(left) == '@') {
                    rollsSurrounding++;
                }
                if (leftIsValid && floorMap.get(row).get(left) == '@') {
                    rollsSurrounding++;
                }
                if (leftIsValid && downIsValid && floorMap.get(down).get(left) == '@') {
                    rollsSurrounding++;
                }
                if (upIsValid && floorMap.get(up).get(col) == '@') {
                    rollsSurrounding++;
                }
                if (downIsValid && floorMap.get(down).get(col) == '@') {
                    rollsSurrounding++;
                }
                if (rightIsValid && upIsValid && floorMap.get(up).get(right) == '@') {
                    rollsSurrounding++;
                }
                if (rightIsValid && floorMap.get(row).get(right) == '@') {
                    rollsSurrounding++;
                }
                if (rightIsValid && downIsValid && floorMap.get(down).get(right) == '@') {
                    rollsSurrounding++;
                }
                if (rollsSurrounding < 4) {
                    numRolls++;
                    if (part2) {
                        floorMap.get(row).set(col, '.');
                    }
                }
            }
        }
        return numRolls;
    }

    public static int findNumberOfAccessibleRollsPart2(ArrayList<ArrayList<Character>> floorMap) {
        int numRolls = 0;
        int nextNumRolls = -1;
        while (nextNumRolls != 0) {
            nextNumRolls = findNumberOfAccessibleRollsPart1(floorMap, true);
            numRolls += nextNumRolls;
        }
        return numRolls;
    }

    public static void printGrid(ArrayList<ArrayList<Character>> grid) {
        for (ArrayList<Character> row : grid) {
            for (char c : row) {
                System.out.printf("%c", c);
            }
            System.out.println();
        }
    }

    public static ArrayList<ArrayList<Character>> readFile(String filename) {
        File file = new File(filename);
        ArrayList<ArrayList<Character>> lines = new ArrayList<>();

        try (Scanner fileReader = new Scanner(file)) {
            while (fileReader.hasNextLine()){
                ArrayList<Character> curRow = new ArrayList<>();
                char[] curLine = fileReader.nextLine().toCharArray();
                for (char c : curLine) {
                    curRow.add(c);
                }
                lines.add(curRow);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        return lines;
    }
}