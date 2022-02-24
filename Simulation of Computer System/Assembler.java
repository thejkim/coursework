public class Assembler {
    enum Keyword {
        AND, OR, XOR, NOT,
        LSHIFT, RSHIFT,
        ADD, SUB, MUL, MOV,
        INT, HLT, JMP,
        CMP, BRANCHIFEQUAL, BRANCHIFGREATER,
        PUSH, POP, CALL, RETURN
    }
    // Instruction Category, named with the number of its symbol(token)
    enum InstructionLength {
        L1, L2, L2B, L3A, L3B, L4
    }
    enum SymbolType {
        OPERATION_CODE,
        REGISTER,
        VALUE,
        EOL,
        UNKNOWN
    }

    // Constant values
    private static final int INSTRUCTION_LEN = 16;
    private static final int NUMBER_OF_REGISTERS = 16;
    private static final int MAX_VALUE = 127; // 8 bits = 127
    private static final int MIN_VALUE = -128; // 8 bits = -128
    private static final int MAX_REGISTER_NUM = NUMBER_OF_REGISTERS -1; // idx starts from 0
    private static final int MIN_REGISTER_NUM = 0;
    private static final int REGISTER_BIT_LEN = 4; // = (max)15
    private static final int OPERATION_CODE_BIT_LEN = 4; // opcode
    private static final int OP_JMP_VALUE_BIT_LEN = 12; // 'interrupt' value
    private static final int OP_MOVE_VALUE_BIT_LEN = 8; // 'move' value
    private static final int BIT_LEN_FIXED_TO_TEN = 10;

    private static String completeInstruction; // bit patterns that are represented by the given instruction(input)

    // Variables used only when an exception thrown.
    private static String currentOpCode; //
    private static String inputSymbol; //

    /*
        INTSTRUCTION : OPCODE{1}[ ]+((VALUE)|(REGISTER[ ]+(REGISTER|VALUE))
        OPCODE : L4  : ADD|SUB|MUL|LSHIFT|RSHIFT|AND|OR|XOR
                 L3B : MOVE
                 L3A : NOT|CMP
                 L2  : INT|JUMP|BRANCHIFEQUAL|BRANCHIFGREATER
                 L2B : PUSH|POP
                 L1  : HALT
     */
    // Indicate category for instruction allowed in this program
    private static InstructionLength determineLength(String operationCode) {
        InstructionLength length = null;
        switch (operationCode) {
            case "HLT" : // halt
            case "RETURN" :
                length = InstructionLength.L1;
                break;
            case "INT" : // interrupt
            case "JMP" : // jump
                length = InstructionLength.L2;
                break;
            case "MOV" : // move
                length = InstructionLength.L3B;
                break;
            case "NOT" : // not
            case "CMP" :
                length = InstructionLength.L3A;
                break;
            case "AND" : // and
            case "OR" : // or
            case "XOR" : // xor
            case "ADD" : // addition
            case "SUB" : // subtract
            case "MUL" : // multiply
            case "LSHIFT" : // left shift
            case "RSHIFT" : // right shift
                length = InstructionLength.L4;
                break;
            case "BRANCHIFEQUAL" : // branch if equal
            case "BRANCHIFGREATER" : // branch if greater
            case "CALL" :
                length = InstructionLength.L2;
                break;
            case "PUSH" :
            case "POP" :
                length = InstructionLength.L2B;
                break;

        }
        return length;
    }

    // Assemble given instructions
    public static String[] assemble(String[] instrunctions) {
        int numberOfInstruction = instrunctions.length;
        String[] bitPatterns = new String[numberOfInstruction];
        for(int i=0; i<numberOfInstruction; i++) {
            String inputLine = instrunctions[i];
            log(i + "\tgiven instruction\t  : " + inputLine);
            try {
                log("\tassembled instruction : " + instructionBitPatterns(inputLine));
                bitPatterns[i] = instructionBitPatterns(inputLine); // call the method that generate bit patterns that represent the given instruction
            } catch (Exception e) {
                log(e.getMessage());
            }
        }
        return bitPatterns;
    }

    // Fill the last unused(un-assigned) bits in 16 bit places
    private static String fillZero(String input) {
        String zeroFilledString = input;
        while (zeroFilledString.length() < INSTRUCTION_LEN) {
            zeroFilledString += "0";
        }
        return  zeroFilledString;
    }

    // Generate bit patterns that represent the given instruction
    private static String instructionBitPatterns(String input) throws Exception {
        SymbolReader sr = new SymbolReader(input); // lexical analyzer : separate incoming instruction(input) into symbol(token)
        completeInstruction = ""; // bit patterns that are represented by the given instruction(input)
        int L2length = 0;
        // Operation code must be the 1st token
        if (accept(SymbolType.OPERATION_CODE, sr, OPERATION_CODE_BIT_LEN)) {
            // Expect Register or Value
            InstructionLength length = determineLength(currentOpCode);

            switch (length) {
                case L1: // Syntax : opcode(4) // ex) HLT
                    if(!accept(SymbolType.EOL, sr, 0)) {
                        throw new Exception("\tSyntax Error : instruction exceeds its format.\n\tUsage : " + currentOpCode);
                    }
                    break;
                case L2: // Syntax : opcode(4) value(12) // ex) INT, JUMP, BRANCHIF, CALL
                    // specify bit length for value based on opcode
                    if(currentOpCode.equals("BRANCHIFEQUAL") || currentOpCode.equals("BRANCHIFGREATER") || currentOpCode.equals("CALL")) {
                        L2length = BIT_LEN_FIXED_TO_TEN;
                    } else { // interrupt, jump case
                        L2length = OP_JMP_VALUE_BIT_LEN;
                    }
                    if(accept(SymbolType.VALUE, sr, L2length)) { // value
                        if(!accept(SymbolType.EOL, sr, 0)) { // another symbol(token) taken when it's not allowed
                            throw new Exception("\tSyntax Error : instruction exceeds its format.\n\tUsage : " + currentOpCode + " value");
                        }
                    } else { // different with the allowed symbol type : must be VALUE
                        throw new Exception("\tSyntax Error : Operation [" + currentOpCode + "] requires a value.\n\tUsage : " + currentOpCode + " value");
                    }
                    break;
                case L2B: // Syntax : opcode(4)[00|01](2) register(10) // ex) PUSH, POP
                    if(accept(SymbolType.REGISTER, sr, BIT_LEN_FIXED_TO_TEN)) {
                        if(!accept(SymbolType.EOL, sr, 0)) {
                            throw new Exception("\tSyntax Error : instruction exceeds its format.\n\tUsage : " + currentOpCode + " SrcRegister.");
                        }
                    } else { // different with the allowed symbol type : must be REGISTER
                        throw new Exception("\tSyntax Error : Opertaion [" + currentOpCode + "] requires a source register. \n\tUsage : " + currentOpCode + " SrcRegister.");
                    }
                    break;
                case L3A: // Syntax : opcode(4) valueRegister(4) destinationRegister(4) // ex) NOT
                    if(accept(SymbolType.REGISTER, sr, REGISTER_BIT_LEN)) { // valueRegister
                        if(accept(SymbolType.REGISTER, sr, REGISTER_BIT_LEN)) { // destinationRegister
                            if(!accept(SymbolType.EOL, sr, 0)) {
                                throw new Exception("\tSyntax Error : instruction exceeds its format.\n\tUsage : " + currentOpCode + " SrcRegister DestRegister");
                            }
                        } else { // different with the allowed symbol type : must be REGISTER
                            throw new Exception("\tSyntax Error : Opertaion [" + currentOpCode + "] requires a source register and a destination register.\n\tUsage : " + currentOpCode + " SrcRegister DestRegister");
                        }
                    } else { // different with the allowed symbol type : must be REGISTER
                        throw new Exception("\tSyntax Error : Opertaion [" + currentOpCode + "] requires a source register and a destination register.\n\tUsage : " + currentOpCode + " SrcRegister DestRegister");
                    }
                    break;
                case L3B: // Syntax : opcode(4) valueRegister(4) value(4) // ex) MOV
                    if(accept(SymbolType.REGISTER, sr, REGISTER_BIT_LEN)) { // destinationRegister
                        if(accept(SymbolType.VALUE, sr, OP_MOVE_VALUE_BIT_LEN)) { // value
                            if(!accept(SymbolType.EOL, sr, 0)) {
                                throw new Exception("\tSyntax Error : instruction exceeds its format.\n\tUsage : " + currentOpCode + " Register Value");
                            }
                        } else { // different with the allowed symbol type : must be VALUE
                            throw new Exception("\tSyntax Error : Opertaion [" + currentOpCode + "] requires a destination register and a value to store.\n\tUsage : " + currentOpCode + " Register Value");
                        }
                    } else { // different with the allowed symbol type : must be REGISTER
                        throw new Exception("\tSyntax Error : Opertaion [" + currentOpCode + "] requires a destination register and a value to store.\n\tUsage : " + currentOpCode + " Register Value");
                    }
                    break;
                case L4: // Syntax : opcode(4) valueRegister(4) valueRegister(4) destinationRegister(4) // ex) ADD
                    if(accept(SymbolType.REGISTER, sr, REGISTER_BIT_LEN)) { // valueRegister
                        if(accept(SymbolType.REGISTER, sr, REGISTER_BIT_LEN)) { // valueRegister
                            if(accept(SymbolType.REGISTER, sr, REGISTER_BIT_LEN)) { // destinationRegister
                                if(!accept(SymbolType.EOL, sr, 0)) {
                                    throw new Exception("\tSyntax Error : instruction exceeds its format.\n\tUsage : " + currentOpCode + " SrcRegister1 SrcRegister2 DestRegister");
                                }
                            } else { // different with the allowed symbol type : must be REGISTER
                                throw new Exception("\tSyntax Error : Opertaion [" + currentOpCode + "] requires 2 source registers and a destination register.\n\tUsage : " + currentOpCode + " SrcRegister1 SrcRegister2 DestRegister");
                            }
                        } else { // different with the allowed symbol type : must be REGISTER
                            throw new Exception("\tSyntax Error : Opertaion [" + currentOpCode + "] requires 2 source registers and a destination register.\n\tUsage : " + currentOpCode + " SrcRegister1 SrcRegister2 DestRegister");
                        }
                    } else { // different with the allowed symbol type : must be REGISTER
                        throw new Exception("\tSyntax Error : Opertaion [" + currentOpCode + "] requires 2 source registers and a destination register.\n\tUsage : " + currentOpCode + " SrcRegister1 SrcRegister2 DestRegister");
                    }
                    break;
            }
        } else { // invalid keyword for opcode
            throw new Exception("\tSyntax Error : Operation code expected, but received unknown code [" + inputSymbol + "]");
        }
        //completeInstruction = "0000000000000000" + completeInstruction;
        //completeInstruction = completeInstruction;
        return completeInstruction;
    }

    // Convert each symbol(token) to bit pattern
    private static String symbolToBinary(String symbol, SymbolType type, int bitLength) throws Exception {
        String binary = null;
        if (type == SymbolType.OPERATION_CODE) {
            switch (symbol) {
                case "HLT":
                    binary = "0000";
                    break;
                case "INT":
                    binary = "0010";
                    break;
                case "MOV":
                    binary = "0001";
                    break;
                case "NOT":
                    binary = "1011";
                    break;
                case "AND":
                    binary = "1000";
                    break;
                case "OR":
                    binary = "1001";
                    break;
                case "XOR":
                    binary = "1010";
                    break;
                case "ADD":
                    binary = "1110";
                    break;
                case "SUB":
                    binary = "1111";
                    break;
                case "MUL":
                    binary = "0111";
                    break;
                case "LSHIFT":
                    binary = "1100";
                    break;
                case "RSHIFT":
                    binary = "1101";
                    break;
                case "JMP" :
                    binary = "0011";
                    break;
                case "CMP" :
                    binary = "01000000"; // 2nd 4 bits are always 0000
                    break;
                case "BRANCHIFEQUAL" :
                    binary = "010101"; // turn ON(==1) the bit(2nd) indicating equality
                    break;
                case "BRANCHIFGREATER" :
                    binary = "010110"; // turn ON(==1) the bit(1st) indicating difference
                    break;
                case "PUSH" :
                    binary = "011000";
                    break;
                case "POP" :
                    binary = "011001";
                    break;
                case "CALL" :
                    binary = "011010";
                    break;
                case "RETURN" :
                    binary = "011011";
                    break;
            }
        } else if (type == SymbolType.REGISTER) {
            try { // convert register # to binary
                binary = decimalToBinary(symbol.substring(1), MIN_REGISTER_NUM, MAX_REGISTER_NUM, bitLength);
            } catch (Exception e){
                log(e.getMessage());
            }
        } else if (type == SymbolType.VALUE) {
            try { // convert (int)value to binary
                binary =decimalToBinary(symbol, MIN_VALUE, MAX_VALUE, bitLength);
            } catch (Exception e){
                throw e;
            }
        }
        return binary;
    }

    // Convert decimal to binary
    private static String decimalToBinary(String decimal, int min, int max, int bitLength) throws Exception{
        String binary;
        int number = Integer.parseInt(decimal); // cast string to int
        if(currentOpCode.equals("JUMP") || currentOpCode.equals("CALL")) {
            min = 0;
            max = 1020;
        } else if(currentOpCode.equals("BRANCHIFGREATER") || currentOpCode.equals("BREANCHIFEQUAL")) {
            min = 0;
            max = 511; // signed max dec in 10 bits == 511
        }
        if(number >= min && number <= max) {
            // Get binary string of specific length (bitLength) with leading zeros
            binary = String.format("%" + bitLength + "s", Integer.toBinaryString(number)).replaceAll(" ", "0");
            if(binary.length() > bitLength) { // if negative, we only need last bitLength# digits
                binary = binary.substring(binary.length() - bitLength);
            }
        } else { // ERROR: Out of range
            throw new Exception("OutOfRange Exception : value can only take numbers from " + min + " to " + max + "\n\tReceived Value : " + number);
        }
        return binary;
    }

    // Take the next symbol(token)
    // Validate the symbol by comparing type
    // Concatenate (Additionally assign) the corresponding bit patterns to "completeInstruction" only if acceptable
    private static boolean accept(SymbolType expectedType, SymbolReader symbolReader, int bitLength) throws Exception {
        inputSymbol = symbolReader.next(); // get the next symbol(token)
        SymbolType inputType = symbolReader.type;

        if (expectedType.equals(inputType)) { // types match
            if (expectedType.equals(SymbolType.OPERATION_CODE)) { // store opcode to use for exception message
                currentOpCode = inputSymbol;
            }
            if (!expectedType.equals(SymbolType.EOL)) { // if it is not end of line == there is another symbol(token) rather than EOL
                completeInstruction += symbolToBinary(inputSymbol, inputType, bitLength);
            } else { // if EOL but there's unused(un-assigned) bits in 16 bit places
                completeInstruction = fillZero(completeInstruction);
            }
            return true;
        } else {
            return false;
        }
    }

    // Shortcut for printing out
    private static void log(String logString) {
        System.out.println(logString);
    }
}
