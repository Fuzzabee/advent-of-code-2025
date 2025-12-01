import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Constants {
    public static final String TEST_INPUT = "TestInput.txt";
    public static final String PUZZLE_INPUT = "PuzzleInput.txt";
}

public class SecretEntrance {
    public static void main(String[] args) {
//        ArrayList<String> rotations = readFile(Constants.TEST_INPUT);
        ArrayList<String> rotations = readFile(Constants.PUZZLE_INPUT);

        int passwordPart1 = findDoorPasswordPart1(rotations);
        System.out.printf("Password for Part 1: %d\n", passwordPart1);
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
            int amount = Integer.parseInt(splitRotation[1]);

            if (splitRotation[0].equals("R")) {
                dial += amount;
            } else if (splitRotation[0].equals("L")) {
                dial -= amount;
            } else {
                System.out.printf("Invalid instruction \"%s\", expected \"L\" or \"R\"", splitRotation[0]);
            }

            if (dial > 99 || dial < 0) {
                dial = dial % 100;
            }

            if (dial == 0) {
                numZeroes++;
            }
        }

        return numZeroes;
    }

    public static String[] splitRotation(String rotation) {
        return new String[] {rotation.substring(0, 1), rotation.substring(1)};
    }
}