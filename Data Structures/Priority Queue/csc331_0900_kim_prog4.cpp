/**
a. CSC 331 - 0900
b. Jo Eun Kim
c. csc331_0900_kim_prog4.cpp
d. Program #4, due on 11/25/2019
e. to implement a priority queue data structure in C++ using an array-based heap
**/

#include <iostream>
#include <queue>

using namespace std;

typedef pair<int, string> pi;

bool isFull() {
    return (psq.size() == 10);
}

int main()
{
    //stringQueue.push("");
    //stringQueue.front();
    //stringQueue.back();
    //stringQueue.pop();
    int dash1, dash2, model, modelDef;
    string name, warranty;
    
    const int currentYear = 2019;
    
    priority_queue<pi, vector<pi>, greater<pi>> psq;
    string input = "";
    
    
    
    while (true) {
        cout << "Enter input: " << endl;
        cin >> input;
        
        if(input == "end-of-file")
            break;
            
        
        
        //cout << name << " " << model << " " << warranty << endl;
        
        if(input == "service") {
            pair<int, string> top = psq.top();
            cout << top.second << endl;
            psq.pop();
        }else {
            
            dash1 = input.find("-", 1);
            dash2 = input.find("-", 2);
            name = input.substr(0, dash1);
            warranty = input.substr(input.length()-1,input.length());
            model = stoi(input.substr(dash1+1, dash2+1));
        
            modelDef = currentYear - model;
            
            if(warranty == "y") // priority 1: warranty code = 'y'
                psq.push(make_pair(1, name));
            else { // non-warranty
                if(modelDef < 6) // priority 2: model is less than 6 years old
                    psq.push(make_pair(2, name));
                else if(modelDef >= 5) // priority 3: model is older than 5 years
                    psq.push(make_pair(3, name));
            }
            
        }
        
    } 
      
    while (!psq.empty()) {
            pair<int, string> top = psq.top();
            cout << top.second << endl;
            psq.pop();
    }
   
    
    return 0;
}
