public class CPU_test2 {
    public static void main(String args[]) throws Exception {
        Computer cpu = new Computer();
        runTest1(cpu);
        runTest2(cpu);
        runTest3(cpu);
    }

    // Test if JUMP works correctly
    public static void runTest1(Computer cpu) throws Exception {
        System.out.println("--------test 1--------");
        cpu = new Computer(); // create a new CPU

        // only register3 should have value in it
        String[] seriesOfInstructions = {"MOV R0 0", "JMP 4", "MOV R1 5", "MOV R2 33", "INT 0", "MOV R3 7", "INT 0", "HLT"};

        // Assemble instructions and store in array of string
        String[] assembledResult = Assembler.assemble(seriesOfInstructions);

        cpu.preload(assembledResult); // load the assembly language program
        cpu.run(); // run cpu

        // Just for testing, we will put string value "null" to the case so that we can compare with pre-calculated result
        String[] programResult = new String[16];
        for(int i=0; i<cpu.registers.length; i++) {
            if(cpu.registers[i] == null) {
                programResult[i] = "null";
            } else {
                programResult[i] = cpu.registers[i].toString();
            }
        }
        // Expected result, pre-calculated
        String[] preCalculatedResult = { "null", "null", "null", "00000000000000000000000000000111",
                                    "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null"
        };

        // Test
        printTestResult(programResult, preCalculatedResult);
    }

    // Test if COMPARE works correctly
    public static void runTest2(Computer cpu) throws Exception {
        System.out.println("--------test 2--------");
        cpu = new Computer(); // create a new CPU

        /* register value
            R0      0
            R1      1
            R2      2
            R3      -1
            R4      -2
            R5      1
            R6      -1
         */
        /** every possible cases to test **/
        /*  a < b   : less!
            1   2   : CMP R1 R2
            -1  0   : CMP R3 R0
            -2  -1  : CMP R4 R3
         */
        /*  a > b   : greater!
            0   -1  : CMP R0 R3
            -1  -2  : CMP R3 R4
            2   1   : CMP R2 R1
         */
        /*  a == b  : equal!
            1   1   : CMP R1 R5
            -1  -1  : CMP R3 R6
            -2  -2  : CMP R2 R2
         */
        String[] seriesOfInstructions = {"MOV R0 0", "MOV R0 0", "MOV R1 1", "MOV R2 2", "MOV R3 -1", "MOV R4 -2", "MOV R5 1", "MOV R6 -1", "INT 0",
                                        "CMP R1 R2", "CMP R3 R0", "CMP R4 R3", // less!
                                        "CMP R0 R3", "CMP R3 R4", "CMP R2 R1", // greater!
                                        "CMP R1 R5", "CMP R3 R6", "CMP R2 R2", // equal!
                                        "HLT"};

        // Assemble instructions and store in array of string
        String[] assembledResult = Assembler.assemble(seriesOfInstructions);

        cpu.preload(assembledResult); // load the assembly language program
        cpu.run(); // run cpu
    }

    // Test if two BRANCHIF cases work correctly
    // ISSUE : branch compare works but PC doesn't seem to update properly
    public static void runTest3(Computer cpu) throws Exception {
        System.out.println("--------test 3--------");
        cpu = new Computer(); // create a new CPU

        /* register value
            R0      0
            R1      1
            R2      2
            R3      -1
            R4      -2
            R5      1
            R6      -1
         */
        /** every possible cases to test **/
        // 1. BRANCHIFGREATER
        /*  a > b   : greater!  => branch
            2   1   : CMP R2 R1
         */
        /*  a < b   : less!
            1   2   : CMP R1 R2
         */
        // 2. BRANCHIFEQUAL
        /*  a == b  : equal!    => branch
            1   1   : CMP R1 R5
         */
        /*  a != b  : not equal!
            1   2   : CMP R1 R2
         */
        String[] seriesOfInstructions = {"MOV R0 0", "MOV R0 0", "MOV R1 1", "MOV R2 2", "MOV R3 -1", "MOV R4 -2", "MOV R5 1", "MOV R6 -1", "INT 0",
                "CMP R0 R3", "BRANCHIFGREATER 2",  // greater, branch taken
                "MOV R7 7", "MOV R8 8", // must be skipped
                "CMP R1 R2", "BRANCHIFGREATER 2", // less, branch must not be taken
                "MOV R9 9", // must be operated
                "INT 0",
                "CMP R1 R5",  "BRANCHIFEQUAL 3", // equal, branch taken
                "MOV R10 10", "MOV R11 11", "MOV R12 12", // must be skipped
                "INT 0",
                "CMP R1 R2", "BRANCHIFEQUAL 1", // not equal, branch must not be taken
                "INT 0", "HLT"};

        // Assemble instructions and store in array of string
        String[] assembledResult = Assembler.assemble(seriesOfInstructions);

        cpu.preload(assembledResult); // load the assembly language program
        cpu.run(); // run cpu
    }

    // Compare program result with the expected result, print test result for each instruction
    private static void printTestResult(String[] assembledResult, String[] preCalculatedResult) {
        System.out.println("\n------------------- Test Result --------------------\n" +
                "\tprogramResult\t <-> preCalculatedResult\n" +
                "----------------------------------------------------");
        for(int i=0; i<preCalculatedResult.length; i++ ) {
            System.out.print(i+"\t" + assembledResult[i] + " <-> " + preCalculatedResult[i]);
            if( assembledResult[i].equals( preCalculatedResult[i] ) || (assembledResult[i] == null && preCalculatedResult[i] == null)) {
                System.out.println(" : success!");
            } else {
                System.out.println(" : failed.");
            }
        }
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

    private static void printArrayOfStrings(String[] array) {
        for(int i=0; i<array.length; i++) {
            System.out.println(array[i]);
        }
    }
}
