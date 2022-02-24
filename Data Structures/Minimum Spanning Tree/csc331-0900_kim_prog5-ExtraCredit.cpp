/**
a. CSC 331 - 0900
b. Jo Eun Kim
c. csc331-0900_kim_prog5-ExtraCredit.cpp
d. Program #5, due on 12/11/2019
e. In addition to prog5, this is for extra credit #8: input & output files features.
**/
// stdc++.h must be in the same directory.
#include <iostream>
#include <fstream>
#include <string>
#include <bits/stdc++.h>
// if </bits.stdc++.h> is not available,
// stdc++.h must be placed in the same directory,
// the following include statement should be used.
// #include "stdc++.h"
#include <algorithm>

using namespace std;

#define vertexCount 100

void printMatrix(int matrix[vertexCount][vertexCount]);

// find the vertex with minimum key value
int minKey(int key[], bool mstSet[]);
  
// print MST and total weight
// constructed MST stored in mst[]  
void printMST(int mst[], int graph[vertexCount][vertexCount], string outputFileName);

// Function to construct, represent graph using adjacency matrix representation  
void primMST(int graph[vertexCount][vertexCount], string outputFileName);

void writeFile(int mst[], ofstream outputfile);

int main(int argc, char* argv[])
{
    /* sample input & output
    1 3 5
    3 2 6
    1 2 7
    end-of-file
    -----------
    MST:
    1 3 5
    3 2 6
    TW: 11
    */
    string inputFileName = argv[1];
    string outputFileName = argv[2];

    cout << inputFileName << outputFileName << endl;

    int vertex[vertexCount] = {};
    int vertexMatrix[vertexCount][vertexCount] = {};

    string input;

    ifstream inputfile (inputFileName);
    

    int totalWeight = 0;
    int v1, v2, w = 0;
    int ws1, ws2 = 0;
    int count = 0;
    int vSize = 0;
    bool exists = false;
    
    while(true) {
        //cout << "Enter three integers: ";
        if (inputfile.is_open()) {
            while (getline(inputfile, input)) {
                cout << input << endl;
                count++;
            }
        } else {
            cout << "Unable to open file.";
            break;
        }

        //getline(cin, input);
        //count++;
        
        if(input == "end-of-file") {
            inputfile.close();
            break;
        }
        
        ws1 = input.find("", 1);
        ws2 = input.find("", 2);
        v1 = stoi(input.substr(0, ws1));
        v2 = stoi(input.substr(ws1+1, ws2-1));
        w = stoi(input.substr(ws2+1, input.length()));
        
        exists = find(begin(vertex), end(vertex), v1) != end(vertex);
        if(exists){
            //cout << "exists" << endl;
        } else {
            vertex[vSize] = v1;
            //cout << "added" << endl;
            vSize++;
        }
        
        exists = find(begin(vertex), end(vertex), v2) != end(vertex);
        if(exists){
            //cout << "exists" << endl;
        } else {
            vertex[vSize] = v2;
            //cout << "added" << endl;
            vSize++;
        }
        
        vertexMatrix[v1-1][v2-1] = w;
        vertexMatrix[v2-1][v1-1] = w;
    }

    cout << endl;
    // printMatrix(vertexMatrix);

    cout << "Minimum spanning tree:" << endl;
    primMST(vertexMatrix, outputFileName);
    //writeFile(mst, outputfile);

    return 0;
}
// void writeFile(int mst[], ofstream outputfile) {

//     if(outputfile.is_open()) {
//         for (int i = 1; i <= sizeof(mst)/sizeof(int); i++) {
//             outputfile << mst[i]+1 << " - " << i+1 << endl;
//         }
        
//     }
// }

void printMatrix(int matrix[vertexCount][vertexCount]) {
    for (int i = 0 ; i < vertexCount ; i++) {
        for (int j = 0 ; j < vertexCount ; j++) {
            cout << matrix[i][j] << "\t";
        }
        cout << endl;
    }
}

int minKey(int key[], bool mstSet[])
{  
    int min = INT_MAX, min_index;  
  
    for (int v = 0; v < vertexCount; v++)  
        if (mstSet[v] == false && key[v] < min)  
            min = key[v], min_index = v;  
  
    return min_index;  
}

void printMST(int mst[], int graph[vertexCount][vertexCount], string outputFileName)
{  
    int totalWeight = 0;
    for (int i = 1; i <= sizeof(mst)/sizeof(int); i++) {
        cout<<mst[i]+1<<" - "<<i+1 << endl;
        totalWeight += graph[i][mst[i]];
    }
    cout << "Edge Weight Total : " << totalWeight << endl;

    ofstream outputfile (outputFileName);

    if(outputfile.is_open()) {
        for (int i = 1; i <= sizeof(mst)/sizeof(int); i++) {
            outputfile << mst[i]+1 << " - " << i+1 << endl;
        }
        
    }
}

void primMST(int graph[vertexCount][vertexCount], string outputFileName)
{  
    // Array to store constructed MST  
    int mst[vertexCount];  
      
    // Key values used to pick minimum weight edge in cut  
    int key[vertexCount];  
      
    // To represent set of vertices not yet included in MST  
    bool mstSet[vertexCount];
  
    for (int i = 0; i < vertexCount; i++)  
        key[i] = INT_MAX, mstSet[i] = false;  
  
    // key is picked as first vertex.  
    key[0] = 0;  
    mst[0] = -1; 
  
    // The MST will have vertexCount vertices
    for (int count = 0; count < vertexCount - 1; count++) 
    {  
        int u = minKey(key, mstSet);  
  
        // Add the picked vertex to the MST Set  
        mstSet[u] = true;  
 
        for (int v = 0; v < vertexCount; v++)  
            if (graph[u][v] && mstSet[v] == false && graph[u][v] < key[v])  
                mst[v] = u, key[v] = graph[u][v];  
    }  
  
    // print the constructed MST  
    printMST(mst, graph, outputFileName);
    
}
