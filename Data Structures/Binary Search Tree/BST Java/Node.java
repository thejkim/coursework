/*
Jo Eun Kim
ICSI 311-0008
Assignment 4 - Binary Search Tree
 */
public class Node {
    String key; // Root|LeftChild|RightChild
    int value;

    Node parent; // parent node
    Node left; // left child node
    Node right; // right child node

    // Constructor
    public Node(int value) {
        this.value = value;
    }

    // print out the node information (value, key, and its parent node's value)
    @Override
    public String toString() {
        return " [" + value + "] " + key + " of " + (parent==null?"null":parent.value);
    }
}
