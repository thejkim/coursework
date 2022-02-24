/*
Jo Eun Kim
ICSI 311-0008
Assignment 4 - Binary Search Tree
 */
public class Log {
    static public void print(String string) {
        System.out.print(string);
    }
    static public void println(String string) {
        System.out.println(string);
    }
    static public void log(String string) {
        print("[" + Thread.currentThread().getStackTrace()[2].getLineNumber() + "] " + string);
    }
    static public void logln(String string) {
        println("[" + Thread.currentThread().getStackTrace()[2].getLineNumber() + "] " + string);
    }
}
