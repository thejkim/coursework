/*
Jo Eun Kim
ICSI 311-0008
Assignment 4 - Binary Search Tree
 */
#include <iostream>
#include <stdlib.h>
#include <time.h>
#include <sstream>

using namespace std;

class Node {
public:
    string key;
    int data;
    Node *parent, *left, *right;
    // Constructor
    Node(int value) {
        data = value;
        parent = left = right = NULL;
    }

    string toString() {
        // Should return a string including text and variables
        ostringstream oss;
        oss << " [" << data << "] " << key;
        if(parent != NULL){
            oss << " of " << parent->data;
        }
        oss << "\n";

        string var = oss.str();
        return var;
    }
};

class BST
{
public:
    Node *root = NULL;
    // headers (class definitions)
    void Insert(int);
    bool Remove(int);
    Node* GetReplacementNode(Node*);
    Node* Find(int);
    void PrintInOrder(Node*);
    void PrintPreOrder(Node*);
    void PrintPostOrder(Node*);
    void FreeUp(Node*);
};


// Function to insert a new node into the BST
void BST ::Insert(int value)
{
    Node* newNode = new Node(value); // new Node instance

    if(root == NULL) {
        newNode->key = "Root";
        newNode->parent= NULL;
        root = newNode;
    } else {
        Node *currentNode = root;
        Node *parentNode = NULL;

        while(true) {
            parentNode = currentNode;
            if(value < parentNode->data) { // go left
                currentNode = parentNode->left;
                if(currentNode == NULL) { // if has no child node
                    newNode->key = "LeftChild";
                    parentNode->left = newNode;
                    newNode->parent = parentNode;
                    break;
                }
            } else { // go right
                currentNode = parentNode->right;
                if(currentNode == NULL) { // if has no child node
                    newNode->key = "RightChild";
                    parentNode->right = newNode;
                    newNode->parent = parentNode;
                    break;
                }
            }
        }
    }
}

// Function to remove a node from the tree
bool BST ::Remove(int value) {
    Node *currentNode = root;
    Node *parentNode = root;

    bool isItALeftChild = true;

    while(currentNode->data != value) {
        parentNode = currentNode;
        if(value < currentNode->data) {
            isItALeftChild = true;
            currentNode = currentNode->left;
        } else {
            isItALeftChild = false;
            currentNode = currentNode->right;
        }
        if(currentNode == NULL) {
            return false;
        }
    }

    if(currentNode->left == NULL && currentNode->right == NULL) {
        if(currentNode == root) {
            root = NULL;
        } else if(isItALeftChild) {
            parentNode->left = NULL;
        } else {
            parentNode->right = NULL;
        }
    } else if(currentNode->right == NULL) {
        if(currentNode == root) {
            root = currentNode->left;
        } else if(isItALeftChild) {
            parentNode->left = currentNode->left;
        } else {
            parentNode->right = currentNode->left;
        }
    } else if(currentNode->left == NULL) {
        if(currentNode == root) {
            root = currentNode->right;
        } else if(isItALeftChild) {
            parentNode->left = currentNode->right;
        } else {
            parentNode->right = currentNode->right;
        }
    } else {
        Node *replacement = GetReplacementNode(currentNode);

        if(currentNode == root) {
            root = replacement;
        } else if(isItALeftChild) {
            parentNode->left = replacement;
        } else {
            parentNode->right = replacement;
        }
        replacement->left = currentNode->left;
    }
    return true;
}

Node* BST ::GetReplacementNode(Node *replacedNode) {
    Node *replacementParent = replacedNode;
    Node *replacement = replacedNode;
    Node *currentNode = replacedNode->right;

    while(currentNode != NULL) {
        replacementParent = replacement;
        replacement = currentNode;
        currentNode = currentNode->left;
    }

    if(replacement != replacedNode->right) {
        replacementParent->left = replacement->right;
        replacement->right = replacedNode->right;
    }
    return replacement;
}

// Function to search a node(value) in the BST
Node* BST ::Find(int value) {
    Node *currentNode = root;
    while(currentNode->data != value) {
        if(value < currentNode->data) {
            currentNode = currentNode->left;
        } else if(value > currentNode->data) {
            currentNode = currentNode->right;
        } else {
        }
        if(currentNode == NULL) {
            break;
        }
    }
    return currentNode;
}

// Print BST in in-order, pre-order, post-order traversal
void BST ::PrintInOrder(Node* currentNode) {
    if(currentNode != NULL) {
        PrintInOrder(currentNode->left);
        cout << currentNode->toString();
        PrintInOrder(currentNode->right);
    }
}
void BST ::PrintPreOrder(Node* currentNode) {
    if(currentNode != NULL) {
        cout << currentNode->toString();
        PrintInOrder(currentNode->left);
        PrintInOrder(currentNode->right);
    }
}
void BST ::PrintPostOrder(Node* currentNode) {
    if(currentNode != NULL) {
        PrintInOrder(currentNode->left);
        PrintInOrder(currentNode->right);
        cout << currentNode->toString();
    }
}
// Free up unnecessary memory space
void BST::FreeUp(Node* currentNode) {
    if(currentNode != NULL) {
        FreeUp(currentNode->left);
        FreeUp(currentNode->right);
        delete currentNode;
        currentNode = NULL;
    }
}

// Driver
int main()
{
    BST b;
    const int ARRAY_LEN = 20;
    const int MIN_NUM = 1;
    const int MAX_NUM = 100;
    int randNum;
    srand(time(NULL)); // initialize random seed
    int arrayOfRandNums[ARRAY_LEN]; // array of 20 integers

    // To take unique 20 integers
    int i=0;
    bool duplicated = false;
    while (true) {
        randNum = rand() % MAX_NUM + MIN_NUM; // generate a random number in 1-100
        for(int j=0; j<i; j++) {
            if(arrayOfRandNums[j] == randNum) {
                duplicated = true;
                break;
            }
        }
        if(!duplicated) {
            arrayOfRandNums[i] = randNum;
            i++;
        }
        if(i == ARRAY_LEN && !duplicated) {
            break;
        }
        duplicated = false;
    }
    int arraySize = sizeof(arrayOfRandNums)/sizeof(arrayOfRandNums[0]); // get the array's size(length) == 20
    // Add elements in array to BST
    for(int k=0; k<arraySize; k++) {
        b.Insert(arrayOfRandNums[k]);
        cout << arrayOfRandNums[k] << "\n";
    }

    // Print out BST in in-order, pre-order, post-order
    cout << "In Order";
    b.PrintInOrder(b.root);
    cout << "\nPre Order";
    b.PrintPreOrder(b.root);
    cout << "\nPost Order";
    b.PrintPostOrder(b.root);

    // Search a value in BST
    cout << "\nFind " << arrayOfRandNums[7];
    Node* node = b.Find(arrayOfRandNums[7]);
    if(node != NULL) {
        cout << "\nFound :" + node->toString();
    } else {
        cout << "\nNode not found";
    }

    // Delete a node, using its value, from the BST
    cout << "\nRemove " << arrayOfRandNums[7];
    if(b.Remove(arrayOfRandNums[7])) {
        cout << "\nRemoved\n";
    } else {
        cout << "Not removed";
    }

    // Print out BST again to check if 7th element has removed
    cout << "\nIn Order\n";
    b.PrintInOrder(b.root);
    cout << "\nPre Order\n";
    b.PrintPreOrder(b.root);
    cout << "\nPost Order\n";
    b.PrintPostOrder(b.root);

    return 0;
}