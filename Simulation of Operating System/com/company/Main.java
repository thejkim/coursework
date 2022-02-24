package com.company;

import com.company.device.Device;
import com.company.device.RandomDevice;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        KernelBindings kb = new KernelBindings();

        System.out.println("-----TEST CASE 1 : Random Device-----");
        // TEST CASE 1 : Random Device - open, write, read, seek, close
        // then [Must Fail]seek for it
        int p1 = (int)kb.CreateProcess();
        int test1ConnectionID = (int)kb.Open("random test1");
        System.out.println("\ttest1ConnectionID = "+test1ConnectionID);
        byte[] test1Data = stringToByteArray("abcdefghijklmnopqrstuvwxyz123456789");
        int test1WriteReturnValue = (int)kb.Write(test1ConnectionID, test1Data);
        System.out.println("\ttest1WriteReturnValue = "+test1WriteReturnValue);
        byte[] test1ReadReturnValue = kb.Read(test1ConnectionID, 5);
        printByteArray(test1ReadReturnValue);
        kb.Seek(test1ConnectionID, 3);
        kb.Close("random " + test1ConnectionID);
        kb.Seek(test1ConnectionID, 3); // It shoudn't be able to seek for it as it's closed.

        System.out.println("\n-----TEST CASE 2 : Pipe Device Part 1-----");
        // TEST CASE 2 : Pipe Device - [new] open, write
        int p2 = (int)kb.CreateProcess();
        int test2ConnectionID = (int)kb.Open("pipe test2");
        System.out.println("\ttest2ConnectionID = "+test2ConnectionID);
        byte[] test2Data = stringToByteArray("abcdefghijklmnopqrstuvwxyz123456789"); // TODO:- when it's shorter than size when reading.
        int test2WriteReturnValue = (int)kb.Write(test2ConnectionID, test2Data);
        System.out.println("\ttest2WriteReturnValue = "+test2WriteReturnValue);

        System.out.println("\n-----TEST CASE 3 : Pipe Device Part 2-----");
        // TEST CASE 3 : Pipe Device
        // open same prog "test2", seek, read from it,
        // and [Must Fail]read and seek from it again,
        // and close,
        // then [Must Fail]seek for it again.
        int p3 = (int)kb.CreateProcess();
        int test3ConnectionID = (int)kb.Open("pipe test2");
        System.out.println("\ttest3ConnectionID = "+test3ConnectionID);
        kb.Seek(test3ConnectionID, 5); // Seek doesn't have to clear the pipe.
        byte[] test3ReadReturnValue = kb.Read(test3ConnectionID, 8); // Read has to clear the pipe.
        printByteArray(test3ReadReturnValue);
        byte[] test3DontRead = kb.Read(test3ConnectionID, 8); // It shouldn't be able to read from it as it's cleared
        printByteArray(test3DontRead); // Empty byte.
        kb.Seek(test3ConnectionID, 5); // It shouldn't be able to seek for it as it's cleared
        kb.Close("pipe " + test3ConnectionID); // closing test3 case
        kb.Close("pipe " + test2ConnectionID); // closing test2 case
        kb.Seek(test3ConnectionID, 5); // It shouldn't be able to seek for it as it's closed/destroyed

        System.out.println("\n-----TEST CASE 3 : Fake File System Device-----");
        // TEST CASE 3 : Fake File System Device
        int p4 = (int)kb.CreateProcess();
//        kb.Open("file "); // throws IOException - checked.
        int test4ConnectionID = kb.Open("file test4.dat");
        System.out.println("\ttest4ConnectionID = " + test4ConnectionID);
        byte[] test4Data = stringToByteArray("abcdefghijklmnopqrstuvwxyz123456789");
        printByteArray(test4Data);
        int test4WriteReturnValue = kb.Write(test4ConnectionID, test4Data);
        System.out.println("\t" + test4WriteReturnValue + " is written.");
        kb.Seek(test4ConnectionID, 10);
        byte[] test4ReadReturn = kb.Read(test4ConnectionID, 10);
        printByteArray(test4ReadReturn);
        printStringFromByteArray(test4ReadReturn);
        byte[] test4ReadNext = kb.Read(test4ConnectionID, 20); // size is larger than number of bytes(15) left => print only 15 bytes.
        printStringFromByteArray(test4ReadNext);
        kb.Close("file " + test4ConnectionID);
        kb.Seek(test4ConnectionID, 1); // check if file was closed: Must print "Cannot find connectionID ..."


        kb.DeleteProcess(p1);
        kb.DeleteProcess(p2);
        kb.DeleteProcess(p3);
        kb.DeleteProcess(p4);

    }

    // helper function to print out byte array for test purpose.
    public static void printByteArray(byte[] data) {
        System.out.print("\tData in Bytes = ");
        for (byte bt : data) {
            System.out.print(String.valueOf(bt));
        }
        System.out.println();
    }

    // helper function to print out byte array in String
    public static void printStringFromByteArray(byte[] data) {
        String str = new String(data);
        System.out.println("\tData in String = " + str);
    }

    // helper function to convert input string to byte array
    public static byte[] stringToByteArray(String str) {
        byte[] test1Data = str.getBytes();
        return test1Data;
    }
}
