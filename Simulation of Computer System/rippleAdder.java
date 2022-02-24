public class rippleAdder {
  /* Addition
        0+0=0
        0+1=1
        1+0=1
        1+1=10 
    - add up each column from right to left and express the answer in binary
    - low bit goes in the sum, high bit "carries" to the next column left
  Following are expressions for sum and carry based on the K-Maps(based on the truth table)
    - Result = A XOR B XOR C(IN)
    - C(OUT) = A AND B OR A AND C(IN) OR B AND C(IN)
  */
  public static longword add(longword a, longword b) {
    int lwSize = 32; // # of bits in longword
    longword result = new longword();

    bit cIN = new bit(); // carry in
    bit cOUT =new bit(); // carry out
    bit temp = new bit(); // to temporarily store a result of expression

    bit bitA = new bit();
    bit bitB = new bit();

    // add each column from right to left
    for( int i=lwSize-1; i>=0; i--) {
      bitA = a.getBit(i);
      bitB = b.getBit(i);

      // result of addition
      temp = ( bitA.xor(bitB) ).xor(cIN);
      result.setBit(i, temp);

      // carry
      // Operator Precedence : () -> AND -> OR
      // AND needs extra parentheses to be evaluated before OR
      temp = ( bitA.and(bitB) ).or( bitA.and(cIN) ).or( bitB.and(cIN) );
      cOUT = temp;

      //update cIN to cOUT, set for carry
      cIN = cOUT;
    }
    return result;
  }

  /* Subtraction
        0-0=0
        1-0=1
        1-1=0
        10-1=1
    - subtract in each column from rifht to left and express the answer in binary
    - subtracting 1 from 0 requires to "borrow" from the left
  Following are expressions for the difference and borrow based on the K-Maps(based on the truth table)
    - Result =    A.NOT AND B.NOT AND B(IN)
              OR  A AND B.NOT AND B(IN).NOT
              OR  A.NOT AND B AND B(IN).NOT
              OR  A AND B AND B(IN)
    - B(OUT) = A.NOT AND B(IN) OR A.NOT AND B OR B AND B(IN)
  */
  public static longword subtract(longword a, longword b) {
    int lwSize = 32;
    longword result = new longword();

    bit bIN = new bit(); // borrow in
    bit bOUT =new bit(); // borrow out
    bit temp = new bit();
 
    bit bitA = new bit();
    bit bitB = new bit();

    // Operator Precedence : () -> NOT -> AND -> XOR -> OR
    // seperate NOT parts from expression to make sure evaluating NOT before AND, XOR, OR
    bit bitANOT = new bit();
    bit bitBNOT = new bit();
    bit bINNOT = new bit();

    // subtract each column from right to left
    for( int i=lwSize-1; i>=0; i--) {
      bitA = a.getBit(i);
      bitB = b.getBit(i);
      // store result value after NOT() evaluated
      bitANOT = bitA.not();
      bitBNOT = bitB.not();
      bINNOT = bIN.not();

      // result of subtraction
      temp = ( (bitANOT.and(bitBNOT)).and(bIN) ).or( (bitA.and(bitBNOT)).and(bINNOT) ).or( (bitANOT.and(bitB)).and(bINNOT) ).or( (bitA.and(bitB)).and(bIN) );
      result.setBit(i, temp);

      // borrow
      temp = ( bitANOT.and(bIN) ).or( bitANOT.and(bitB) ).or( bitB.and(bIN) );
      bOUT = temp;

      //update bIN to bOUT, set for borrow
      bIN = bOUT;
    }
    return result;
  }
}
