/*
Jo Eun Kim
ICSI 311-0008
Assignment 1 - build and search B-Tree
 */
import java.util.*;
public class BTree_test {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter 1 to start new testing or -999 to quit testing: ");
        int toTest = input.nextInt();

        // run test until -999 entered
        while(toTest != -999) {
            RootNode root = buildTree(); // build a B-Tree according to the certain test data and user inputs

            int valueToSearch = 0;
            boolean isFound = false;

            if(root != null) {
                System.out.print("Enter a number to search in the B-tree or -999 to stop searching: ");
                valueToSearch = input.nextInt();
                while (valueToSearch != -999) { // take the user input for the value to search until -999 entered
                    isFound = searchTree(root.nodes, valueToSearch);
                    if (isFound) {
                        System.out.print(valueToSearch + " is found. ");
                    } else {
                        System.out.print(valueToSearch + " is not found. ");
                    }
                    // ask user again for another value to search
                    System.out.print("Enter a number to search in the B-tree or -999 to stop searching: ");
                    valueToSearch = input.nextInt();
                }
            }
            // ask user again whether to test with another dataset or not
            System.out.print("Enter 1 to start new testing or -999 to quit testing: ");
            toTest = input.nextInt();
        }
    }

    /* method to build a B-Tree according to the certain test data and user inputs
        In this program, we will have 4 test cases given in the class.
     */
    private static RootNode buildTree() {
        Scanner input = new Scanner(System.in);
        RootNode root = new RootNode(1, 1000, new ArrayList<Node>()); // top-most root(1-1000)
        RootNode rootOne, rootTwo, rootThree, rootFour, rootFive;
        LeafNode leafOne, leafTwo, leafThree, leafFour, leafFive;

        int min, max; // range of root node
        int num = 0; // to take user input for the value of leafnode
        Integer[] values = {}; // array of Integer to hold multiple values

        /* we have 4 test cases given in total for this assignment
        *   seperated each case to setup for RootNode(s) */
        System.out.print("Enter test #: ");
        int testNumber = input.nextInt();
        switch (testNumber) {
            // case 1 is designed for the example B-Tree diagram in the assignment instruction.
            case 1:
                // there are 3 RootNodes as below
                rootOne = new RootNode(100, 199, new ArrayList<Node>());
                rootTwo = new RootNode(200, 399, new ArrayList<Node>());
                rootThree = new RootNode(300, 399, new ArrayList<Node>());

                // take user inputs in order to add values into LeafNode
                min = 100; max = 199;
                System.out.println("Start setting for RootNode(" + min + " - " + max + ")...");
                values = takeInput(num, values, min, max);
                // add: assign the collection of the inputs(values) to LeafNode
                leafOne = new LeafNode(new ArrayList<>(Arrays.asList(values)));
                // add: connects the leafnode to the right rootnode
                rootOne.addNode(leafOne);

                min = 200; max = 399;
                System.out.println("Start setting for RootNode(" + min + " - " + max + ")...");
                values = takeInput(num, values, min, max);
                leafTwo = new LeafNode(new ArrayList<>(Arrays.asList(values)));
                rootTwo.addNode(leafTwo);

                min = 300; max = 399;
                System.out.println("Start setting for RootNode(" + min + " - " + max + ")...");
                values = takeInput(num, values, min, max);
                leafThree = new LeafNode(new ArrayList<>(Arrays.asList(values)));
                rootThree.addNode(leafThree);
                // connects rootnode to another rootnode
                rootTwo.addNode(rootThree);
                // connects rootnode(s) to the top root
                root.addNode(rootOne);
                root.addNode(rootTwo);
                break;
            // case 2 is designed for the Test Case 1 in the TestDataProgramming1Assignment.pdf
            case 2:
                rootOne = new RootNode(1, 100, new ArrayList<Node>());
                rootTwo = new RootNode(101, 200, new ArrayList<Node>());
                rootThree = new RootNode(201, 400, new ArrayList<Node>());
                rootFour = new RootNode(401, 500, new ArrayList<Node>());
                rootFive = new RootNode(501, 1000, new ArrayList<Node>());

                min = 1; max = 100;
                System.out.println("Start setting for RootNode(" + min + " - " + max + ")...");
                values = takeInput(num, values, min, max);
                leafOne = new LeafNode(new ArrayList<>(Arrays.asList(values)));
                min = 101; max = 200;
                System.out.println("Start setting for RootNode(" + min + " - " + max + ")...");
                values = takeInput(num, values, min, max);
                leafTwo = new LeafNode(new ArrayList<>(Arrays.asList(values)));
                min = 201; max = 400;
                System.out.println("Start setting for RootNode(" + min + " - " + max + ")...");
                values = takeInput(num, values, min, max);
                leafThree = new LeafNode(new ArrayList<>(Arrays.asList(values)));
                min = 401; max = 500;
                System.out.println("Start setting for RootNode(" + min + " - " + max + ")...");
                values = takeInput(num, values, min, max);
                leafFour = new LeafNode(new ArrayList<>(Arrays.asList(values)));
                min = 501; max = 1000;
                System.out.println("Start setting for RootNode(" + min + " - " + max + ")...");
                values = takeInput(num, values, min, max);
                leafFive = new LeafNode(new ArrayList<>(Arrays.asList(values)));

                rootOne.addNode(leafOne);
                rootTwo.addNode(leafTwo);
                rootThree.addNode(leafThree);
                rootFour.addNode(leafFour);
                rootFive.addNode(leafFive);

                root.addNode(rootOne);
                root.addNode(rootTwo);
                root.addNode(rootThree);
                root.addNode(rootFour);
                root.addNode(rootFive);

                break;
            // case 3 is designed for the Test Case 2 in the TestDataProgramming1Assignment.pdf
            case 3:
                rootOne = new RootNode(1, 100, new ArrayList<Node>());
                rootTwo = new RootNode(101, 200, new ArrayList<Node>());
                rootThree = new RootNode(201, 1000, new ArrayList<Node>());

                min = 1; max = 100;
                System.out.println("Start setting for RootNode(" + min + " - " + max + ")...");
                values = takeInput(num, values, min, max);
                leafOne = new LeafNode(new ArrayList<>(Arrays.asList(values)));
                min = 101; max = 200;
                System.out.println("Start setting for RootNode(" + min + " - " + max + ")...");
                values = takeInput(num, values, min, max);
                leafTwo = new LeafNode(new ArrayList<>(Arrays.asList(values)));
                min = 201; max = 1000;
                System.out.println("Start setting for RootNode(" + min + " - " + max + ")...");
                values = takeInput(num, values, min, max);
                leafThree = new LeafNode(new ArrayList<>(Arrays.asList(values)));

                rootOne.addNode(leafOne);
                rootTwo.addNode(leafTwo);
                rootThree.addNode(leafThree);

                root.addNode(rootOne);
                root.addNode(rootTwo);
                root.addNode(rootThree);

                break;
            // case 4 is designed for the Test Case 3 in the TestDataProgramming1Assignment.pdf
            case 4:
                rootOne = new RootNode(1, 75, new ArrayList<Node>());
                rootTwo = new RootNode(76, 301, new ArrayList<Node>());
                rootThree = new RootNode(302, 525, new ArrayList<Node>());
                rootFour = new RootNode(526, 800, new ArrayList<Node>());
                rootFive = new RootNode(801, 1000, new ArrayList<Node>());

                min = 1; max = 75;
                System.out.println("Start setting for RootNode(" + min + " - " + max + ")...");
                values = takeInput(num, values, min, max);
                leafOne = new LeafNode(new ArrayList<>(Arrays.asList(values)));
                min = 76; max = 301;
                System.out.println("Start setting for RootNode(" + min + " - " + max + ")...");
                values = takeInput(num, values, min, max);
                leafTwo = new LeafNode(new ArrayList<>(Arrays.asList(values)));
                min = 302; max = 525;
                System.out.println("Start setting for RootNode(" + min + " - " + max + ")...");
                values = takeInput(num, values, min, max);
                leafThree = new LeafNode(new ArrayList<>(Arrays.asList(values)));
                min = 526; max = 800;
                System.out.println("Start setting for RootNode(" + min + " - " + max + ")...");
                values = takeInput(num, values, min, max);
                leafFour = new LeafNode(new ArrayList<>(Arrays.asList(values)));
                min = 801; max = 1000;
                System.out.println("Start setting for RootNode(" + min + " - " + max + ")...");
                values = takeInput(num, values, min, max);
                leafFive = new LeafNode(new ArrayList<>(Arrays.asList(values)));

                rootOne.addNode(leafOne);
                rootTwo.addNode(leafTwo);
                rootThree.addNode(leafThree);
                rootFour.addNode(leafFour);
                rootFive.addNode(leafFive);

                root.addNode(rootOne);
                root.addNode(rootTwo);
                root.addNode(rootThree);
                root.addNode(rootFour);
                root.addNode(rootFive);

                break;
            default: // ignore all the other case requested, return null
                System.out.println("Only 4 cases available.");
                root = null;
        }
        return root;
    }

    /* SEARCHING consists of two different methods: searchTree() and searchValue() */
    /* recursively look up the all nodes in the tree
        keeps searching from right to left and bottom to top (backward) */
    private static boolean searchTree(Collection<Node> nodes, int target) {
        if(nodes.size() == 0) { // empty. nothing to search
            return false;
        }
        ArrayList<Node> n = new ArrayList<Node>(nodes);
        Node currentNode = n.remove(n.size()-1); // to compare with the LAST-NODE(child(ren)) of the passed node first.

        if(!currentNode.isRoot) { // it is a leafnode
            if(searchValue(currentNode.values, target)) { // compare target number with all values in the leafnode
                return true;
            }
        } else { // it is a rootnode. check if there are other nodes under it. (to the next level to start searching from the bottom)
            return searchTree(currentNode.nodes, target);
        }
        // go back to the previous node(to left), which would be the new LAST-NODE in this function call since the original last node was removed
        return searchTree(n, target);
    }

    // recursively search a target number in 'values' of a LeafNode
    private static boolean searchValue(Collection<Integer> values, int target) {
        if(values.size() == 0) { // is empty. nothing to search
            return false;
        } else {
            ArrayList<Integer> numebrs = new ArrayList<Integer>(values);
            Integer numberToCompare = numebrs.remove(numebrs.size()-1);
            if(numberToCompare == target) {
                return true;
            } else {
                return searchValue(numebrs, target);
            }
        }
    }

    /* helper function to add an element to an array
        returns the updated array */
    public static Integer[] add_number(int size, Integer[] myArray, int value) {
        Integer[] newArray = new Integer[size+1]; // create a new array with +1 size of passed array
        // copy the passed array to the new array
        for(int i=0; i<size; i++) {
            newArray[i] = myArray[i];
        }
        // add the passed value at the last index(==size)
        newArray[size] = value;
        return newArray;
    }

    /* helper function to take user input for the values of current leafnode
        returns the list of values entered */
    public static Integer[] takeInput(int num, Integer[] values, int min, int max) {
        Scanner input = new Scanner(System.in);
        while(num != -999) {
            System.out.print("Enter a number for the value of LeafNode or -999 to end: ");
            num = input.nextInt();
            if(num >= min && num <= max) { // add num to the array only if num is in range
                values = add_number(values.length, values, num);
            } else if(num == -999){
                // not to print "Invalid input" for loop terminating signal -999
            }else { // out of range
                System.out.println("Invalid input.");
            }
        }
        return values;
    }
}
