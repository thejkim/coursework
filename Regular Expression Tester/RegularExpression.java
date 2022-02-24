/*
Jo Eun Kim
ICSI 311-0008
Assignment 2 - Regular Expression
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RegularExpression {
    public static void main(String[] args) {
        String regularExpression;
        String fileName;
        regularExpression = args[0]; // 1st command line argument == regex
        fileName = args[1]; // 2nd command line argument == text file name

        StateMachine stateMachine = new StateMachine();
        stateMachine.setRegex(regularExpression); // send regular expression to StateMachine(), will build corresponding state machine

        /**** Deliverable #1: A state machine of your engine ****/
        stateMachine.printStateMachine(); // print the generated state machine, displying each stage info

        testAndPrintResult(stateMachine, fileName); // test given test case, print matching info and test result
    }

    /**** Deliverable #3: TEST PLAN on how you plan to test all of the regular expression options ****
     : Prepared 5 test cases to include and check all of the regular expression options
        - TEST CASES (each regex is included in text file at 1st line)
            1. Regex: a*[123]b+c*  /   Test File: testcase1.txt
            2. Regex: 12+[xyz]a*    /   Test File: testcase2.txt
            3. Regex: [123456789]reg+exa*[gode]0    /   Test File: testcase3.txt
            4. Regex: a*b+c*[def]gh*i+  /   Test File: testcase4.txt
            5. Regex: h+e*l[abc][10]*    /   Test File: testcase5.txt
     : Method to verify test result
        - For each text file, it is designed to have an indicator at the end of each line with "=" sign
        - after "=" there is
            1. "Y" : if match is supposed to be found in the line
                OR
            2. "N" : if match is supposed to be not found in the line
        - When test done, it will print out if test was success or failed in two steps of verification
            1. by matching "Y" with our program result. Check if "Y" when match() == true
                AND
            2. by mmtching count for "Y" and match result
        - In this way, we can verify test result that "all" and "only" matching lines are detected as matched
     */
    public static void testAndPrintResult(StateMachine stateMachine, String fileName) {
        String inputData; // a string in each line in the file, be checked if match found in this string
        ArrayList<Integer> matchedLines = new ArrayList<Integer>(); // store matched line numbers
        String[] splitString; // for my test plan, split text in a line before and after "=", splitString[1] indicates result
        int countMatch = 0; // ++ if function match() == true
        int countY = 0; // ++ if there is "Y" at the end of each line (after "=" sign)
        boolean testSuccess = false;
        try {
            int lineNumber = 0;
            File inputFile = new File(fileName); // open file
            Scanner myReader = new Scanner(inputFile); // read line
            while (myReader.hasNextLine()) { // until end of file
                lineNumber++;
                inputData = myReader.nextLine(); // read and store a line info

                // Count if "Y" at the end of each line. We assume we have "=" at every line
                splitString = inputData.split("=");
                if( splitString[1].equals("Y")) {
                    countY++;
                }

                if(stateMachine.match(inputData)) { // *** Match found ***
                    // Print matching info (line #, start position, end position, matched part of string)
                    System.out.println("Match found on line " + lineNumber + ", starting at position " + stateMachine.matchStartIndex
                            + " and ending at position " + stateMachine.matchEndIndex + ": " + stateMachine.resultString(inputData, stateMachine.matchStartIndex, stateMachine.matchEndIndex));
                    matchedLines.add(lineNumber); // add line # to 'matchedLines'
                    countMatch++;
                    // Check result
                    if(splitString[1].equals("Y")) {
                        testSuccess = true; // match() == true && "Y"
                    } else {
                        testSuccess = false;
                    }
                }
            }
            myReader.close(); // close file
        } catch (FileNotFoundException e) { // error handling
            System.out.println("Cannot find or open the file.");
            e.printStackTrace();
        }
        // Print result for the test
        if(testSuccess == true && countY == countMatch) { // "all" and "only" matching lines were detected as matched
            System.out.println("\nTest success.");
        } else {
            System.out.println("\nTest failed.");
        }
    }
}
