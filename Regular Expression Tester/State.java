/*
Jo Eun Kim
ICSI 311-0008
Assignment 2 - Regular Expression
 */

public class State {
    enum QuantifierType {
        STAR,
        PLUS,
        BRACKET,
        CHARACTER
    }

    int id;
    char[] character;
    QuantifierType type;

    public State() {
        id = 0;
        character = null;
        type = null;
    }
}
