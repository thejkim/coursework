public class Computer {
    private Memory memory = new Memory(); // add a memory member
    private longword PC; // program counter
    private longword SP; // stack pointer
    /*** Just for testing purpose for assignment 9 and 10, I changed the variable "registers" to be accessable from other classes ***/
    public longword[] registers = new longword[16]; // array of 16 longwords : we got 16 registers
    longword currentInstruction;
    //second 4 bits, third 4 bits, fourth 4 bits
    longword bitSequence2;
    longword bitSequence3;
    longword bitSequence4;

    longword executeResult;
    longword destinationRegister;

    private bit isHalted;
    bit[] opcode;

    // global variables for assignment 9 - compare, jump, branch
    bit cmpIndicatorbit0 = new bit(); // set 0 if less than or 1 if greater than
    bit cmpIndicatorbit1 = new bit(); // set 0 if not equal or 1 if equal
    longword newAddressToJump;
    longword newAddressToBranch;

    public void run() throws Exception {
        PC = new longword(); // program counter (tracker) initialized to 0
        SP = new longword(); // initialize to 0
        bit bit1 = new bit();
        bit1.set(1);
        for(int i=22; i<30; i++) { // ==(dec)1020
            SP.setBit(i, bit1); // pre-initialize to (dec)1020
        }

        isHalted = new bit();
        isHalted.clear(); // default is false(==0) for now

        while(isHalted.getValue() == 0) {
            fetch();
            decode();
            executeResult = execute();
            if (executeResult != null) { // store
                store();
            } else {
                if (isHalted.getValue() == 1 ) { // halt
                    break;
                }
            }
        }
    }
    // fetch
    public void fetch() throws Exception {
        // store new instruction read from memory where PC is pointing to
        currentInstruction = memory.read(PC);
        logLn("---Key information that will be used in the process of the new instruction---");
        log("\tCurrent Instruction = "); printLongword(currentInstruction);
        logLn("\tPC = " + PC.getSigned());
        logLn("\tSP = " + SP.getSigned());

        // prepare to increment PC by 2
        longword toIncrementBy = new longword(); // to add to current PC
        bit bit1 = new bit(); // bit with value 1
        bit1.set(1);
        //toIncrementBy.setBit(27, bit1); // ==(dec)16 : 2 ahead in memory address(in bytes) == 16 bits
        /* FOR THIS ASSIGNMENT and convenience on testing to check if its functionality is working properly,
            we increment PC by (dec)2 so it points to every 16th bit position (as we preload with 16-bit Strings)
            in our 8,192-bits memorySpace
         */
        toIncrementBy.setBit(30, bit1); // ==(dec)2
        // update PC to points to the next longword
        if(rippleAdder.add(PC, toIncrementBy).getSigned() > 1020) {
            throw new Exception("Out Of Range: PC cannot point to higher than 1020.\n\tYour PC is trying to point to" + rippleAdder.add(PC, toIncrementBy).getSigned());
        } else {
            PC = rippleAdder.add(PC, toIncrementBy);
        }
    }
    // decode
    public void decode() {
        opcode = new bit[4]; // first 4 bits
        for(int i=0; i<opcode.length; i++) {
            opcode[i] = currentInstruction.getBit(16+i); // instruction is 16 bits, starting with opcode
        }
        longword temp = new longword();

        // to separate parts of instruction, we use shift
        /* when calculate amount of shift, 16 is base as longword is 32 bits and instruction is 16 bits
           so, leftmost 16 bits will never be used == we only use rightmost 16 bits */
        newAddressToJump = currentInstruction.leftShift(20); // get the rightmost 12 bits
        newAddressToJump = newAddressToJump.rightShift(20);
        newAddressToBranch = currentInstruction.leftShift(22); // get the rightmost 10 bits
        newAddressToBranch = newAddressToBranch.rightShift(22);

        temp = currentInstruction.leftShift(20);
        bitSequence2 = temp.rightShift(28);
        temp = currentInstruction.leftShift(24);
        bitSequence3 = temp.rightShift(28);
        temp = currentInstruction.leftShift(28);
        bitSequence4 = temp.rightShift(28);
    }
    // execute
    public longword execute() throws Exception{
        longword bytesPerAddressIndex = new longword();
        bit bit1 = new bit();
        bit1.set(1);
        bytesPerAddressIndex.setBit(29, bit1); // 4 bytes == 32 bits

        if (ALU.bitsToString(opcode).equals("0000")) { // halt
            System.out.println("------Halt------");
            isHalted.set();
            return null;
        } else if (ALU.bitsToString(opcode).equals("0001")) { // move
            System.out.println("------Move------");
            destinationRegister = bitSequence2;
            return bitSequencesToLongoword(bitSequence3, bitSequence4);
        } else if (ALU.bitsToString(opcode).equals("0011")) { // jump
            System.out.println("------Jump------");
            int jumpValue = newAddressToJump.getSigned();
            // Check if given address position is valid (positive)
            if(jumpValue >= 0) {
                destinationRegister = null; // we're not assigning any value to register
                return newAddressToJump; // return new address to jump to
            } else { // negative address value
                throw new Exception("ERROR: Cannot jump to the given negative address position at [" + jumpValue + "].");
            }
        } else if (ALU.bitsToString(opcode).equals("0100")) { // COMPARE
            longword operand1 = registers[bitSequence3.getSigned()];
            longword operand2 = registers[bitSequence4.getSigned()];

            bit[] tempOpCodeToSub = new bit[4];
            for(int i=0; i<4; i++) {
                tempOpCodeToSub[i] = bit1;
            }

            longword opResult = ALU.doOp(tempOpCodeToSub, operand1, operand2); // 1110 == add
            //printLongword(opResult);
            int signedIntResult = opResult.getSigned();
            if(signedIntResult == 0) { // equal
                logLn("equal!");
                cmpIndicatorbit1.set(1);
                cmpIndicatorbit0.set(0); // either 0 or 1 if equal => I choose 0 as default
            } else if(signedIntResult > 0) { // greater than
                logLn("greater!");
                cmpIndicatorbit0.set(1);
            } else if(signedIntResult < 0) { // less than
                logLn("less!");
                cmpIndicatorbit0.set(0);
            }
            destinationRegister = null; // we're not assigning any value to register
            return null;
        } else if (ALU.bitsToString(opcode).equals("0101")) { // BRANCHIF
            System.out.println("-----Branch if-----");
            /* opcode 0101 shares many names so we need to figure out which condition we are looking for
               - Assembler sets indicator bit corresponding to given input command when it translates human-friendly language(string) to computer-friendly language(binary)
                 1. if we need to check difference (BranchIfGreater) then 20th bit is ON(==1)
                 2. if we need to check equality (BranchIfEqual) then 21th bit is ON(==1)
               - Then here, we need to check if the indicator bit matches with the result of COMPARE
                 1. if currentInstruction.getBit(20) is ON(==1), then we need to check if opResult in comparison indicates it's greater
                    - cmpIndicatorBit0.getValue() == 1 if greater, 0 otherwise
                 2. if currentInstruction.getBit(21) is ON(==1), then we need to check if opResult in comparison indicates it's equal
                    - cmpIndicatorBit1.getValue() == 1 if equal, 0 otherwise
            */
            if(currentInstruction.getBit(20).getValue() == 1) { // check if greater
                if(cmpIndicatorbit0.getValue() == 1) {
                    System.out.println("Branch is taken.");
                    return newAddressToBranch;
                } else {
                    System.out.println("Branch is not taken.");
                    return null;
                }
            }
            if(currentInstruction.getBit(21).getValue() == 1) { // check if equal
                if(cmpIndicatorbit1.getValue() == 1) {
                    System.out.println("Branch is taken.");
                    return newAddressToBranch;
                } else {
                    System.out.println("Branch is not taken.");
                    return null;
                }
            }
            destinationRegister = null;
            return null;
        } else if (ALU.bitsToString(opcode).equals("0010")) { // interrupt
            System.out.println("------Interrupt------");
            if(bitSequence4.getSigned() == 0) {
                // print all of the registers to the screen
                System.out.println("---Printing all of the registers to the screen---");
                for(int i=0; i<registers.length; i++) {
                    System.out.print("Register " + i + " : ");
                    if(registers[i] != null) {
                        printLongword(registers[i]);
                    } else {
                        System.out.println("null");
                    }
                }
            } else if(bitSequence4.getSigned() == 1) {
                System.out.println("---Printing all 1024 bytes (separated by newLine for every 32 bits) of memory to the screen---");
                // print all 1024 bytes of memory to the screen
                bit[] bits = memory.dump(0, 1024*8-1);
                for(int i=0; i<1024*8; i++) {
                    System.out.print(bits[i]);
                }
                System.out.println();
            } else {
                System.out.println("Invalid command: Interrupt value is out of range");
            }
            return null;
        } else if (ALU.bitsToString(opcode).equals("0110")) {
            if(currentInstruction.getBit(20).getValue() == 1) { // call or return
                if(currentInstruction.getBit(21).getValue() == 1) { // return
                    System.out.println("------Return------");
                    PC = memory.read(SP); // pop RN(last thing in stack) off of the stack, then set PC to RN
                    logLn("[BEFORE] sp = "+SP.getSigned());

                    longword resetToZero = new longword(); // initialize to 0
                    memory.write(SP, resetToZero); // remove value at SP after pop

                    if(rippleAdder.add(SP, bytesPerAddressIndex).getSigned() > 1020) {
                        throw new Exception("Out Of Range: SP cannot point to higher than 1020.\n\tYour SP is trying to point to " + rippleAdder.add(SP, bytesPerAddressIndex).getSigned());
                    } else {
                        SP = rippleAdder.add(SP, bytesPerAddressIndex); // add 4 from the SP for the next pop
                        logLn("[AFTER] sp = "+SP.getSigned());
                    }

                } else { // call
                    System.out.println("------Call------");
                    memory.write(SP, PC); // push the current address(where we are at this point), which will be our return address(RN), onto the stack
                    PC = newAddressToBranch; // jump to given address, using newAddressToBranch as the last 10 bits hold the value for address

                    // Check if given address position is valid (positive)
                    int jumpValue = newAddressToBranch.getSigned();
                    if(jumpValue >= 0) {
                        destinationRegister = null; // we're not assigning any value to register
                        return newAddressToBranch; // return new address to jump to
                    } else { // negative address value
                        throw new Exception("ERROR: Cannot jump to the given negative address position at [" + jumpValue + "].");
                    }
                }
            } else { // push or pop
                if(currentInstruction.getBit(21).getValue() == 1) { // pop
                    System.out.println("------Pop------");
                    logLn("BEFORE pop, sp = "+SP.getSigned());
                    longword resetToZero = new longword(); // initialize to 0 (primitive type <int> cannot be null)
                    longword storedValue; // value popped off of the stack
                    if(rippleAdder.add(SP, bytesPerAddressIndex).getSigned() > 1020) {
                        throw new Exception("Out Of Range: SP cannot point to higher than 1020.\n\tYour SP is trying to point to" + rippleAdder.add(SP, bytesPerAddressIndex).getSigned());
                    } else {
                        SP = rippleAdder.add(SP, bytesPerAddressIndex); // add 4 from the SP for the next pop
                        storedValue = memory.read(SP); // get the value popped off of the stack to store to the given register
                        memory.write(SP, resetToZero); // remove(reset to zero) value at SP after pop
                        logLn("AFTER pop, sp = "+SP.getSigned());
                    }

                    // We're assigning the value got out off of the stack to the given register
                    destinationRegister = bitSequence4;
                    return storedValue;
                } else { // push
                    System.out.println("------Push------");
                    logLn("before push, sp = "+SP.getSigned());
                    memory.write(SP, registers[bitSequence4.getSigned()]);
                    printStack(); // just to check the stack after each push for test - it will print only the last 32 bytes in memory
                    SP = rippleAdder.subtract(SP, bytesPerAddressIndex); // subtract 4 from the SP for the next push
                    logLn("after push, sp = "+SP.getSigned());
                    destinationRegister = null; // we're not assigning any value to register
                }
            }
            return null;
        } else { // operate ALU
            System.out.println("------ALU------");
            longword operand1 = registers[bitSequence2.getSigned()];
            longword operand2 = registers[bitSequence3.getSigned()];
            destinationRegister = bitSequence4;
            return ALU.doOp(opcode, operand1, operand2); // store result of calculation(op1 opcode op2)
        }
    }
    // store
    public void store() {
        if(executeResult != null && destinationRegister != null) {
            if(destinationRegister.getSigned() <= 15) { // prevent storing to unavailable register (we only have 16)
                registers[destinationRegister.getSigned()] = executeResult;
            }
        } else if (executeResult != null && destinationRegister == null){
            if(ALU.bitsToString(opcode).equals("0011") || ALU.bitsToString(opcode).equals("0110")) { // jump, call
                // change PC to points to the given address
                PC = executeResult;
            } else { // branch
                // update PC by adding data(branch value) to PC
                PC = rippleAdder.add(PC, executeResult);
            }
        } // else, conditional branch has not taken. Does not update PC.
        newAddressToBranch = new longword(); // reset
        newAddressToJump = new longword(); // reset
    }

    // Preload the array of instructions to memory
    public void preload(String[] instructions) {
        longword memoryAddress = new longword();
        longword longword = new longword();
        bit bit = new bit();
        int size = 16; // each string is formed as collection of 16 bits

        int j=0;
        for(int i=0; i<instructions.length; i++) { // instructions.length = number of strings
            if(i%2 == 0) { // at even number'th (first 16 bits)
                for(j=0; j<size; j++) {
                    bit.set(Character.getNumericValue(instructions[i].charAt(j)));
                    longword.setBit(j, bit);
                }
            } else { // at odd number'th (last 16 bits)
                for(j=0; j<size; j++) {
                    bit.set(Character.getNumericValue(instructions[i].charAt(j)));
                    longword.setBit(j+16, bit);
                }
            }
            if(i%2 == 1 || i == instructions.length-1) { // if all 32 bits(2 strings) prepared or iF there's only one string(16 bits) left
                this.memory.write(memoryAddress, longword); // write to memory
                longword memOffset = new longword(); // prepare to increment the position to write in by 4
                bit.set(1);
                memOffset.setBit(29, bit);
                memoryAddress = rippleAdder.add(memoryAddress, memOffset); // increment by 4 (4 groups of 8-bits memory block)
            }
        }
    }

    // Convert bit sequences to a longword
    public longword bitSequencesToLongoword(longword sequence1, longword sequence2) {
        longword result = new longword(); // initialize to 0 : positive
        bit onBit = new bit();
        onBit.set(1); // a bit with value 1
        if(sequence1.getBit(28).getValue() == 1) { // initialize to 1 : negative
            for(int i=0; i<result.size(); i++) {
                result.setBit(i, onBit);
            }
        }
        for(int i=24; i<28; i++) {
            result.setBit(i, sequence1.getBit(i+4));
        }
        for (int i = 28; i < 32; i++) {
            result.setBit(i, sequence2.getBit(i));
        }
        return result;
    }

    public void printStack() {
        // print 32 bytes of memory from the end
        bit[] bits = memory.dump(0, 1024*8-1);
        System.out.println("---Printing the stack (last 32 bytes in memory)---\n...");
        int j=0;
        for(int i=992*8; i<1024*8; i++) {
            System.out.print(bits[i]);
            j++;
            if(j%8==0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    public void printLongword(longword data) {
        for(int i=0; i<data.size(); i++) {
            System.out.print(data.getBit(i).getValue());
        }
        System.out.println();
    }

    public bit[] stringToBits(String str) {
        bit[] opcode = new bit[4];
        bit singleBit = new bit();
        for(int i=0; i<4; i++) {
            singleBit.set(Character.getNumericValue(str.charAt(i)));
            opcode[i] = singleBit;
        }
        return opcode;
    }

    // Shortcut for printing out
    public void logLn(String str) {
        System.out.println(str);
    }
    public void log(String str) {
        System.out.print(str);
    }
}
