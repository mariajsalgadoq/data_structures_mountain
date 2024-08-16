package project5;

/**
 * Represents a hiker in the mountain climbing simulation.
 * Manages the hiker's supplies and provides methods to collect and use supplies.
 * 
 * @version 1.0
 * @author Majo Salgado
 */
public class Hiker {
    private String name;
    private int foodCount;
    private int raftCount;
    private int axeCount;

    /**
     * Constructs a new Hiker with the specified name and initializes supply counts to zero.
     * 
     * @param name the name of the hiker
     */
    public Hiker(String name) {
        this.name = name;
        this.foodCount = 0;
        this.raftCount = 0;
        this.axeCount = 0;
    }

    /**
     * Adds supplies collected from a rest stop to the hiker's inventory.
     * 
     * @param supply the supply item to add (e.g., "food", "raft", "axe")
     * @param quantity the quantity of the supply to add
     */
    public void collectSupplies(String supply, int quantity) {
        switch (supply) {
            case "food":
                this.foodCount += quantity;
                break;
            case "raft":
                this.raftCount += quantity;
                break;
            case "axe":
                this.axeCount += quantity;
                break;
            default:
                System.out.println("Unknown supply encountered: " + supply);
                break;
        }
    }

    /**
     * Attempts to use supplies to overcome an obstacle.
     * 
     * @param obstacle the type of obstacle to overcome (e.g., "fallen tree", "river")
     * @return true if the hiker has the necessary supplies to overcome the obstacle, false otherwise
     */
    public boolean useSuppliesToOvercome(String obstacle) {
        switch (obstacle) {
            case "fallen tree":
                return useAxe();
            case "river":
                return useRaft();
            default:
                System.out.println("Unknown obstacle encountered: " + obstacle);
                return false;
        }
    }

    /**
     * Decreases the count of axes if available.
     * 
     * @return true if an axe was used successfully, false otherwise
     */
    private boolean useAxe() {
        if (axeCount > 0) {
            axeCount--;
            return true;
        }
        return false;
    }

    /**
     * Decreases the count of rafts if available.
     * 
     * @return true if a raft was used successfully, false otherwise
     */
    private boolean useRaft() {
        if (raftCount > 0) {
            raftCount--;
            return true;
        }
        return false;
    }

    /**
     * Returns a string representation of the hiker's current supplies.
     * 
     * @return a string representation of the hiker's current supplies
     */
    public String getSuppliesInfo() {
        return "Hiker{" +
               "name='" + name + '\'' +
               ", foodCount=" + foodCount +
               ", raftCount=" + raftCount +
               ", axeCount=" + axeCount +
               '}';
    }

    @Override
    public String toString() {
        return getSuppliesInfo();
    }
}

