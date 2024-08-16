package project5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The MountainClimb class is the entry point for the mountain climbing simulation.
 * It loads mountain data from an input file and initiates the exploration of paths
 * down the mountain using a BSTMountain.
 * 
 * @version 1.0
 * @author Majo Salgado
 */
public class MountainClimb {

    /**
     * Main method to run the mountain climbing simulation.
     * 
     * @param args command line arguments, expects a single argument specifying the input file name
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java MountainClimb <inputFileName>");
            System.exit(1);
        }

        String inputFileName = args[0];
        File inputFile = new File(inputFileName);

        BSTMountain mountain = new BSTMountain();
        try {
            loadMountainFromFile(mountain, inputFile);
            mountain.explore();
        } catch (FileNotFoundException e) {
            System.err.println("Error: The file '" + inputFileName + "' was not found.");
            System.exit(1);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Loads mountain data from the given file into the BSTMountain.
     * 
     * @param mountain The BSTMountain instance to populate.
     * @param file The file to read from.
     * @throws FileNotFoundException if the specified file does not exist.
     */
    private static void loadMountainFromFile(BSTMountain mountain, File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.trim().isEmpty()) {
                parseLineAndAddToMountain(mountain, line.trim());
            }
        }
        scanner.close();
    }

    /**
     * Parses a single line from the input file and adds the rest stop to the mountain.
     * 
     * @param mountain The BSTMountain to add the rest stop to.
     * @param line The line to parse.
     */
    private static void parseLineAndAddToMountain(BSTMountain mountain, String line) {
        String[] parts = line.trim().split("\\s+", 2);
        if (parts.length == 0) return;

        String label = parts[0];
        RestStop restStop = new RestStop(label);

        if (parts.length > 1) {
            String details = parts[1];
            Pattern pattern = Pattern.compile("food|raft|axe|fallen tree|river");
            Matcher matcher = pattern.matcher(details);

            boolean foundObstacle = false;
            while (matcher.find()) {
                String match = matcher.group();
                if (isObstacle(match)) {
                    foundObstacle = true;
                    restStop.addObstacle(match);
                } else if (!foundObstacle && isSupply(match)) {
                    restStop.addSupply(match);
                }
            }
        }
        mountain.add(restStop);
    }

    /**
     * Checks if the given item is a supply.
     * 
     * @param item the item to check
     * @return true if the item is a supply, false otherwise
     */
    private static boolean isSupply(String item) {
        return item.equals("food") || item.equals("raft") || item.equals("axe");
    }

    /**
     * Checks if the given item is an obstacle.
     * 
     * @param item the item to check
     * @return true if the item is an obstacle, false otherwise
     */
    private static boolean isObstacle(String item) {
        return item.equals("fallen tree") || item.equals("river");
    }
}
