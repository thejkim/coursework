public class multiplier_test {
    public static void main(String args[]) {
        longword lw1 = new longword();
        longword lw2 = new longword();
        bit bit1 = new bit();
        bit bit0 = new bit();

        runTests(bit0, bit1, lw1, lw2); // call the test class
    }
    /*
        (All test will be done in unsigned 32 bits, which means the 1st bit in 32 combination of bits is not used as the sign bit.)
        In this test, we use two helper functions
            private static String longwordToString(longword lw) : convert longword to String
            private static longword stringTolongword(String s) : convert String to longword
        in order to set up test data sets and to compare our result with the correct result more easily and efficiently.
        Using these methods, we can simply insert binary numbers as string into variables used to test.
     */
    public static void runTests(bit bit0, bit bit1, longword lw1, longword lw2) {
        /* test set 1
                                                              1010
                                                            x 1011
           (ignore upper 32 bits) 00000000000000000000000001101110
        */

        // assign bit values, converted from string form, to longword
        lw1 = stringTolongword("00000000000000000000000000001010");
        lw2 = stringTolongword("00000000000000000000000000001011");

        System.out.println("lw1 = " + longwordToString(lw1)
                + "\n" + "lw2 = " + longwordToString(lw2));

        System.out.print("lw1 * lw2 : ");
        longword resultInLongword = Multiply.multiply(lw1, lw2);
        String resultInString = longwordToString(resultInLongword);
        String correctResultInString = "00000000000000000000000001101110";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );
        System.out.println();

        /* test set 2
                                  00000000000000000000000000000000
                                x 00000000000000000000000000000000
           (ignore upper 32 bits) 00000000000000000000000000000000
        */

        // assign bit values, converted from string form, to longword
        lw1 = stringTolongword("00000000000000000000000000000000");
        lw2 = stringTolongword("00000000000000000000000000000000");

        System.out.println("lw1 = " + longwordToString(lw1)
                + "\n" + "lw2 = " + longwordToString(lw2));

        System.out.print("lw1 * lw2 : ");
        resultInLongword = Multiply.multiply(lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "00000000000000000000000000000000";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );
        System.out.println();

        /* test set 3
                                  11111111111111111111111111111111
                                x 00000000000000000000000000000000
           (ignore upper 32 bits) 00000000000000000000000000000000
        */

        // assign bit values, converted from string form, to longword
        lw1 = stringTolongword("11111111111111111111111111111111");
        lw2 = stringTolongword("00000000000000000000000000000000");

        System.out.println("lw1 = " + longwordToString(lw1)
                + "\n" + "lw2 = " + longwordToString(lw2));

        System.out.print("lw1 * lw2 : ");
        resultInLongword = Multiply.multiply(lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "00000000000000000000000000000000";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );
        System.out.println();

        /* test set 4
                                         11111111111111111111111111111111
                                       x 11111111111111111111111111111111
         1111111111111111111111111111111000000000000000000000000000000001
                  (ignore upper 32 bits) 00000000000000000000000000000001
        */

        // assign bit values, converted from string form, to longword
        lw1 = stringTolongword("11111111111111111111111111111111");
        lw2 = stringTolongword("11111111111111111111111111111111");

        System.out.println("lw1 = " + longwordToString(lw1)
                + "\n" + "lw2 = " + longwordToString(lw2));

        System.out.print("lw1 * lw2 : ");
        resultInLongword = Multiply.multiply(lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "00000000000000000000000000000001";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );
        System.out.println();

        /* test set 5
                                         10001011000001111111110011100000
                                       x 00001011010010111100000000000001
                  (ignore upper 32 bits) 00111110010011111111110011100000
        */

        // assign bit values, converted from string form, to longword
        lw1 = stringTolongword("10001011000001111111110011100000");
        lw2 = stringTolongword("00001011010010111100000000000001");

        System.out.println("lw1 = " + longwordToString(lw1)
                + "\n" + "lw2 = " + longwordToString(lw2));

        System.out.print("lw1 * lw2 : ");
        resultInLongword = Multiply.multiply(lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "00111110010011111111110011100000";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );
    }

    // helper function to convert longword to string in order to compare with the correct result
    private static String longwordToString(longword lw) {
        String lwString = "";
        for(int i=0; i< lw.size(); i++) {
            lwString += lw.getBit(i).toString(); // call method toString() of bit class to convert bit to string, and combine to resulting string
        }
        return lwString;
    }
    // helper function to convert string to longword in order to assign test data more easily to the longword a and b
    private static longword stringTolongword(String s) {
        longword lw3 = new longword();
        bit bit1 = new bit();
        bit1.set();
        int bitValue = 0;
        for(int i=0; i<s.length(); i++) {
            // convert character to integer at each ith position in the string s
            bitValue = Integer.parseInt(String.valueOf(s.charAt(i)));
            if (bitValue == 1) // assign 1 where appropriate
                lw3.setBit(i, bit1);
        }
        return lw3;
    }
    // helper function to print out the result
    public static void printLongword(longword lw4) {
        for (int i = 0; i < lw4.size(); i++)
            System.out.print(lw4.getBit(i).getValue());
        System.out.println();
    }
}
