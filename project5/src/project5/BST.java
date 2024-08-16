package project5;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Stack;

/**
 * A binary search tree (BST) implementation that supports generic types.
 * Provides operations for adding, removing, and querying elements,
 * as well as iterators for different traversal orders.
 * 
 * @param <E> the type of elements in this BST, which must be comparable
 * @version 1.0
 * @author Majo Salgado
 */
public class BST<E extends Comparable<E>> implements Iterable<E> {
    protected Node root;
    private int size = 0;

    /**
     * Constructs an empty BST.
     */
    public BST() {
    }

    /**
     * Constructs a BST from an array of elements. The resulting BST is balanced.
     * 
     * @param elements an array of elements to add to the BST
     */
    public BST(E[] elements) {
        root = buildBalancedTree(elements, 0, elements.length - 1);
        size = elements.length;
    }

    private Node buildBalancedTree(E[] elements, int start, int end) {
        if (start > end) {
            return null;
        }
        int mid = (start + end) / 2;
        Node node = new Node(elements[mid]);
        node.left = buildBalancedTree(elements, start, mid - 1);
        node.right = buildBalancedTree(elements, mid + 1, end);
        updateHeight(node);
        updateSize(node);
        return node;
    }

    /**
     * A node in the BST.
     */
    protected class Node {
        E value;
        Node left;
        Node right;
        int subtreeSize;
        int height;

        /**
         * Constructs a new node with the specified value.
         * 
         * @param value the value of the new node
         */
        public Node(E value) {
            this.value = value;
            this.left = null;
            this.right = null;
            this.subtreeSize = 1;
            this.height = 1;
        }

        /**
         * Returns the data of this node.
         * 
         * @return the data of this node
         */
        public E getData() {
            return value;
        }

        /**
         * Returns the left child of this node.
         * 
         * @return the left child of this node
         */
        public Node getLeft() {
            return left;
        }

        /**
         * Returns the right child of this node.
         * 
         * @return the right child of this node
         */
        public Node getRight() {
            return right;
        }
    }

    /**
     * Finds the node containing the specified element.
     * 
     * @param e the element to find
     * @param current the current node in the search
     * @return the node containing the element, or null if not found
     */
    private Node findNode(E e, Node current) {
        while (current != null) {
            int cmp = e.compareTo(current.value);
            if (cmp < 0) {
                current = current.left;
            } else if (cmp > 0) {
                current = current.right;
            } else {
                return current;
            }
        }
        return null;
    }

    /**
     * Finds the predecessor of the specified element.
     * 
     * @param e the element to find the predecessor of
     * @return the predecessor of the element, or null if no predecessor exists
     */
    public E predecessor(E e) {
        Node node = findNode(e, root);
        if (node == null) {
            return null;
        }
        if (node.left != null) {
            return max(node.left).value;
        }

        Node predecessor = null;
        Node ancestor = root;
        while (ancestor != node) {
            if (node.value.compareTo(ancestor.value) > 0) {
                predecessor = ancestor;
                ancestor = ancestor.right;
            } else {
                ancestor = ancestor.left;
            }
        }
        return (predecessor == null) ? null : predecessor.value;
    }

    /**
     * Finds the successor of the specified element.
     * 
     * @param e the element to find the successor of
     * @return the successor of the element, or null if no successor exists
     */
    public E successor(E e) {
        Node node = findNode(e, root);
        if (node == null) {
            return null;
        }
        if (node.right != null) {
            return min(node.right).value;
        }

        Node successor = null;
        Node ancestor = root;
        while (ancestor != node) {
            if (node.value.compareTo(ancestor.value) < 0) {
                successor = ancestor;
                ancestor = ancestor.left;
            } else {
                ancestor = ancestor.right;
            }
        }
        return (successor == null) ? null : successor.value;
    }

    /**
     * Prints the tree structure.
     */
    public void printTree() {
        printNode(root);
    }

    private void printNode(Node node) {
        if (node == null) return;
        System.out.print(node.value + " -> ");
        if (node.left != null) System.out.print("L:" + node.left.value + " ");
        if (node.right != null) System.out.print("R:" + node.right.value + " ");
        System.out.println();
        printNode(node.left);
        printNode(node.right);
    }

    /**
     * Adds the specified element to the BST.
     * 
     * @param e the element to add
     * @return true if the element was added, false if it was already present
     */
    public boolean add(E e) {
        if (e == null) throw new NullPointerException("Cannot add null to the BST");
        if (!contains(e)) {
            root = add(root, e);
            size++;
            return true;
        }
        return false;
    }

    private Node add(Node node, E e) {
        if (node == null) {
            return new Node(e);
        }
        int cmp = e.compareTo(node.value);
        if (cmp < 0) {
            node.left = add(node.left, e);
        } else if (cmp > 0) {
            node.right = add(node.right, e);
        } else {
            return node; // Duplicate value, do not insert
        }
        
        updateHeight(node);
        updateSize(node);
        return node;
    }

    /**
     * Removes the specified element from the BST.
     * 
     * @param o the element to remove
     * @return true if the element was removed, false if it was not found
     */
    public boolean remove(Object o) {
        if (o == null) {
            throw new NullPointerException("Cannot remove null from the BST");
        }
        if (!(o instanceof Comparable)) {
            throw new ClassCastException("Cannot compare object of type " + o.getClass().getName());
        }
        if (contains(o)) {
            root = remove(root, (E) o);
            size--;
            return true;
        }
        return false;
    }

    private Node remove(Node node, E e) {
        if (node == null) return null;

        int cmp = e.compareTo(node.value);
        if (cmp < 0) {
            node.left = remove(node.left, e);
        } else if (cmp > 0) {
            node.right = remove(node.right, e);
        } else {
            if (node.left == null || node.right == null) {
                Node temp = (node.left != null) ? node.left : node.right;
                if (temp == null) {
                    temp = node;
                    node = null;
                } else {
                    node = temp;
                }
            } else {
                Node temp = min(node.right);
                node.value = temp.value;
                node.right = remove(node.right, temp.value);
            }
        }

        if (node == null) return node;

        updateHeight(node);
        updateSize(node);

        return node;
    }

    /**
     * Clears the BST, removing all elements.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Checks if the BST contains the specified element.
     * 
     * @param o the element to check for
     * @return true if the element is found, false otherwise
     */
    public boolean contains(Object o) {
        if (o == null) throw new NullPointerException("BST does not permit null elements");
        if (!(o instanceof Comparable)) throw new ClassCastException("Object of type " + o.getClass().getName() + " cannot be compared.");
        return contains(root, (E) o);
    }

    private boolean contains(Node node, E e) {
        while (node != null) {
            int cmp = e.compareTo(node.value);
            if (cmp < 0) node = node.left;
            else if (cmp > 0) node = node.right;
            else return true;
        }
        return false;
    }

    /**
     * Returns the size of the BST.
     * 
     * @return the number of elements in the BST
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the BST is empty.
     * 
     * @return true if the BST is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the height of the BST. This operation is O(1).
     * 
     * @return the height of the BST
     */
    public int height() {
        return (root == null) ? 0 : root.height;
    }

    private void updateHeight(Node node) {
        if (node != null) {
            node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        }
    }

    private int getHeight(Node node) {
        return (node == null) ? 0 : node.height;
    }

    private void updateSize(Node node) {
        if (node != null) {
            int oldSize = node.subtreeSize;
            int newSize = 1 + size(node.left) + size(node.right);
            if (newSize != oldSize) {
                node.subtreeSize = newSize;
            }
        }
    }

    private int size(Node node) {
        return node == null ? 0 : node.subtreeSize;
    }

    @Override
    public Iterator<E> iterator() {
        return new BSTIterator(root);
    }

    private class BSTIterator implements Iterator<E> {
        private Stack<Node> stack;

        public BSTIterator(Node root) {
            stack = new Stack<>();
            pushAll(root);
        }

        private void pushAll(Node node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            Node current = stack.pop();
            E data = current.value;
            pushAll(current.right);
            return data;
        }
    }

    public Iterator<E> preorderIterator() {
        return new PreorderIterator(root);
    }

    private class PreorderIterator implements Iterator<E> {
        private Stack<Node> stack;

        public PreorderIterator(Node root) {
            stack = new Stack<>();
            if (root != null) {
                stack.push(root);
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            Node current = stack.pop();
            E data = current.value;
            if (current.right != null) {
                stack.push(current.right);
            }
            if (current.left != null) {
                stack.push(current.left);
            }
            return data;
        }
    }

    public Iterator<E> postorderIterator() {
        return new PostorderIterator(root);
    }

    private class PostorderIterator implements Iterator<E> {
        private Stack<Node> stack1;
        private Stack<Node> stack2;

        public PostorderIterator(Node root) {
            stack1 = new Stack<>();
            stack2 = new Stack<>();
            if (root != null) {
                stack1.push(root);
            }
            while (!stack1.isEmpty()) {
                Node node = stack1.pop();
                stack2.push(node);
                if (node.left != null) {
                    stack1.push(node.left);
                }
                if (node.right != null) {
                    stack1.push(node.right);
                }
            }
        }

        @Override
        public boolean hasNext() {
            return !stack2.isEmpty();
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            return stack2.pop().value;
        }
    }


    /**
     * Returns the element at the specified index.
     * 
     * @param index the index of the element to return
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return getElementAtIndex(root, index);
    }

    private E getElementAtIndex(Node node, int index) {
        if (node == null) return null;

        int leftSize = (node.left != null) ? node.left.subtreeSize : 0;
        if (index < leftSize) {
            return getElementAtIndex(node.left, index);
        } else if (index > leftSize) {
            return getElementAtIndex(node.right, index - leftSize - 1);
        } else {
            return node.value;
        }
    }

    /**
     * Returns the first (smallest) element currently in this tree.
     * 
     * @return the first (smallest) element currently in this tree
     * @throws NoSuchElementException if the BST is empty
     */
    public E first() {
        if (root == null) throw new NoSuchElementException("Set is empty");
        return min(root).value;
    }

    private Node min(Node node) {
        if (node == null) {
            throw new NoSuchElementException("No smaller element available");
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * Returns the last (largest) element currently in this tree.
     * 
     * @return the last (largest) element currently in this tree
     * @throws NoSuchElementException if the BST is empty
     */
    public E last() {
        if (root == null) throw new NoSuchElementException("Set is empty");
        return max(root).value;
    }

    private Node max(Node node) {
        if (node == null) {
            throw new NoSuchElementException("No larger element available");
        }
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BST<?> other = (BST<?>) obj;
        if (this.size != other.size) {
            return false;
        }
        Iterator<?> thisIter = this.iterator();
        Iterator<?> otherIter = other.iterator();

        while (thisIter.hasNext() && otherIter.hasNext()) {
            Object thisElem = thisIter.next();
            Object otherElem = otherIter.next();

            if (!thisElem.equals(otherElem)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        Iterator<E> it = iterator();
        if (!it.hasNext()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        while (it.hasNext()) {
            E e = it.next();
            sb.append(e == this ? "(this Collection)" : String.valueOf(e));
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(']');
        return sb.toString();
    }

    /**
     * Returns a string representation of the BST in tree format.
     * 
     * @return a string representation of the BST in tree format
     */
    public String toStringTreeFormat() {
        StringBuilder sb = new StringBuilder();
        toStringTreeFormat(sb, root, 0);
        return sb.toString();
    }

    private void toStringTreeFormat(StringBuilder sb, Node node, int level) {
        if (node == null) {
            appendIndent(sb, level);
            sb.append("null\n");
            return;
        }

        if (level > 0) {
            appendIndent(sb, level - 1);
            sb.append("|--");
        }
        sb.append(node.value + "\n");

        toStringTreeFormat(sb, node.left, level + 1);
        toStringTreeFormat(sb, node.right, level + 1);
    }

    private void appendIndent(StringBuilder sb, int level) {
        for (int i = 0; i < level; i++) {
            sb.append("   ");
        }
    }
}
