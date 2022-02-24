// setBit() UPDATED on 10/14/2020
import java.lang.*; // to use Math.pow()

public class longword implements ILongword {
  private bit[] value;
  private final int LONGWORD_SIZE = 32;

  //constructor
  public longword() {
    value = new bit[LONGWORD_SIZE];
    // create an instance of Bit class for each index with default value 0
    for(int i=0; i<LONGWORD_SIZE; i++) {
      value[i] = new bit();
    }
  }

  /** methods **/
  // 1. getBit(int i) - get bit i
  public bit getBit(int i) {
    // return bit only if it's index is in range(0-31)
    if(i < LONGWORD_SIZE && i > -1)
      return value[i];
    else { // Handle Exception
      return null;
    }
  }

  // 2. setBit(int i, bit value) - set bit i's value
  public void setBit(int i, bit value) {
    // assign value only if the target index is in range(0-31)
    if(i < LONGWORD_SIZE && i > -1) {
      this.value[i].set(value.getValue());
    }
  }

  // 3. and(longword other) - "and" two longwords, returning a third
  public longword and(longword other) {
    longword result = new longword(); // a new longword to set to the result
    // perform 'and' operation in each index of the collection of bits
    for(int i=0; i<LONGWORD_SIZE; i++) {
      // for each index i, call the method and() in bit.java to set the result
      // value[i].and(other.getBit(i)) : returns a bit with value of the result of 'value[i] AND other'
      result.setBit(i, value[i].and(other.getBit(i)) );
    }
    return result;
  }

  // 4. or(longword other) - "or" two longwords, returning a third
  public longword or(longword other) {
    longword result = new longword();
    // perform 'or' operation in each index of the collection of bits
    for(int i=0; i<LONGWORD_SIZE; i++) {
      // for each index i, call the method or() in the 'bit' class to set the result
      // value[i].or(other.getBit(i)) : returns a bit with value of the result of 'value[i] OR other'
      result.setBit(i, value[i].or(other.getBit(i)) );
    }
    return result;
  }

  // 5. xor(longword other) - "xor" two longwords, returning a third
  public longword xor(longword other) {
    longword result = new longword();
    // perform 'xor' operation in each index of the collection of bits
    for(int i=0; i<LONGWORD_SIZE; i++) {
      // for each index i, call the method xor() in the 'bit' class to set the result
      // value[i].xor(other.getBit(i)) : returns a bit with value of the result of 'value[i] XOR other'
      result.setBit(i, value[i].xor(other.getBit(i)) );
    }
    return result;
  }

  // 6. not() - negate this longword, creating another
  public longword not() {
    longword result = new longword();
    // perform 'not' operation in each index of the collection of bits
    for(int i=0; i<LONGWORD_SIZE; i++) {
      // for each index i, call the method not() in the 'bit' class to set the result
      result.setBit(i, value[i].not());
    }
    return result;
  }

  // 7. rightShift(int amount) - rightshift this longword by amount bits, creating a new longword
  // right shift shall move all the digits in the binary number along to the right and fills the gaps after the shift with 0 
  // Approach: fill in 'result' with amount number of 0 first(from left side), 
  //          then fill in the rest with the values of the original bits('this.value')
  public longword rightShift(int amount) {
    longword result = new longword(); // here every bit of result is initialized to 0(by constructor) so it doesn't need to fill the gaps with 0 again
    // set 'result' with the original values of bits from index value of 'amount' to 31st index.
    for (int i=amount ; i < LONGWORD_SIZE ; i++) {
      result.setBit(i, this.value[i-amount]);
    }
    return result;
  }

  // 8. leftShift(int amount) - leftshift this longword by amount bits, creating a new longword
  // left shift shall move all the digits in the binary number along to the left and fills the gaps after the shift with 0
  // Approach: fill in 'result' with the values from the index of 'amount' of original bits(this.value) first(from left), 
  //          then fill in the rest with 'amount' number of 0 
  public longword leftShift(int amount) {
    longword result = new longword();
    // deal with result[0 to (32-amount-1)] since 'result' is already initialized with 0 by above line.
    for(int i=0; i< LONGWORD_SIZE - amount; i++) {
      //values in current index i of 'result' = values in index i+amount of 'this.value'
      result.setBit(i, this.value[i+amount]);
    }
    return result;
  }
  
  @Override
  // 9. toString() - returns a comma separated string of 0's and 1's: "0,0,0,0,0 (etcetera)" for example
  public String toString() {
    String strFormOfLongword = ""; // updating string of the collection of bits
    String strFormOfBit = ""; // string converted from a bit

    for(int i=0; i<LONGWORD_SIZE; i++) {
      strFormOfBit = String.valueOf(value[i].getValue()); // convert a bit to a String
      strFormOfLongword += strFormOfBit; // add strFormOfBit next to strFormOfLongword
      /** For later assignment, comma between each bit would not be efficient to use toString() so I comment out**/
//      if( i != LONGWORD_SIZE-1) // add comma after strFormOfLongword except for when last bit added
//        strFormOfLongword += ",";
    }
    return strFormOfLongword;
  }

  // 10. getUnsigned() - returns the value of this longword as a long
  // convert binary number stored in the variable 'value' to unsigned integer
  // because 'value' may have an unsigned integer value that can be store in 'int', the result needs to be 'long'
  public long getUnsigned() {
    double p=0.0; // double for Math.pow()
    long result = 0;

    // calc: (2^32 * 1st digit) + (2^31 * 2nd digit) + ... + (2^0 * last digit)
    // for each ith's, int value of value[i] * 2^(LONGWORD_SIZE-i-1)
    for(int i=0; i<LONGWORD_SIZE; i++) {
      p = LONGWORD_SIZE-i-1;
      // sum up the result after converting double to long using Math.round() 
      result += Math.round( value[i].getValue() * Math.pow(2.0, p) );
    }
    return result;
  }

  // 11. getSigned() - returns the value of this longword as an int
  // convert binary number stored in the variable 'value' to signed integer
  public int getSigned() {
    double p = 0.0;
    int result = 0;

    for(int i=1; i<LONGWORD_SIZE; i++) { // 0th value is for a sign identifier
      p = LONGWORD_SIZE-i-1;
      result += Math.round( value[i].getValue() * Math.pow(2.0, p) );
    }

    if(value[0].getValue() == 0) // positive
      return result;
    else // else if value == 1, negative
      return Math.round( result * (-1) ); // multiply result by -1 to make it negative, convert long to int
  }

  // 12. copy(longword other) - copies the value of the bits from another longword into this one
  public void copy(longword other) {
    for(int i=0; i<LONGWORD_SIZE; i++) {
      bit bit = new bit();
      bit.set(other.getBit(i).getValue());
      this.value[i] = bit;
      //value.setBit(i, other.getBit(i)); // set this.value's bits to other's bits
      //this.value[i] = other.getBit(i);
    }
  }

  // 13. set(int value) - set the value of the bits of this longword (used for tests)
  public void set(int value) { // signed integer -> binary number
      int i=31; // last idx
      // convert int 'value' to binary number and set each digit's value to this.value[i]
      while (value > 0) { // going backward
        this.value[i--].set(value % 2); // set each value of digit in longword with a result of each int value%2(remainder gives 0 or 1)
        value = value/2; // update value with value/2 for next step(loop)
      }

      // sign bit
      if (value < 0)
        this.value[0].set(1);
      else
        this.value[0].set(0);
  }

  // ADDED: helper function to return the fixed value for Object "longword" as a data type we are using (will use frequently)
  public int size() {
    return LONGWORD_SIZE;
  }
}
