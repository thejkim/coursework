/*
Jo Eun Kim
ICSI 311-0008
Assignment 1 - build and search B-Tree
 */
import java.util.*;
public class LeafNode extends Node{
    public LeafNode(Collection<Integer> values) {
        this.isRoot = false; // always false since not using for leafnode
        this.values = values;
        this.nodes = null; // always null since LeafNode has no child node(s)
        int min = 0; // not using for leafnode
        int max = 0; // not using
    }
}
