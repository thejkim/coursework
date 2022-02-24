public class memory_test {
    public static void main(String args[]) {
        runTests(); // run test
    }
    public static void runTests() {
        Memory memory = new Memory();
        longword data = new longword(); // value to write
        longword result = new longword(); // value to read
        longword address = new longword(); // value of address in binary

        /* test 1 */
        System.out.println("TEST 1 : regular test case");
        //System.out.println("data = 01111111111111111111111111111110");
        System.out.println("data = 11111111111111110000000011110000");
        System.out.println("address value = 1020");
        //data = stringTolongword("01111111111111111111111111111110");
        data = stringTolongword("11111111111111110000000011110000");
        address = stringTolongword("00000000000000000000001111111100"); // 1020
        memory.write(address, data);
        result = memory.read(address);
        System.out.print("reading the address... \nresult = ");
        printLongword(result); // this should print 01111111111111111111111111111110
        System.out.print("reading address 0 (noting written to yet)...\nresult = ");
        address = new longword();
        result = memory.read(address);
        printLongword(result); // this should print 00000000000000000000000000000000
        System.out.println();

        /* test 2
        *   will test if write() overwrites and read() reads the updated(overwritten) memory */
        System.out.println("TEST 2 : overwrite");
        System.out.println("data = 11111111111111111111111111111111");
        System.out.println("address value = 1019");
        data = stringTolongword("11111111111111111111111111111111");
        address = stringTolongword("00000000000000000000001111111011"); // 1019 => this should overwrite 1020, 1021, 1022
        System.out.print("reading the address before write()... \nresult = ");
        result = memory.read(address);
        printLongword(result);
        memory.write(address, data);
        result = memory.read(address);
        System.out.print("reading the address after write()... \nresult = ");
        printLongword(result);
        address = stringTolongword("00000000000000000000001111111100"); // 1020
        System.out.print("reading the address 1020 again to check if it has changed... \nresult = ");
        result = memory.read(address);
        printLongword(result); // this should print 11111111111111111111111111111110
        System.out.println();

        /* test 3
        *   will test case of out of memory space */
        System.out.println("TEST 3 : read and write out of memory");
        System.out.println("address value = 1021");
        address = stringTolongword("00000000000000000000001111111101"); // 1021
        memory.write(address, data);
        memory.read(address);
        System.out.println();
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
