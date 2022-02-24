/*
Jo Eun Kim
ICSI 311-0008
Assignment 2 - Regular Expression
 */

import java.util.ArrayList;

public class StateMachine {
    String regex;
    boolean state;
    ArrayList<State> stateList;
    int matchStartIndex;
    int matchEndIndex;

    public StateMachine() { // constructor
        regex = null;
        state = false;
        stateList = new ArrayList<State>();
        matchStartIndex = -1;
        matchEndIndex = -1;
    }

    public void setRegex(String regex) { // set regular expression
        this.regex = regex;
        buildStateMachine(); // function call to build state machine if regex is set
    }

    // Generate state machine corresponds to given regular expression
    public void buildStateMachine() {
        int stateIdx = 0;
        State state = null;
        for(int i=0; i<regex.length(); i++) {
            if( regex.charAt(i) == '*' ) { // STAR
                state = stateList.get(stateIdx-1);
                state.type = State.QuantifierType.STAR;
            } else if ( regex.charAt(i) == '+' ) { // PLUS
                state = stateList.get(stateIdx-1);
                state.type = State.QuantifierType.PLUS;
            } else if ( regex.charAt(i) == '[' ){ // BRACKET
                state = new State();
                state.id = stateIdx;
                state.type = State.QuantifierType.BRACKET;

                StringBuilder strBuffer = new StringBuilder();
                while(regex.charAt(++i) != ']') {
                    strBuffer.append(regex.charAt(i));
                }
                state.character = strBuffer.toString().toCharArray();
                stateList.add(stateIdx, state);
                stateIdx++;
            } else { // A SINGLE CHARACTER
                state = new State();
                state.character = new char[1];
                state.id = stateIdx;
                state.character[0] = regex.charAt(i);
                state.type = State.QuantifierType.CHARACTER;
                stateList.add(stateIdx, state);
                stateIdx++;
            }
        }
    }

    public void printStateMachine() {
        State state = null;
        System.out.println("Regular expression : " + regex);
        for (int i = 0 ; i < stateList.size() ; i++) {
            state = stateList.get(i);
            System.out.print("State #" + state.id + " : Char [");
            for (int j = 0 ; j < state.character.length ; j++)
                System.out.print(state.character[j]);
            System.out.println("] / Type : " + typeName(state.type));
        }
        System.out.println();
    }
    // helper function to print out enum (each operation)
    private String typeName(State.QuantifierType type) {
        switch (type) {
            case STAR:
                return "*";
            case PLUS:
                return "+";
            case BRACKET:
                return "[]";
            case CHARACTER:
                return "Single Character";
        }
        return "Unknown Type";
    }

    // Match only the following regular expressions
    //   *, +, [], single character
    public boolean match(String str) {
        // No matching if the input or state machine is empty
        if (str == null || str == "" || stateList.isEmpty()) {
            return false;
        }

        boolean matchBegan = false; // the first match found
        boolean plusMatchBegan = false; // at least one char matched with PLUS state
        boolean matchedChar = false; // current input char matches with the current state
        boolean matchedFullRegex = false; // the full matching string found

        State currentState = null;
        int currentStateIndex = 0;
        final int finalStateIndex = stateList.size() - 1;
        boolean moveToNextState = false;
        boolean moveToInitialState = false;

        int currentInputIndex = 0;
        int inputLength = str.length();
        char[] input = str.toCharArray();
        // Current input char has been (previously) checked by STAR state
        boolean[] inputChecked = new boolean[inputLength]; // default : false

        boolean endMatching = false;
        while (!endMatching) {
            currentState = stateList.get(currentStateIndex);
            matchedChar = isMatch(currentState, input[currentInputIndex]);

            switch (currentState.type) {
                case STAR:
                    if (matchedChar) { // current input char matches with the current STAR state
                        if (matchBegan == false) { // First matching char found
                            matchStartIndex = currentInputIndex;
                        }
                        matchBegan = true;
                        currentInputIndex++;
                        // Keep updating endPos if input string ends with matching character
                        if(currentInputIndex == inputLength-1) {
                            matchEndIndex = currentInputIndex;
                        }
                    } else { // current input does not match
                        // Previous input char was the last matching char
                        // It would update matchEndIndex if there is more matching character after once
                        if (matchBegan ) {
                            matchEndIndex = currentInputIndex - 1;
                        }

                        // Current input char has been already checked by this STAR state
                        if (inputChecked[currentInputIndex]) {
                            currentInputIndex++;
                        } else {
                            inputChecked[currentInputIndex] = true;
                            moveToNextState = true;
                        }
                    }
                    break;
                case PLUS:
                    if (matchedChar) { // current input char matches with the current PLUS state
                        if (matchBegan == false) { // first matching char found
                            matchStartIndex = currentInputIndex;
                        }
                        matchBegan = true;
                        plusMatchBegan = true;
                        if(plusMatchBegan) {
                            matchEndIndex = currentInputIndex;
                        }
                        currentInputIndex++;
                    } else { // current input does not match
                        if (plusMatchBegan) { // if at least one char matched with tue current PLUS state
                            moveToNextState = true;
                        } else {
                            if (matchBegan) { // previous input char was the last matching char
                                matchEndIndex = currentInputIndex - 1;
                            }
                            matchBegan = false;

                            if (currentStateIndex == 0) { // if current state is initial state
                                currentInputIndex++;
                            } else {
                                moveToInitialState = true;
                            }
                        }
                        plusMatchBegan = false;
                    }
                    break;
                // For now, Bracket and Character states behave in the same way
                case BRACKET:
                case CHARACTER:
                    if (matchedChar) { // current input char matches with the current BRACKET state
                        if (matchBegan == false) { // first matching char found
                            matchStartIndex = currentInputIndex;
                        }
                        matchBegan = true;
                        currentInputIndex++;
                        moveToNextState = true;
                    } else { // current input does not match
                        if (matchBegan) { // previous input char was the last matching char
                            matchEndIndex = currentInputIndex - 1;
                        }
                        matchBegan = false;

                        if (currentStateIndex == 0) { // if current state is initial state
                            currentInputIndex++;
                        } else {
                            moveToInitialState = true;
                        }
                    }
                    break;
                // current input char matches with the current CHARACTER state
            }

            /* String, matching the regular expression, exists if :
                1. matchBegan is true
                  AND
                        1-1. current state is the final state
                            OR
                        1-2. there is no PLUS, BRACKET, CHARACTER state left
             */
            if (matchBegan) {
                if (currentStateIndex == finalStateIndex) {
                    matchedFullRegex = true;
                } else if (currentInputIndex >= inputLength-1) { // end of the input string
                    matchedFullRegex = true;
                    // Check if there is non-STAR state left
                    for (int j=currentStateIndex+1 ; j <= finalStateIndex ; j++)
                        if (stateList.get(j).type != State.QuantifierType.STAR)
                            matchedFullRegex = false;
                }
            }

            // Handle state move flags
            if (moveToNextState) {
                currentStateIndex++;
                moveToNextState = false;
            }
            if (moveToInitialState) {
                currentStateIndex = 0;
                moveToInitialState = false;
            }

            // At the end of the loop
            /* End the matching loop if :
                1. Last input character checked
                  OR
                2. There is no more state to move to
             */
            if (currentInputIndex > inputLength-1 || currentStateIndex > finalStateIndex) {
                endMatching = true;
            }
        }

        return matchedFullRegex;
    }

    // Compare the state character(s) with the input character
    private boolean isMatch(State state, char character) {
        for (int i=0 ; i < state.character.length ; i++)
            if (state.character[i] == character)
                return true;
        return false;
    }

    // Print the matching part of the input string
    public String resultString(String str, int startPosition, int endPosition) {
        String result = "";
        for(int i=startPosition; i<=endPosition; i++) {
            result += str.charAt(i);
        }
        return result;
    }
}
