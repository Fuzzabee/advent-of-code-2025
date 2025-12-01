/***
 * https://adventofcode.com/2025/day/1
 ***/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Constants {
    public static final String PUZZLE_INPUT = "PuzzleInput.txt";
    public static final String TEST_INPUT = "TestInput.txt";
}

public class SecretEntrance {
    public static void main(String[] args) {
//        ArrayList<String> rotations = readFile(Constants.TEST_INPUT);
        ArrayList<String> rotations = readFile(Constants.PUZZLE_INPUT);

        long part1StartTime = System.currentTimeMillis();
        int passwordPart1 = findDoorPasswordPart1(rotations);
        long part1EndTime = System.currentTimeMillis();
        long part1Time = (part1EndTime - part1StartTime);
        System.out.printf("Password for Part 1: %d\n", passwordPart1);
        System.out.printf("Part 1 run time: %dms\n\n", part1Time);

        long part2StartTime = System.currentTimeMillis();
        int passwordPart2 = findDoorPasswordPart2(rotations);
        long part2EndTime = System.currentTimeMillis();
        long part2Time = (part2EndTime - part2StartTime);
        System.out.printf("Password for Part 2: %d\n", passwordPart2);
        System.out.printf("Part 2 run time: %dms\n", part2Time);
    }

    public static ArrayList<String> readFile(String filename) {
        ArrayList<String> results = new ArrayList<>();
        File file = new File(filename);

        try (Scanner fileReader = new Scanner(file)) {
            while (fileReader.hasNextLine()) {
                results.add(fileReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        return results;
    }

    public static int findDoorPasswordPart1(ArrayList<String> rotations) {
        int dial = 50;
        int numZeroes = 0;

        for (String rotation : rotations) {
            String[] splitRotation = splitRotation(rotation);
            String direction = splitRotation[0];
            int amount = Integer.parseInt(splitRotation[1]);

            if (direction.equals("R")) {
                dial += amount;
            } else if (direction.equals("L")) {
                dial -= amount;
            } else {
                System.out.printf("Invalid instruction \"%s\", expected \"L\" or \"R\"", direction);
            }

            if (dial > 99) {
                dial = dial % 100;
            } else if (dial < 0) {
                dial = Math.floorMod(dial, 100);
            }

            if (dial == 0) {
                numZeroes++;
            }
        }

        return numZeroes;
    }

    public static int findDoorPasswordPart2(ArrayList<String> rotations) {
        int dial = 50;
        int numZeroes = 0;

        for (String rotation : rotations) {
            boolean dialWasZero = dial == 0;
            String[] splitRotation = splitRotation(rotation);
            String direction = splitRotation[0];
            int amount = Integer.parseInt(splitRotation[1]);

            if (direction.equals("R")) {
                dial += amount;
            } else if (direction.equals("L")) {
                dial -= amount;
            } else {
                System.out.printf("Invalid instruction \"%s\", expected \"L\" or \"R\"", direction);
            }

            if (dial > 99) {
                numZeroes += dial / 100;
                dial = dial % 100;
            } else if (dial < 0) {
                if (!dialWasZero) {
                    numZeroes += (-1 * (dial / 100)) + 1;
                } else if (amount > 99) {
                    numZeroes += -1 * (dial / 100);
                }
                dial = Math.floorMod(dial, 100);
            } else if (dial == 0) {
                numZeroes += 1;
            }

        }
        return numZeroes;
    }

    public static String[] splitRotation(String rotation) {
        return new String[] {rotation.substring(0, 1), rotation.substring(1)};
    }
}