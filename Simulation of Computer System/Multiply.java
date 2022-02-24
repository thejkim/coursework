public class Multiply {
    public static longword multiply(longword a, longword b) {
        longword result = new longword();
        longword temp = new longword(); // temporarily hold a single result for each addition to be summed up to the cumulated result(final)
        int longword_size = 32;

        // loop only for lower 32 bits as we ignore upper 32 bits.
        for(int i=0; i<longword_size; i++) {
            // if current digit's value of 'b' == 1,
            //  then result should be the same as 'a' shifted to left by the amount of the current digit's position(from right to left)
            //  if/else selection statement starts the comparison from the end (right to left) => longword_size - 1 - i
            // else fill with 0
            if (b.getBit(longword_size-i-1).getValue() == 1) {
                temp = a.leftShift(i); // left shift by i(current bit's position in b)
            } else {
                temp = new longword(); // initialize to 0
            }
            result = rippleAdder.add(result, temp); // add temp result to the cumulated result, and update the cumulated result to it.
        }
        return result;
    }
}
