public class ALU_test {
    public static void main(String args[]) {
        longword lw1 = new longword();
        longword lw2 = new longword();
        bit[] operation = new bit[4];

        runTests(operation, lw1, lw2); // call the test class
    }
    /*
        (All test will be done in unsigned 32 bits, which means the 1st bit in 32 combination of bits is not used as the sign bit.)
        In this test, we use two helper functions
            private static String longwordToString(longword lw) : convert longword to String
            private static longword stringTolongword(String s) : convert String to longword
        in order to set up test data sets and to compare our result with the correct result more easily and efficiently.
        Using these methods, we can simply insert binary numbers as string into variables used to test.
     */
    public static void runTests(bit[] op, longword lw1, longword lw2) {
        bit bit1 = new bit();
        bit1.set();
        bit bit0 = new bit();
        bit0.clear();

        /* test set 1
           a            = 00000000000000000000000010001101
           b            = 00000000000000000000000001011100
           and          = 00000000000000000000000000001100
           or           = 00000000000000000000000011011101
           xor          = 00000000000000000000000011010001
           not "a"      = 11111111111111111111111101110010
           * shift "a" by 28 : (bin)11100 == (dec)28
           left shift   = 11010000000000000000000000000000
           right shift  = 00000000000000000000000000000000
           add          = 00000000000000000000000011101001
           subtract     = 00000000000000000000000000110001
           multiply     = 00000000000000000011001010101100
        */

        // assign bit values, converted from string form, to longword
        lw1 = stringTolongword("00000000000000000000000010001101");
        lw2 = stringTolongword("00000000000000000000000001011100");

        System.out.println("a = " + longwordToString(lw1)
                + "\n" + "b = " + longwordToString(lw2));

        System.out.print("and : ");
        op = new bit[] {bit1, bit0, bit0, bit0}; // 1000 – and
        // call method doOp() with given operation code
        longword resultInLongword = ALU.doOp(op, lw1, lw2);
        // convert result to String => we can easily compare to the correct result
        String resultInString = longwordToString(resultInLongword);
        // the correct result in string, which to be compared with our result got from doOp()
        String correctResultInString = "00000000000000000000000000001100";
        // if resultInString == correctResultInString, it works correctly
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("or : ");
        op = new bit[] {bit1, bit0, bit0, bit1}; // 1001 – or
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "00000000000000000000000011011101";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("xor : ");
        op = new bit[] {bit1, bit0, bit1, bit0}; // 1010 – xor
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "00000000000000000000000011010001";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("not : ");
        op = new bit[] {bit1, bit0, bit1, bit1}; // 1011 – not
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "11111111111111111111111101110010";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("left shift : ");
        op = new bit[] {bit1, bit1, bit0, bit0}; // 1100 – left shift
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "11010000000000000000000000000000";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("right shift : ");
        op = new bit[] {bit1, bit1, bit0, bit1}; // 1101 – right shift
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "00000000000000000000000000000000";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("add : ");
        op = new bit[] {bit1, bit1, bit1, bit0}; // 1110 – add
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "00000000000000000000000011101001";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("subtract : ");
        op = new bit[] {bit1, bit1, bit1, bit1}; // 1111 – subtract
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "00000000000000000000000000110001";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("multiply : ");
        op = new bit[] {bit0, bit1, bit1, bit1}; // 0111 - multiply
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "00000000000000000011001010101100";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );
        System.out.println();

        /* test set 2
           a            = 00000000000000000000000000000000
           b            = 11111111111111111111111111111111
           and          = 00000000000000000000000000000000
           or           = 11111111111111111111111111111111
           xor          = 11111111111111111111111111111111
           not "a"      = 11111111111111111111111111111111
           * shift "a" by 28 : (bin)11111 == (dec)31
           left shift   = 00000000000000000000000000000000
           right shift  = 00000000000000000000000000000000
           add          = 11111111111111111111111111111111
           subtract     = 00000000000000000000000000000001
           multiply     = 00000000000000000000000000000000
        */

        // assign bit values, converted from string form, to longword
        lw1 = stringTolongword("00000000000000000000000000000000");
        lw2 = stringTolongword("11111111111111111111111111111111");

        System.out.println("a = " + longwordToString(lw1)
                + "\n" + "b = " + longwordToString(lw2));

        System.out.print("and : ");
        op = new bit[] {bit1, bit0, bit0, bit0}; // 1000 – and
        // call method doOp() with given operation code
        resultInLongword = ALU.doOp(op, lw1, lw2);
        // convert result to String => we can easily compare to the correct result
        resultInString = longwordToString(resultInLongword);
        // the correct result in string, which to be compared with our result got from doOp()
        correctResultInString = "00000000000000000000000000000000";
        // if resultInString == correctResultInString, it works correctly
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("or : ");
        op = new bit[] {bit1, bit0, bit0, bit1}; // 1001 – or
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "11111111111111111111111111111111";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("xor : ");
        op = new bit[] {bit1, bit0, bit1, bit0}; // 1010 – xor
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "11111111111111111111111111111111";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("not : ");
        op = new bit[] {bit1, bit0, bit1, bit1}; // 1011 – not
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "11111111111111111111111111111111";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("left shift : ");
        op = new bit[] {bit1, bit1, bit0, bit0}; // 1100 – left shift
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "00000000000000000000000000000000";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("right shift : ");
        op = new bit[] {bit1, bit1, bit0, bit1}; // 1101 – right shift
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "00000000000000000000000000000000";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("add : ");
        op = new bit[] {bit1, bit1, bit1, bit0}; // 1110 – add
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "11111111111111111111111111111111";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("subtract : ");
        op = new bit[] {bit1, bit1, bit1, bit1}; // 1111 – subtract
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "00000000000000000000000000000001";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("multiply : ");
        op = new bit[] {bit0, bit1, bit1, bit1}; // 0111 - multiply
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "00000000000000000000000000000000";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );
        System.out.println();

        /* test set 3
           a            = 10001011000001111111110011100000
           b            = 00001011010010111100000000000001
           and          = 00001011000000111100000000000000
           or           = 10001011010011111111110011100001
           xor          = 10000000010011000011110011100001
           not "a"      = 01110100111110000000001100011111
           * shift "a" by 28 : (bin)00001 == (dec)1
           left shift   = 00010110000011111111100111000000
           right shift  = 01000101100000111111111001110000
           add          = 10010110010100111011110011100001
           subtract     = 01111111101111000011110011011111
           multiply     = 00111110010011111111110011100000
        */

        // assign bit values, converted from string form, to longword
        lw1 = stringTolongword("10001011000001111111110011100000");
        lw2 = stringTolongword("00001011010010111100000000000001");

        System.out.println("a = " + longwordToString(lw1)
                + "\n" + "b = " + longwordToString(lw2));

        System.out.print("and : ");
        op = new bit[] {bit1, bit0, bit0, bit0}; // 1000 – and
        // call method doOp() with given operation code
        resultInLongword = ALU.doOp(op, lw1, lw2);
        // convert result to String => we can easily compare to the correct result
        resultInString = longwordToString(resultInLongword);
        // the correct result in string, which to be compared with our result got from doOp()
        correctResultInString = "00001011000000111100000000000000";
        // if resultInString == correctResultInString, it works correctly
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("or : ");
        op = new bit[] {bit1, bit0, bit0, bit1}; // 1001 – or
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "10001011010011111111110011100001";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("xor : ");
        op = new bit[] {bit1, bit0, bit1, bit0}; // 1010 – xor
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "10000000010011000011110011100001";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("not : ");
        op = new bit[] {bit1, bit0, bit1, bit1}; // 1011 – not
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "01110100111110000000001100011111";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("left shift : ");
        op = new bit[] {bit1, bit1, bit0, bit0}; // 1100 – left shift
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "00010110000011111111100111000000";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("right shift : ");
        op = new bit[] {bit1, bit1, bit0, bit1}; // 1101 – right shift
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "01000101100000111111111001110000";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("add : ");
        op = new bit[] {bit1, bit1, bit1, bit0}; // 1110 – add
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "10010110010100111011110011100001";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("subtract : ");
        op = new bit[] {bit1, bit1, bit1, bit1}; // 1111 – subtract
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "01111111101111000011110011011111";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("multiply : ");
        op = new bit[] {bit0, bit1, bit1, bit1}; // 0111 - multiply
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "00111110010011111111110011100000";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );
        System.out.println();

        /* test set 4
           a            = 10111110101000010101110110000101
           b            = 11111111111111111111111111100110
           and          = 10111110101000010101110110000100
           or           = 11111111111111111111111111100111
           xor          = 01000001010111101010001001100011
           not "a"      = 01000001010111101010001001111010
           * shift "a" by 6 : (bin)00110 == (dec)6
           left shift   = 10101000010101110110000101000000
           right shift  = 00000010111110101000010101110110
           add          = 10111110101000010101110101101011
           subtract     = 11000001010111101010001001100001
                          10111110101000010101110110011111
           multiply     = 10100011100111001000000001111110
        */
        lw1 = stringTolongword("10111110101000010101110110000101");
        lw2 = stringTolongword("11111111111111111111111111100110");

        System.out.println("a = " + longwordToString(lw1)
                    + "\n" + "b = " + longwordToString(lw2));

        System.out.print("and : ");
        op = new bit[] {bit1, bit0, bit0, bit0}; // 1000 – and
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "10111110101000010101110110000100";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("or : ");
        op = new bit[] {bit1, bit0, bit0, bit1}; // 1001 – or
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "11111111111111111111111111100111";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("xor : ");
        op = new bit[] {bit1, bit0, bit1, bit0}; // 1010 – xor
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "01000001010111101010001001100011";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("not : ");
        op = new bit[] {bit1, bit0, bit1, bit1}; // 1011 – not
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "01000001010111101010001001111010";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("left shift : ");
        op = new bit[] {bit1, bit1, bit0, bit0}; // 1100 – left shift
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "10101000010101110110000101000000";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("right shift : ");
        op = new bit[] {bit1, bit1, bit0, bit1}; // 1101 – right shift
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "00000010111110101000010101110110";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("add : ");
        op = new bit[] {bit1, bit1, bit1, bit0}; // 1110 – add
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "10111110101000010101110101101011";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("subtract : ");
        op = new bit[] {bit1, bit1, bit1, bit1}; // 1111 – subtract
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "10111110101000010101110110011111";
        System.out.println(( resultInString.equals(correctResultInString) == true ? "pass" : "fail" ) );

        System.out.print("multiply : ");
        op = new bit[] {bit0, bit1, bit1, bit1}; // 0111 - multiply
        resultInLongword = ALU.doOp(op, lw1, lw2);
        resultInString = longwordToString(resultInLongword);
        correctResultInString = "10100011100111001000000001111110";
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
