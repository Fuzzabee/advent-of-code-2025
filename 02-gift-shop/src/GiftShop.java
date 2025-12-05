/***
 * https://adventofcode.com/2025/day/2
 ***/

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

class Constants {
    public static final String PUZZLE_INPUT = "PuzzleInput.txt";
    public static final String TEST_INPUT = "TestInput.txt";
}

public class GiftShop {
    public static void main(String[] args) {
//        String idRanges = readFile(Constants.TEST_INPUT);
        String idRanges = readFile(Constants.PUZZLE_INPUT);

        long part1StartTime = System.currentTimeMillis();
        ArrayList<Long> validIDPart1 = findValidIDPart1BruteForce(idRanges);
        long sumOfIDs = sumOfArrayList(validIDPart1);
        long part1EndTime = System.currentTimeMillis();
        long part1Time = (part1EndTime - part1StartTime);
        System.out.printf("Sum of ID's for Part 1: %d\n", sumOfIDs);
        System.out.printf("Part 1 run time: %dms\n\n", part1Time);

//        long part2StartTime = System.currentTimeMillis();
//        int passwordPart2 = findDoorPasswordPart2(rotations);
//        long part2EndTime = System.currentTimeMillis();
//        long part2Time = (part2EndTime - part2StartTime);
//        System.out.printf("Password for Part 2: %d\n", passwordPart2);
//        System.out.printf("Part 2 run time: %dms\n", part2Time);
    }

    public static String readFile(String filename) {
        File file = new File(filename);

        try (Scanner fileReader = new Scanner(file)) {
            return fileReader.nextLine();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        return null;
    }

    public static ArrayList<Long> findValidIDPart1BruteForce(String idRanges) {
        String[] idRangesSplit = idRanges.split(",");
        ArrayList<Long> validIDs = new ArrayList<>();

        for (String s : idRangesSplit) {
            String[] limits = s.split("-");

            if (!hasPotentialValidID(limits[0], limits[1])) {
//                System.out.printf("No valid ID (%s - %s)\n", limits[0], limits[1]);
                continue;
            }

            long lowerLimit = Long.parseLong(limits[0]);
            long upperLimit = Long.parseLong(limits[1]);
            for (long i = lowerLimit; i <= upperLimit; i++) {
//                System.out.printf("working on %d... ", i);
                String asString = Long.toString(i);
                if (asString.length() % 2 != 0 || i < 10) { continue; }
                int halfwayPoint = asString.length() / 2;

                boolean isValid = true;
                for (int j = 0; j < halfwayPoint; j++) {
                    if (asString.charAt(j) != asString.charAt(j + halfwayPoint)) {
                        isValid = false;
                        break;
                    }
                }
                if (isValid) {
//                    System.out.printf("found %d\n", i);
                    validIDs.add(i);
                }
            }
        }

        return validIDs;
    }

    public static ArrayList<Integer> findValidIDPart1(String idRanges) {
        String[] idRangesSplit = idRanges.split(",");
        ArrayList<Integer> validIDs = new ArrayList<>();

        for (String s : idRangesSplit) {
            String[] limits = s.split("-");
            String lowString = limits[0];
            int lowLength = lowString.length();
            long lowLong = Long.parseLong(lowString);
            String highString = limits[1];
            int highLength = highString.length();
            long highLong = Long.parseLong(highString);

            if (!hasPotentialValidID(lowString, highString)) {
                System.out.printf("No valid ID (%s - %s)\n", lowString, highString);
                continue;
            }

            if (lowLength % 2 != 0) {
                System.out.printf("Skipping %s ", lowString);
            } else {
                System.out.printf("Working %s ", lowString);
                if (lowLength == highLength) {
                    System.out.printf("Same Size ");
                    int lowPrefix = Integer.parseInt(lowString.substring(0, lowLength / 2));
                    int highPrefix = Integer.parseInt(highString.substring(0, highLength / 2));
                    if (lowPrefix == highPrefix) {
                        long potentialID = Long.parseLong(lowString.substring(0, lowLength / 2) + lowString.substring(0, lowLength / 2));
                        if (potentialID >= lowLong && potentialID <= highLong) {
                            System.out.printf("found %d ", potentialID);
                        }
                    }
                    System.out.printf("Pre = %d - %d ", lowPrefix, highPrefix);
                }
            }
            System.out.println();
            if (highLength % 2 != 0) {
                System.out.printf("Skipping %s ", highString);
            } else {
                System.out.printf("Working %s ", highString);
            }
            System.out.println();
        }
        return validIDs;
    }

    public static boolean hasPotentialValidID(String low, String high) {
        return low.length() % 2 == 0 || high.length() % 2 == 0 ||
                (Math.abs(low.length() - high.length()) % 2 == 0 && low.length() != high.length());
    }

    public static long sumOfArrayList(ArrayList<Long> list) {
        long sum = 0;
        for (long n : list) {
            sum += n;
        }
        return sum;
    }
}