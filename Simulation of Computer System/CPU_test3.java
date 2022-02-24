public class CPU_test3 {
    public static void main(String args[]) throws Exception {
        Computer cpu = new Computer();
        runTest1(); // Assembler test for new instructions
        runTest2(cpu); // Test for "push" case
        runTest3(cpu); // Test for "push" + "pop" case
        runTest4(cpu); // Test for "push", "pop", + "call" case
        runTest5(cpu); // Test for "push", "pop", "call" + "return" case
    }

    // Test Case 1: Test if Assembler assembles the new instructions to binary patterns correctly
    public static void runTest1() throws Exception {
        System.out.println("--------test 1--------");

        // only register3 should have value in it
        String[] seriesOfInstructions = {"PUSH R1", "POP R2", "CALL 3", "RETURN", // valid instruction
                                        "PUSH 3", "POP 10", "CALL R1", "RETURN 0", // invalid. Syntax error.
                                        "PUSH R1 R2", "CALL 3 R1"}; // invalid. Syntax error.

        // Assemble instructions and store in array of string
        String[] assembledResult = Assembler.assemble(seriesOfInstructions);

        // When there's exception, it takes null
        // So just for testing, we will put string value "null" to the case so that we can compare with pre-calculated result
        assembledResultExceptionValueToString(assembledResult);

        // Expected result, pre-calculated
        String[] preCalculatedResult = { "0110000000000001", "0110010000000010",
                "0110100000000011", "0110110000000000",
                "null", "null", "null", "null", "null", "null"
        };

        // Test
        printTestResult(assembledResult, preCalculatedResult);
    }

    // Test Case 2: Test if "push" works correctly (whether it pushes onto the stack at the right position)
    public static void runTest2(Computer cpu) throws Exception {
        System.out.println("--------test 2--------");
        cpu = new Computer(); // create a new CPU

        // value of R1 should be on the stack at memory address 1020
        String[] seriesOfInstructions = {"INT 0", "MOV R1 10", "INT 0", "PUSH R1", "INT 1", "HLT"};

        // Assemble instructions and store in array of string
        String[] assembledResult = Assembler.assemble(seriesOfInstructions);

        cpu.preload(assembledResult); // load the assembly language program
        cpu.run(); // run cpu
    }

    // Test Case 3: Test if "pop" also works correctly by checking whether
    //  1. it pops the right value from the stack at the right position,
    //  2. it stores the value popped off of the stack to the given register,
    //  3. it removes(reset to zero) data from memory once pop done
    public static void runTest3(Computer cpu) throws Exception {
        System.out.println("--------test 3--------");
        cpu = new Computer(); // create a new CPU

        // value of R1 should be on the stack at memory address 1020
        String[] seriesOfInstructions = {"INT 0", "MOV R1 10", "INT 0", "PUSH R1", "INT 1",
                                        "POP R2", // store the value popped off of the stack into R2
                                        "INT 0",  // print out registers to see if R2 has the value of R1
                                        "INT 1", "HLT"}; // print out memory to see if the space hold R1 on the stack is being empty(zero)

        // Assemble instructions and store in array of string
        String[] assembledResult = Assembler.assemble(seriesOfInstructions);

        cpu.preload(assembledResult); // load the assembly language program
        cpu.run(); // run cpu
    }

    // Test Case 4: Test if "call" also works correctly
    public static void runTest4(Computer cpu) throws Exception {
        System.out.println("--------test 4--------");
        cpu = new Computer(); // create a new CPU

        // value of R1 should be on the stack at memory address 1020
        String[] seriesOfInstructions = {"INT 0", // should be ignored
                                        "MOV R1 5", "MOV R2 10",
                                        "INT 0",
                                        "PUSH R1", "PUSH R2",
                                        "INT 1",
                                        "CALL 16",
                                        "MOV R5 20", // MUSH BE SKIPPED after "CALL 16"
                                        "ADD R1 R2 R3", // MUSH BE EXECUTED after "CALL 16"
                                        "INT 0", // print registers to see if the result of R1+R2 is stored in R3
                                                    // and to see if R5 is not holding any value in it
                                        "HLT"};

        // Assemble instructions and store in array of string
        String[] assembledResult = Assembler.assemble(seriesOfInstructions);

        cpu.preload(assembledResult); // load the assembly language program
        cpu.run(); // run cpu

        // Just for testing, we will put string value "null" to the case of exception thrown so that we can compare with pre-calculated result
        String[] programResult = new String[16];
        programResultExceptionValueToString(cpu, programResult);

        // Expected result, pre-calculated
        String[] preCalculatedResult = {
                "null", "00000000000000000000000000000101", "00000000000000000000000000001010", "00000000000000000000000000001111",
                "null", "null", "null", "null",
                "null", "null", "null", "null",
                "null", "null", "null", "null"
        };

        // Test
        printTestResult(programResult, preCalculatedResult);
    }

    // Test Case 5: Test if "return" also works correctly
    public static void runTest5(Computer cpu) throws Exception {
        System.out.println("--------test 5--------");
        cpu = new Computer(); // create a new CPU

        // Checking point:
        //  1. R5 has a value in it,
        //  2. R9 does NOT have a value in it
        //  If so, test is success.
        String[] seriesOfInstructions = {"INT 0", // should be ignored // PC=0
                "MOV R1 5", "MOV R2 10", // PC=0, 2
                "INT 0", // PC=4
                "PUSH R1", "PUSH R2", // PC=6, 8
                "INT 0", // PC=10
                "CALL 22", // PC=12
                "MOV R5 20", // MUSH BE SKIPPED after "CALL 22" but MUSH BE EXECUTED after "RETURN" // PC=14
                "INT 0", // PC=16
                "INT 1", // PC=18
                "HLT", // test ends at this point // PC=20
                "ADD R1 R2 R3", // MUSH BE EXECUTED after "CALL 22" // PC=22
                "RETURN", // at this point, PC will be pointing back to right after "CALL 20" (PC=14) // PC=24
                "MOV R9 30", // MUST NOT BE EXECUTED => NO value should be stored in R9
                "INT 0", // MUST BE NOT EXECUTED
                "HLT"}; // MUST BE NOT EXECUTED

        // Assemble instructions and store in array of string
        String[] assembledResult = Assembler.assemble(seriesOfInstructions);

        cpu.preload(assembledResult); // load the assembly language program
        cpu.run(); // run cpu

        // Just for testing, we will put string value "null" to the case of exception thrown so that we can compare with pre-calculated result
        String[] programResult = new String[16];
        programResultExceptionValueToString(cpu, programResult);

        // Expected result, pre-calculated
        String[] preCalculatedResult = {
                "null", "00000000000000000000000000000101", "00000000000000000000000000001010", "00000000000000000000000000001111",
                "null", "00000000000000000000000000010100", "null", "null",
                "null", "null", "null", "null",
                "null", "null", "null", "null"
        };

        // Test
        printTestResult(programResult, preCalculatedResult);
    }

    // Used for Assembler test - Put string value "null" to the case of exception thrown
    private static void assembledResultExceptionValueToString(String[] assembledResult) {
        for(int i=0; i<assembledResult.length; i++) {
            if(assembledResult[i] == null) {
                assembledResult[i] = "null";
            }
        }
    }
    // Used for cpu test (status of registers) - Put string value "null" to the case of exception thrown, or copy the value of what registers are holding
    private static void programResultExceptionValueToString(Computer cpu, String[] programResult) {
        for(int i=0; i<cpu.registers.length; i++) {
            if(cpu.registers[i] == null) {
                programResult[i] = "null";
            } else {
                programResult[i] = cpu.registers[i].toString();
            }
        }
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
