/***
 * https://adventofcode.com/2025/day/3
 ***/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

class Constants {
    public static final String PUZZLE_INPUT = "PuzzleInput.txt";
    public static final String TEST_INPUT = "TestInput.txt";
}

public class Lobby {
    public static void main(String[] args) {
        ArrayList<String> idRanges = readFile(Constants.TEST_INPUT);
//        ArrayList<String> idRanges = readFile(Constants.PUZZLE_INPUT);

        long part1StartTime = System.currentTimeMillis();
        ArrayList<Integer> part1ValidID = findMaxJoltagesFromBanksPart1(idRanges);
        int part1SumOfIDs = sumOfArrayList(part1ValidID);
        long part1EndTime = System.currentTimeMillis();
        long part1Time = (part1EndTime - part1StartTime);
        System.out.printf("Sum of ID's for Part 1: %d\n", part1SumOfIDs);
        System.out.printf("Part 1 run time: %dms\n\n", part1Time);

        long part2StartTime = System.currentTimeMillis();
        ArrayList<Integer> part2ValidID = findMaxJoltagesFromBanksPart2(idRanges);
        int part2SumOfIDs = sumOfArrayList(part2ValidID);
        long part2EndTime = System.currentTimeMillis();
        long part2Time = (part2EndTime - part2StartTime);
        System.out.printf("Sum of ID's for Part 1: %d\n", part2SumOfIDs);
        System.out.printf("Part 1 run time: %dms\n\n", part2Time);
    }

    public static ArrayList<Integer> findMaxJoltagesFromBanksPart1(ArrayList<String> banks) {
        ArrayList<Integer> maxJoltages = new ArrayList<>();

        for (String s : banks) {
            char largest = 0;
            char nextLargest = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) > largest && i + 1 != s.length()) {
                    largest = s.charAt(i);
                    nextLargest = 0;
                } else if (s.charAt(i) > nextLargest) {
                    nextLargest = s.charAt(i);
                }
            }
            int joltage = Integer.parseInt(String.valueOf(largest)) * 10 + Integer.parseInt(String.valueOf(nextLargest));
            maxJoltages.add(joltage);
        }

        return maxJoltages;
    }

    public static ArrayList<Integer> findMaxJoltagesFromBanksPart2(ArrayList<String> banks) {
        ArrayList<Integer> maxJoltages = new ArrayList<>();

        for (String s : banks) {
            ArrayDeque<Character> deque = new ArrayDeque<>();
            for (int i = s.length() - 1; i >= 0; i--) {
                if (deque.size() < 12) {
                    deque.addLast(s.charAt(i));
                } else if (s.charAt(i) > deque.getLast()) {

                }
            }
//            int joltage = Integer.parseInt(String.valueOf(largest)) * 10 + Integer.parseInt(String.valueOf(nextLargest));
//            maxJoltages.add(joltage);
        }

        return maxJoltages;
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

    public static int sumOfArrayList(ArrayList<Integer> list) {
        int sum = 0;
        for (int n : list) {
            sum += n;
        }
        return sum;
    }
}