public class ALU {
    public static longword doOp(bit[] operation, longword a, longword b) {
        longword result = new longword();
        int digits = 5; // will use this number of digits when we convert longword to int => last 5 digits of longword b in this case.

        // bitsToString() converts the array of bits to a string
        switch(bitsToString(operation)) {
            case "1000": // and
                result = a.and(b);
                break;
            case "1001": // or
                result = a.or(b);
                break;
            case "1010": // xor
                result = a.xor(b);
                break;
            case "1011": // not
                result = a.not();
                break;
            case "1100": // left shift
                // left shift "a" by amount, which is generated from last 5 digits of "b" in form of decimal
                result = a.leftShift(amountOfShift(b, digits)); // method amountOfShift() converts binary to decimal
                break;
            case "1101": // right shift
                result = a.rightShift(amountOfShift(b, digits));
                break;
            case "1110": // add
                result = rippleAdder.add(a, b);
                break;
            case "1111": // subtract
                result = rippleAdder.subtract(a, b);
                break;
            case "0111": // multiply
                result = Multiply.multiply(a, b);
                break;
            default:
                System.out.println("Invalid operation.");
        }
        return result;
    }
    // helper function to convert bits(operation) to string in order to use in switch statement
    public static String bitsToString(bit[] bits) {
        String bitString = "";
        for (int i=0 ; i < bits.length ; i++) {
            // call method toString() of bit class to convert bit to string, then combine to resulting string
            bitString += bits[i].toString();
        }
        return bitString;
    }
    // helper function to generate the amount that decides how far to shift
    private static int amountOfShift(longword b, int digits) {
        /* generate an integer for shift cases using last 5 digits of longword "b" */
        // convert binary values of b's last 5 digits' bits (27 to 31) to integer
        // 27 ..< 31 == longword_size-digits ..< longword_size
        longword temp = new longword();
        // copy only last 5 digit's bit value to temp for the same position
        // method size() of class longword returns the value for longword_size
        for(int i=b.size()-digits; i<b.size(); i++) {
            temp.setBit(i, b.getBit(i));
        }
        return temp.getSigned(); // getSigned() : convert a longword in binary to int. We can use getSigned() since it cannot be negative.
    }
}
