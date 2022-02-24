/*
Jo Eun Kim
ICSI 311-0008
Assignment 1 - build and search B-Tree
 */
import java.util.Collection;
// base class Node for LeafNode and RootNode
public class Node {
    boolean isRoot; // to indicate the type of node
    Collection<Integer> values; // collection of numbers for LeafNode
    Collection<Node> nodes; // collection of nodes(can have both types of node) for RootNode
    int min; // for range of RootNode
    int max; // for range of RootNode

    public Node() { // initialize
        this.isRoot = false;
        this.values = null;
        this.nodes = null;
        this.min = 0;
        this.max = 0;
    }

    // to connect nodes
    public void addNode(Node node) {
        this.nodes.add(node);
    }
}