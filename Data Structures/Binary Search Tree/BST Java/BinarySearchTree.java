/*
Jo Eun Kim
ICSI 311-0008
Assignment 4 - Binary Search Tree
 */
public class BinarySearchTree {
    Node root = null; // root node

    // Method to add a new node into the BST
    public void add(int value) {
        // Create a new node and initialize it
        Node newNode = new Node(value);

        if (root == null) { // Empty tree. newNode becomes root
            newNode.key = "Root";
            newNode.parent = null;
            root = newNode;
        } else {
            // Set root as the node we will start with as we traverse the tree
            Node currentNode = root;
            Node parentNode = null;

            while (true) {
                parentNode = currentNode; // start from root(==currentNode)

                if (value < parentNode.value) { // go left
                    currentNode = parentNode.left;
                    if (currentNode == null) { // if there's no child node
                        newNode.key = "LeftChild";
                        parentNode.left = newNode; // put newNode into the left of it
                        newNode.parent = parentNode;
                        break;
                    }
                } else { // go right
                    currentNode = parentNode.right;
                    if (currentNode == null) { // if there's no child node
                        newNode.key = "RightChild";
                        parentNode.right = newNode; // put newNode into the right of it
                        newNode.parent = parentNode;
                        break;
                    }
                }
            }
        }
    }

    // Method to remove a node from the tree
    public boolean remove(int value) {
        Node currentNode = root; // start at root(top of BST)
        Node parentNode = root;

        boolean isItALeftChild = true;

        // While we have not found the node, keep looking
        while (currentNode.value != value) {
            parentNode = currentNode;
            if (value < currentNode.value) {
                // value looking for is smaller than the current node value : go left
                isItALeftChild = true;
                currentNode = currentNode.left;
            } else { // go right
                // value looking for is bigger than the current node value : go right
                isItALeftChild = false;
                currentNode = currentNode.right;
            }
            if (currentNode == null) // was not found
                return false;
        }

        // If the node doesn't have children, delete it
        if (currentNode.left == null && currentNode.right == null) {
            if (currentNode == root) // if root, delete it
                root = null;
            else if (isItALeftChild) // if it's a left child of the parent, delete it in it's parent
                parentNode.left = null;
            else // if it's a right child of the parent, delete it in it's parent
                parentNode.right = null;
        } else if (currentNode.right == null) { // if no right child
            if (currentNode == root)
                root = currentNode.left;
            else if (isItALeftChild)
                parentNode.left = currentNode.left;
            else
                parentNode.right = currentNode.left;
        } else if (currentNode.left == null) { // if no left child
            if (currentNode == root)
                root = currentNode.right;
            else if (isItALeftChild)
                parentNode.left = currentNode.right;
            else
                parentNode.right = currentNode.right;
        } else { // there are two children so we need to find the deleted nodes replacement
            Node replacement = getReplacementNode(currentNode);

            if (currentNode == root) // replace root with the replacement
                root = replacement;
            else if (isItALeftChild) // deleted node was a left child
                parentNode.left = replacement; // make the replacement the left child
            else // deleted node was a right child
                parentNode.right = replacement; // make the replacement the right child

            replacement.left = currentNode.left;
        }
        return true;
    }

    // Helper function used in remove function
    public Node getReplacementNode(Node replacedNode) {
        Node replacementParent = replacedNode;
        Node replacement = replacedNode;
        Node currentNode = replacedNode.right;

        while (currentNode != null) { // while there are no more left children
            replacementParent = replacement;
            replacement = currentNode;
            currentNode = currentNode.left;
        }

        // If the replacement isn't the right child
        // move the replacement into the parents
        // leftChild slot and move the replaced nodes
        // right child into the replacements rightChild
        if (replacement != replacedNode.right) {
            replacementParent.left = replacement.right;
            replacement.right = replacedNode.right;
        }

        return replacement;
    }

    // Helper function to search a node(value) in the BST
    public Node find(int value) {
        Node currentNode = root; // start at root (top of BST)

        while (currentNode.value != value) { // keep finding while not found
            if (value < currentNode.value) {
            // value looking for is smaller than the current node value : go left
                currentNode = currentNode.left;
            } else if (value > currentNode.value) {
            // value looking for is bigger than the current node value : go right
                currentNode = currentNode.right;
            }
            if (currentNode == null) // was not found
                break;
        }
        return currentNode;
    }
    /* Print BST in in-order, pre-order, post-order traversal
        - All nodes will be visited in ascending order
        - We will use recursion to go to one node and then go to its child nodes and so forth
     */
    public void printInOrder(Node currentNode) {
        if (currentNode != null) {
            printInOrder(currentNode.left); // traverse the left node
            System.out.println(currentNode.toString()); // print out the currently visited one
            printInOrder(currentNode.right); // traverse the right node
        }
    }
    public void printPreOrder(Node currentNode) {
        if (currentNode != null) {
            System.out.println(currentNode.toString());
            printPreOrder(currentNode.left);
            printPreOrder(currentNode.right);
        }
    }
    public void printPostOrder(Node currentNode) {
        if (currentNode != null) {
            printPostOrder(currentNode.left);
            printPostOrder(currentNode.right);
            System.out.println(currentNode.toString());
        }
    }
}
