/*
Jo Eun Kim
ICSI 311-0008
Assignment 4 - Binary Search Tree
 */
import java.util.Random;
public class BST_driver {
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        final int ARRAY_LENGTH = 20; // length of the array
        final int MIN_NUM = 1;
        final int MAX_NUM = 100;
        int randNum;
        Random rand = new Random(); // random number generator
        int arrayOfRandNums[] = new int[ARRAY_LENGTH]; // array of 20 integers

        int i=0; // index for existing elements in array
        // We are generating 20 "unique" integers
        boolean duplicated = false;
        while(true) {
            randNum = rand.nextInt(MAX_NUM - MIN_NUM + 1) + MIN_NUM; // random number in 1-100
            // Looking at the array until there's no more element to look at
            // We only need to keep looking at up to i th element
            for(int j=0; j<i; j++) {
                if(arrayOfRandNums[j] == randNum) {
                    duplicated = true;
                    break;
                }
            }
            if(!duplicated) { // take the randomly generated number(randNum) only if the same value is not already in the array
                arrayOfRandNums[i] = randNum;
                i++;
            }
            if(i == ARRAY_LENGTH && !duplicated) { // 20 unique integers taken
                break;
            }
            duplicated = false; // reset
        }
        // Add elements in array to BST
        for(int k=0; k<arrayOfRandNums.length; k++) {
            bst.add(arrayOfRandNums[k]);
            Log.println("arrayOfrandNums[" + k + "] = " +arrayOfRandNums[k]);
        }

        // Print out BST in in-order, pre-order, post-order
        Log.println("In Order");
        bst.printInOrder(bst.root);
        Log.println("\nPre Order");
        bst.printPreOrder(bst.root);
        Log.println("\nPost Order");
        bst.printPostOrder(bst.root);

        // Search a value in BST
        // I chose 7th element to verify find function works properly
        Log.println("\nFind " + arrayOfRandNums[7]);
        Node node = bst.find(arrayOfRandNums[7]);
        if (node != null)
            Log.println("Found :" + node.toString());
        else
            Log.println("Node not found");

        // Delete a node, using its value, from the BST
        // I chose 7th element to verify remove function works properly
        Log.println("\nRemove " + arrayOfRandNums[7]);
        Log.println(bst.remove(arrayOfRandNums[7])?"Removed":"Not removed");

        // Print out BST again to check if 7th element has removed
        Log.println("In Order");
        bst.printInOrder(bst.root);
        Log.println("\nPre Order");
        bst.printPreOrder(bst.root);
        Log.println("\nPost Order");
        bst.printPostOrder(bst.root);
    }
}
