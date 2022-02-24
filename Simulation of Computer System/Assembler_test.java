public class Assembler_test extends Exception {
    public static void main(String args[]) {
        // Instructions consist of all possible cases(successes and expections) with expected(pre-calculated) result
        // 28 success cases, 15 expection(error) cases
        String[] seriesOfInstructions = {"MOV R1 1", // 0001000100000001 // R1 = 1
                                "MOV R2 2", // 10 // 0001001000000010
                                "MOV R3 3",// 11 // 0001001100000011
                                "MOV R4 -40", // 11011000 // 0001010011011000
                                "MOV R5 50", // 110010 // 0001010100110010
                                "MOV R6 60", // 111100 // 0001011000111100
                                "MOV R7 -70", // 10111010 // 0001011110111010
                                "MOV R8 80", // 1010000 // 0001100001010000
                                "MOV R9 90", // 1011010 // 0001100101011010
                                "MOV R10 -100", // 10011100 // 0001101010011100
                                "MOV R2 110", // 1101110 // 0001001001101110
                                "MOV R12 127", // 1111111 // 0001110001111111
                                "MOV R13 -128", // 10000000 // 0001110110000000
                                "INT 0", "INT 1", // print registers, memory // 0010000000000000, 0010000000000001
                                "NOT R1 R2", // R2=NOT(R1) = -2, 11111110 // 1011000100100000
                                "AND R4 R5 R4", // R4=R4 AND R5 = 16, 00010000 // 1000010001010100
                                "OR R5 R6 R7", // R7=R5 OR R6 = 62, 00111110 // 1001010101100111
                                "XOR R7 R8 R9", // R9=R7 XOR R8 = -22, 11101010 // 1010011110001001
                                "LSHIFT R10 R1 R12", // R12=R10.leftShift(R1) = 56, 00111000 // 1100101000011100
                                "RSHIFT R11 R3 R13", // R13=R11.rightShift(R3) = 112, 01110000 // 1101101100111101
                                "ADD R1 R2 R14", // R14=R1+R2 = 3, 00000011 // 1110000100101110
                                "SUB R5 R9 R2", // R2=R5-R9 = -40, 11011000 // 1111010110010001
                                "MUL R3 R4 R15", // R15=R3*R4 = -120, 10001000 // 0111001101001111
                                "MUL R3 R5 R15", // R15=R3*R5 = -150 // TODO: For later assignment, MAY NEED TO HANDLE if the stored value is out of range
                                "INT 0", "INT 1", // print registers, memory // 0010000000000000, 0010000000000001
                                "CMD", // MUST FAIL : Syntax error(invalid opcode)
                                "MOV R14 128", // MUST FAIL: out of range // 13th case
                                "MOV R15 -129", // MUST FAIL : out of range
                                "INT 2", //  MUST FAIL : interrupt value is out of range
                                "MOV R1 R2", // MUST FAIL : Syntax error
                                "MOV 10 20", // MUST FAIL : Syntax error
                                "ADD 10 R2 R3", // MUST FAIL : Syntax error
                                "OR R7 153 R5", // MUST FAIL : Syntax error
                                "LSHIFT R2 R10 3", // MUST FAIL : Syntax error
                                "NOT 123 R9", // MUST FAIL : Syntax error
                                "NOT R9 123", // MUST FAIL : Syntax error
                                "NOT 12 34", // MUST FAIL : Syntax error
                                "MOV R1 100 R2", // MUST FAIL : Syntax error - exceeds
                                "SUB R5 R4 R4 R4", // MUST FAIL : Syntax error - exceeds
                                "INT 0 1", // MUST FAIL : Syntax error - exceeds
                                "HLT" // halt // 0000000000000000
        };

        // Assemble instructions and store in array of string
        String[] assembledResult = Assembler.assemble(seriesOfInstructions);

        // When there's exception, it takes null
        // So just for testing, we will put string value "null" to the case so that we can compare with pre-calculated result
        for(int i=0; i<assembledResult.length; i++) {
            if(assembledResult[i] == null) {
                assembledResult[i] = "null";
            }
        }
        // Expected result, pre-calculated
        String[] preCalculatedResult = { "0001000100000001",
                "0001001000000010",
                "0001001100000011",
                "0001010011011000",
                "0001010100110010",
                "0001011000111100",
                "0001011110111010",
                "0001100001010000",
                "0001100101011010",
                "0001101010011100",
                "0001001001101110",
                "0001110001111111",
                "0001110110000000",
                "0010000000000000",
                "0010000000000001",
                "1011000100100000",
                "1000010001010100",
                "1001010101100111",
                "1010011110001001",
                "1100101000011100",
                "1101101100111101",
                "1110000100101110",
                "1111010110010010",
                "0111001101001111",
                "0111001101011111",
                "0010000000000000",
                "0010000000000001",
                "null", "null", "null", "null", "null",
                "null", "null", "null", "null", "null",
                "null", "null", "null", "null", "null",
                "0000000000000000"
        };

        // Test
        printTestResult(assembledResult, preCalculatedResult);

    }
    // Compare program result with the expected result, print test result for each instruction
    private static void printTestResult(String[] assembledResult, String[] preCalculatedResult) {
        System.out.println("\n------------------- Test Result --------------------\n" +
                        "\tassembedResult\t <-> preCalculatedResult\n" +
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
    private static void printArrayOfStrings(String[] array) {
        for(int i=0; i<array.length; i++) {
            System.out.println(array[i]);
        }
    }
}
