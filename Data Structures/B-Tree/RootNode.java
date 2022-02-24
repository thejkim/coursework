/*
Jo Eun Kim
ICSI 311-0008
Assignment 1 - build and search B-Tree
 */
import java.util.*;
public class RootNode extends Node{
    public RootNode(int min, int max, Collection<Node> nodes) {
        this.isRoot = true; // always true
        this.values = null; // always null since RootNode only has child node(s)
        this.nodes = nodes;
        this.min = min;
        this.max = max;
    }
}
