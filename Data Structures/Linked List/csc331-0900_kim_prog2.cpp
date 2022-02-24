/**
CSC 331-0900
Jo Eun Kim
csc331-0900_kim_prog2.cpp
program #2, due on 10/9/2019
To implement a transaction-based linked list data structure using C++
Last updated: 10/5/2019 2:37 AM
**/

#include <iostream>
#include <string>
using namespace std;

//node consists of name, age, and pointer(link) to another node(next node in this program) in list
struct nodeType {
	string name;
	int age;
	nodeType *link;
};

//function prototypes
bool add(nodeType **head, string input); // insert node in list.
bool isInList(nodeType *head, string input); // check if same name is already in list.
void showList(nodeType *head); // display list with name and respective age on a line by itself.
bool deleteNode(nodeType **head, string input); // delete node in list if exists.

int main() {
	nodeType *first;
	string input;
	first = NULL;	

	cout << "Enter a transaction..." << endl;
	getline(cin, input);
    
    while (input != "Q") {
        if(input[0] == 'A') {
            if(isInList(first, input)) { // name is already on list. won't be added.
                cout << "name not added" << endl;
            } else { // name is not on list. will be added.
                add(&first, input); 
                cout << "name added" << endl;
            }
        } else if(input[0] == 'D') {
            if(first != NULL) {
                if(deleteNode(&first, input)) { // name is on list. will be deleted.
                   cout << "name deleted" << endl;
                }else { // name is not on list.
                    cout << "name not found" << endl;
                }
            } else { // list is empty.
                cout << "name not found" << endl;
            }
        } else if(input[0] == 'L') {
            if(first != NULL) // at least one element in the list needed to display list.
                showList(first);
            else { // list is empty.
                cout << "List is empty."<< endl;
            }
        } else { // invalid cmd input.
            cout << "Invalid transaction form. Use 'A' to add, 'D' to delete, 'L' to display the list, and 'Q' to terminate the program." << endl;
        }
        
        cout << "Enter a transaction..." << endl;
        getline(cin, input);
    }
	return 0;
}

bool add(nodeType **first, string input) {
    nodeType *head, *current, *newNode, *prior;
    head = *first;
    int done;
    
    int ws2 = input.find(" ", 2); // find pos where 2nd white-space exists.
    string inputName = input.substr(2, ws2-2); // store substr from input[2] ~before 2nd white-space found.
    int inputAge = stoi(input.substr(ws2+1, input.length())); // store and convert substr after 2nd white-space(to max) to int
    
    if(head == NULL) { // insert into empty list
        head = new nodeType;
        head -> name = inputName;
        head -> age = inputAge;
        head -> link = NULL;
        done = 1;
    } else {
        if(head->name > input.substr(2, ws2-2)) { // insert at the top of list
            newNode = new nodeType;
            newNode -> name = inputName;
            newNode -> age = inputAge;
            newNode -> link = head;
            head = newNode;
            done = 1;
        } else {
            done = 0;
            current = head;
            while( (current != NULL) && (!done) ) { // insert between two existing nodes
                if(current -> name > inputName) {
                    newNode = new nodeType;
                    newNode -> name = inputName;
                    newNode -> age = inputAge;
                    newNode -> link = current;
                    prior -> link = newNode;
                    done = 1;
                } else { // jump to next node to check the condition above.
                    prior = current;
                    current = current -> link;
                }
            } if(!done) { // insert at the end of list
                newNode = new nodeType;
                newNode -> name = inputName;
                newNode -> age = inputAge;
                newNode -> link = current;
                prior -> link = newNode;
                done = 1;
            } 
        }
    }
    *first = head;
    return done;
}

bool deleteNode(nodeType **first, string input) {
    nodeType *head, *current, *previousNode;
    head = *first;
    
    previousNode = head; // first node
    current = head -> link; // next(second) node
    int done = 0;
    
    string inputName = input.substr(2, input.length()); // store substr from input[2] to end(max)

    if(isInList(head, input)) { // inputName is in list. will be deleted.
        while((previousNode != NULL) && (!done) ) { // case 1: to check if first node needed to be deleted since there may be only one element in list.
            if(previousNode -> name == inputName) { // inputName matches to first node->name. will be excepted from list.
                head = current; // 2nd node(current) is now the first node in list.
                done = 1;
            } else { // case 2: to check from 2nd node in list to end.
                while((current != NULL) && (!done) ) { // there are at least 2 elements in list.
                    if(current -> name == inputName) { // matches. will be deleted and relinked.
                        previousNode -> link = current -> link; // two nodes before and after deleting node linked.
                        delete current;
                        done = 1;
                    } else { // jump to next node to check the condition above.
                        previousNode = current;
                        current = current -> link;
                    }
                }
            }
        }
        *first = head;
        return done = 1;
    } else { // inputName is not in list.
        return done = 0;
    }
}

void showList(nodeType *head) {
    nodeType *current;
    current = head;
    while(current != NULL) {
        cout << current -> name << " " << current -> age << endl;
        current = current -> link;
    }
}

bool isInList(nodeType *head, string input) {
    nodeType *current;
    current = head;
    int ws2 = input.find(" ", 2);
    string name = input.substr(2, ws2-2);
    int found = 0;
    while((current!=NULL) && (!found)) {
        found = (name == current->name);
        current=current->link;
    }
    return found;
}