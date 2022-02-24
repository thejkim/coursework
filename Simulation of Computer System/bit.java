public class bit implements IBit {
  private int value; // bit's value

  // constructor
  public bit() {
      value = 0; // initialize to 0
  }

  /** methods **/
  // 1. set(int value) - sets the value of the bit
  // need to check if the passed value is valid or not
  public void set(int value) {
      if(value == 0 || value == 1) // valid value = 0, 1
          this.value = value; // set the bit's(this) value to the passed value
      else // invalid value, throw exception
          throw new IllegalArgumentException("ERROR: fail to validate.");
  }

  // 2. toggle() - changes the value from 0 to 1 or 1 to 0
  public void toggle() {
     if(value == 0) // 0 to 1
         value = 1;
     else // 1 to 0
         value = 0;
  }

  // 3. set() - sets the bit(value) to 1
  public void set() {
      value = 1;
  }

  // 4. clear() - sets the bit(value) to 0
  public void clear() {
      value = 0;
  }

  // 5. getValue() - returns the current value
  public int getValue() {
      return value;
  }

  // 6. and(bit other) - performs 'and' on two bits and returns a new bit set to the result
  // expected inputs and outputs : 0&0=0, 0&1=0, 1&0=0, 1&1=1
  public bit and(bit other) {
      bit result = new bit(); // a new bit to set to the result
      if(value == 1 && other.getValue() == 1)  // both values == 1, then set the result to 1
        result.set();
      else  // else, set the result to 0
        result.clear();
      return result;
  }

  // 7. or(bit other) - performs 'or' on two bits and returns a new bit set to the result
  // expected inputs and outputs : 0|0=0, 0|1=1, 1|0=1, 1|1=1
  public bit or(bit other) {
      bit result = new bit();
      if(value == 0 && other.getValue() == 0) // both values == 0, then set the result to 0
        result.clear();
      else // else, set the result to 1
        result.set();
      return result;
  }

  // 8. xor(bit other) - performs 'xor' on two bits and returns a new bit set to the result
  // expected inputs and outputs : 0^0=0, 0^1=1, 1^0=1, 1^1=0
  public bit xor(bit other) {
      bit result = new bit();
      if(value == other.getValue()) // if both values are the same, then set the result to 0
         result.clear();
      else // both values are not the same, then set the result to 1
         result.set();
      return result;
  }

  // 9. not() - performs 'not' on the existing bit, returning the result as a new bit
  // change the value to the other value = toggle()
  public bit not() {
     bit result = new bit();
     result.set(value); // set the result value to the current value before toggle
     result.toggle();
     return result;
  }

  // 10. toString() - returns "0" or "1"
  @Override
  public String toString() {
     //if it is allowed to use String.valueOf(int);
     String str = String.valueOf(value);
     return str;

     //if NOT allowed,
     //return ""+value;
  }
}
