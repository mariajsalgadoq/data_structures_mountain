package project5;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a rest stop in the mountain climbing simulation.
 * A rest stop can have a label, supplies, and obstacles.
 * 
 * @version 1.0
 * @author Majo Salgado
 */
public class RestStop implements Comparable<RestStop> {
    private String label;
    private List<String> supplies = new ArrayList<>();
    private List<String> obstacles = new ArrayList<>();

    /**
     * Constructs a new RestStop with the specified label.
     * 
     * @param label the label of the rest stop
     */
    public RestStop(String label) {
        this.label = label;
    }

    /**
     * Adds a supply to the rest stop.
     * 
     * @param supply the supply to add
     */
    public void addSupply(String supply) {
        this.supplies.add(supply);
    }

    /**
     * Adds an obstacle to the rest stop.
     * 
     * @param obstacle the obstacle to add
     */
    public void addObstacle(String obstacle) {
        this.obstacles.add(obstacle);
    }

    /**
     * Returns the label of the rest stop.
     * 
     * @return the label of the rest stop
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the list of supplies available at the rest stop.
     * 
     * @return the list of supplies
     */
    public List<String> getSupplies() {
        return supplies;
    }

    /**
     * Returns the list of obstacles present at the rest stop.
     * 
     * @return the list of obstacles
     */
    public List<String> getObstacles() {
        return obstacles;
    }

    /**
     * Compares this rest stop to another rest stop based on their labels.
     * 
     * @param other the other rest stop to compare to
     * @return a negative integer, zero, or a positive integer as this rest stop's label
     *         is less than, equal to, or greater than the specified rest stop's label
     */
    @Override
    public int compareTo(RestStop other) {
        return this.label.compareTo(other.label);
    }

    /**
     * Returns a string representation of the rest stop.
     * 
     * @return a string representation of the rest stop
     */
    @Override
    public String toString() {
        return "RestStop{" +
               "label='" + label + '\'' +
               ", supplies=" + supplies +
               ", obstacles=" + obstacles +
               '}';
    }
}
