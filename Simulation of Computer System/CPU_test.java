public class CPU_test {
    public static void main(String args[]) throws Exception {
        Computer cpu = new Computer();
        runTest1(cpu); // to test MOVE
        runTest2(cpu); // to test ALU
    }

    public static void runTest1(Computer cpu) throws Exception {
        /* test MOVE instruction */
        cpu = new Computer(); // create a new CPU
        // 19 Strings == 10 longwords
        String[] preloadingDataSets = { "0001001101010101", // move R0 00000000 (== 0 in dec)
                                        "0001000100000001", // move R1 00000001 (== 1 in dec)
                                        "0001001000000010", // move R2 00000010 (== 2 in dec)
                                        "0001001101010101", // move R3 01010101 (== 85 in dec)
                                        "0001010001111111", // move R4 01111111 (== 127)
                                        "0001010111110000", // move R5 11110000 (== -16)
                                        "0001011010000001", // move R6 10000001 (== -127)
                                        "0001011111111111", // move R7 11111111 (== -1)
                                        "0001011010000001", // move R6 10000001 (== -127)
                                        "0010000000000000", // // interrupt 0
                                        "0010000000000000", // // interrupt 0
                                        "0001100100011100", // move R11 00011100 (== 28)
                                        "0001110011111110", // move R12 11111110 (== -2)
                                        "0001110101010101", // move R13 10101010 (== -86)
                                        "0001111000111001", // move R14 00111001 (== 57)
                                        "0001111110001111", // move R15 10001111 (== -113)
                                        "0010000000000001", // interrupt 1
                                        "0010000000000001", // intterupt 1
                                        "0000000000000000"  // halt
                                };
        cpu.preload(preloadingDataSets);
        cpu.run();
    }

    public static void runTest2(Computer cpu) throws Exception {
        /* test MOVE instruction */
        cpu = new Computer(); // create a new CPU
        // 19 Strings == 10 longwords
        String[] preloadingDataSets = { "0001001101010101", // move R0 00000000 (== 0 in dec)  <<
                                        "0001000100000001", // move R1 00000001 (== 1 in dec)
                                        "0001001000000010", // move R2 00000010 (== 2 in dec)  <<
                                        "0001001101010101", // move R3 01010101 (== 85 in dec)
                                        "0001010001111111", // move R4 01111111 (== 127)  <<
                                        "0001010111110000", // move R5 11110000 (== -16)
                                        "0001011010000001", // move R6 10000001 (== -127)  <<
                                        "0001011111111111", // move R7 11111111 (== -1)
                                        "0001011010000001", // move R6 10000001 (== -127)  <<
                                        "0010000000000000", // // interrupt 0
                                        "0010000000000000", // // interrupt 0  <<
                                        "0001100100011100", // move R11 00011100 (== 28)
                                        "0001110011111110", // move R12 11111110 (== -2)  <<
                                        "0001110101010101", // move R13 10101010 (== -86)
                                        "0001111000111001", // move R14 00111001 (== 57) <<
                                        "0001111110001111", // move R15 10001111 (== -113)
                                        "0010000000000001", // interrupt 1
                                        "1000000100100011", // R3 = R1 AND R2
                                        "1110001101011000", // R8 = R3 + R5
                                        "1111001101000101", // R5 = R3 - R4
                                        "0010000000000000", // interrupt 0
                                        "0010000000000001", // interrupt 1
                                        "0000000000000000", // halt
                                        "0000000000000000",
                                        "1001011010101100",
                                        "1001011010101100"
                                };
        cpu.preload(preloadingDataSets);
        cpu.run();
    }
}
