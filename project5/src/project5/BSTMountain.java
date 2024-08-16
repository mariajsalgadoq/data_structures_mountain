
package project5;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * BSTMountain class extends the BST class for RestStop elements.
 * It includes methods to explore and find successful paths down the mountain.
 * 
 * @version 1.0
 * @author Majo Salgado
 */
public class BSTMountain extends BST<RestStop> {
    private List<List<RestStop>> successfulPaths;

    /**
     * Constructs an empty BSTMountain.
     */
    public BSTMountain() {
        super();
        successfulPaths = new ArrayList<>();
    }

    /**
     * Explores all paths from the root to the leaves, collecting successful paths.
     */
    public void explore() {
        List<RestStop> currentPath = new ArrayList<>();
        List<String> collectedSupplies = new ArrayList<>();
        int maxDepth = calculateMaxDepth(root) - 1;

        explore(root, currentPath, collectedSupplies, 0, maxDepth);
        reportPaths();
    }

    /**
     * Recursively explores the tree from the given node.
     * 
     * @param node the current node
     * @param currentPath the current path being explored
     * @param collectedSupplies the supplies collected so far
     * @param currentDepth the current depth of the node
     * @param maxDepth the maximum depth of the tree
     */
    private void explore(Node node, List<RestStop> currentPath, List<String> collectedSupplies, int currentDepth, int maxDepth) {
        if (node == null) {
            return;
        }

        RestStop currentStop = node.getData();
        currentPath.add(currentStop);

        boolean isLeaf = (node.left == null && node.right == null);

        List<String> suppliesSnapshot = new ArrayList<>(collectedSupplies);
        collectedSupplies.addAll(currentStop.getSupplies());

        if (canSurvive(currentStop, collectedSupplies, isLeaf)) {
            if (isLeaf) {
                if (currentDepth == maxDepth) {
                    successfulPaths.add(new ArrayList<>(currentPath));
                }
            } else {
                explore(node.left, new ArrayList<>(currentPath), new ArrayList<>(collectedSupplies), currentDepth + 1, maxDepth);
                explore(node.right, new ArrayList<>(currentPath), new ArrayList<>(collectedSupplies), currentDepth + 1, maxDepth);
            }
        }

        collectedSupplies.clear();
        collectedSupplies.addAll(suppliesSnapshot);
        currentPath.remove(currentPath.size() - 1);
    }

    /**
     * Checks if the hiker can survive given the current supplies and obstacles at the rest stop.
     * 
     * @param stop the current rest stop
     * @param supplies the current supplies
     * @param isLeaf whether the current node is a leaf
     * @return true if the hiker can survive, false otherwise
     */
    private boolean canSurvive(RestStop stop, List<String> supplies, boolean isLeaf) {
        List<String> obstacles = stop.getObstacles();

        if (!isLeaf && !supplies.contains("food")) {
            return false;
        } else if (!isLeaf) {
            supplies.remove("food");
        }

        for (String obstacle : obstacles) {
            if (obstacle.equals("fallen tree") && supplies.contains("axe")) {
                supplies.remove("axe");
            } else if (obstacle.equals("fallen tree")) {
                return false;
            }

            if (obstacle.equals("river") && supplies.contains("raft")) {
                supplies.remove("raft");
            } else if (obstacle.equals("river")) {
                return false;
            }
        }

        return true;
    }

    /**
     * Calculates the maximum depth of the tree.
     * 
     * @param node the current node
     * @return the maximum depth of the tree
     */
    private int calculateMaxDepth(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(calculateMaxDepth(node.left), calculateMaxDepth(node.right));
    }

    /**
     * Reports all successful paths by printing them.
     */
    public void reportPaths() {
        for (List<RestStop> path : successfulPaths) {
            System.out.println(pathToString(path));
        }
    }

    /**
     * Converts a path to a string representation.
     * 
     * @param path the path to convert
     * @return the string representation of the path
     */
    private String pathToString(List<RestStop> path) {
        return path.stream()
                   .map(RestStop::getLabel)
                   .collect(Collectors.joining(" "));
    }
}
