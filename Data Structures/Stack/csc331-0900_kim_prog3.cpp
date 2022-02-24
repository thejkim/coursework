/**
a. CSC 331 - 0900
b. Jo Eun Kim
c. csc331-0900.kim_prog3.cpp
d. Program #3, due on 10/30/2019
e. to implement a stack data structure in C++
**/

#include <iostream>
#include <stack>
#include <string>

using namespace std;

int charToInt(char ch);

int main(){
    stack<double> postfixStack;
    string input = "";

     while (true) {
        cout << "Please enter an expression in post-fix notation: ";
        cin >> input;

        if (input == "end-of-file")
            break;

        // Range
        // for(char& c : input) {
        //     cout << c << endl;
        // }

        int oper = 0;

        double oper1 = 0;
        double oper2 = 0;

        bool error = 0; // true = 0, false = non-0

        // Iterator
        // Input : 43+5- = 2
        /*
            23+ (result = 5)
            34*2/ (result = 6)
            42+351-*+ = 18
            832*6-/ (result= “error: division by zero”)
        */
        for(string::iterator it = input.begin(); it != input.end(); ++it) {
            oper = charToInt(*it);

            // single digit int
            if (charToInt(*it) >= 0) {
                postfixStack.push(oper);
            } else {
                oper1 = postfixStack.top();
                postfixStack.pop();
                oper2 = postfixStack.top();
                postfixStack.pop();

                switch (*it) {
                    case '+':
                        postfixStack.push(oper2 + oper1);
                        break;
                    case '-':
                        postfixStack.push(oper2 - oper1);
                        break;
                    case '*':
                        postfixStack.push(oper2 * oper1);
                        break;
                    case '/':
                        if (oper1 == 0.0) {
                            cout << "Invalid : divide by zero." << endl;
                            it = input.end()-1;
                            error = true;
                        } else {
                            postfixStack.push(oper2 / oper1);
                        }
                        break;
                    default:
                        break;
                }
            }
        }

        if (error != true)
            cout << input << " = " << postfixStack.top() << endl << endl;
    }
    // // Old Fashion
    // for(string::size_type i = 0; i < input.size(); ++i) {
    //     cout << atoi(&input[i]) << endl;
    // }

    return 0;
}

int charToInt(char ch) {
    int num = (int)ch - 48;
    if( num >= 0 && num <=9) {
        return num;
    } else {
        return -1;
    }
}