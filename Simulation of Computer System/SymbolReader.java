public class SymbolReader {
    public String input;
    Assembler.SymbolType type = Assembler.SymbolType.UNKNOWN;

    // Constructor
    public SymbolReader() {
        this.input = null;
    }
    public SymbolReader(String input) {
        this.input = input;
    }

    // Get next token (symbol) from the given input, separated by a blank space
    public String next() {
        String symbol = null;
        Character character;
        while (input != null && !input.equals("")) {
            character = input.charAt(0);
            input = input.substring(1);
            if (character == ' ' || input.equals("")) {
                if(input.equals("") ) {
                    if(symbol==null) {
                        symbol = ""; // remove "null" when it's only one character
                    }
                    symbol += character;
                }
                determinateType(symbol);
                return symbol;
            }
            if (symbol==null) { // null
                symbol = character.toString();
            } else {
                symbol += character;
            }
        }
        if (input == null || input.isEmpty()) { // end of line
            determinateType("\\EOL");
        }
        return symbol;
    }

    public void determinateType(String symbol) {
        type = Assembler.SymbolType.UNKNOWN; // reset
        // Operation
        if (symbol.equals(Assembler.Keyword.HLT.name()) || symbol.equals(Assembler.Keyword.INT.name()) ||
                symbol.equals(Assembler.Keyword.MOV.name()) || symbol.equals(Assembler.Keyword.NOT.name()) ||
                symbol.equals(Assembler.Keyword.AND.name()) || symbol.equals(Assembler.Keyword.OR.name()) ||
                symbol.equals(Assembler.Keyword.XOR.name()) || symbol.equals(Assembler.Keyword.LSHIFT.name()) ||
                symbol.equals(Assembler.Keyword.RSHIFT.name()) || symbol.equals(Assembler.Keyword.ADD.name()) ||
                symbol.equals(Assembler.Keyword.SUB.name()) || symbol.equals(Assembler.Keyword.MUL.name()) ||
                symbol.equals(Assembler.Keyword.JMP.name()) || symbol.equals(Assembler.Keyword.CMP.name()) ||
                symbol.equals(Assembler.Keyword.BRANCHIFEQUAL.name()) || symbol.equals(Assembler.Keyword.BRANCHIFGREATER.name()) ||
                symbol.equals(Assembler.Keyword.PUSH.name()) || symbol.equals(Assembler.Keyword.POP.name()) ||
                symbol.equals(Assembler.Keyword.CALL.name()) || symbol.equals(Assembler.Keyword.RETURN.name())
                ) {
                type = Assembler.SymbolType.OPERATION_CODE;
        } else if (symbol.charAt(0) == 'R' && Integer.parseInt(symbol.substring(1)) < 16) { // Register
                type = Assembler.SymbolType.REGISTER;
        } else if (symbol.equals("\\EOL")) { // End of Line
            type = Assembler.SymbolType.EOL;
        } else { // Value
            try {
                int value = Integer.parseInt(symbol);
                type = Assembler.SymbolType.VALUE; // Value
            } catch (Exception e) {
                type = Assembler.SymbolType.UNKNOWN; // Unknown
            }
        }
    }
}
