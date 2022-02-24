public class longword_test {
	public static void main(String[] args) {
		longword lw1 = new longword(); // longword1 as left longword
   		longword lw2 = new longword(); // longword2 as right longword
   		bit bit0 = new bit(); // to hold a bit with value of 0
   		bit bit1 = new bit(); // to hold a bit with value of 1

   		runTests(bit0, bit1, lw1, lw2); // call the test class
	}

	/* Sample dataset and expected result for each method
	2 sample longwords for all case (0 with 0), (0 with 1), (1 with 0), (1 with 1) 
		lw1 		= 0...10001101 -> valueInIdx(24, 28, 29, 31) == 1
		lw2 		= 0...01010100 -> valueInIdx(25, 27, 29) == 1
	Expected results
		lw1 AND lw2 : 0...00000100 -> valueInIdx(29) == 1
		lw1 OR  lw2 : 0...11011101 -> valueInIdx(24, 25, 27, 28, 29, 31) == 1 
		lw1 XOR lw2	: 0...11011001 -> valueInIdx(24, 25, 27, 28, 31) == 1
		NOT lw1 	: 1...01110010 -> valueInIdx(0-23, 25, 26, 27, 30) == 1

		lw1, RIGHT SHIFT 3 : 0...00000010001 -> valueInIdx(27, 31) == 1
		lw1, LEFT SHIFT 3  : 0...10001101000 -> valueInIdx(21, 25, 26, 28) == 1 

		lw1 to Unsigned Integer : 141
		lw1 to Signed Integer : 141
		lw1 with value of 1 in idx value of 0 to Signed Integer : -141

		set(value) - signed integer 2147483647 to binary number = 01111111111111111111111111111111
	*/
	
	static public void runTests(bit bit0, bit bit1, longword lw1, longword lw2) {
		int longword_size = 32;
		// setBit, getBit works
		bit0.set(0); // a bit with value of 0
		bit1.set(1); // a bit with value of 1

		lw1.setBit(0, bit1); // set lw1's first digit to 1
		System.out.println("setBit(0, bit1) : " + (lw1.getBit(0).getValue() == 1 ? "pass" : "fail") ); // 1
		System.out.println("getBit(5, bit1) : " + (lw1.getBit(5).getValue() == 0 ? "pass" : "fail") ); // 0
		lw1.setBit(0, bit0); // reset for next tests
		
		//lw1 = 0...10001101 -> valueInIdx(24, 28, 29, 31) == 1
		lw1.setBit(24, bit1);
		lw1.setBit(28, bit1);
		lw1.setBit(29, bit1);
		lw1.setBit(31, bit1);

		//lw2 = 0...01010100 -> valueInIdx(25, 27, 29) == 1
		lw2.setBit(25, bit1);
		lw2.setBit(27, bit1);
		lw2.setBit(29, bit1);

		//temporary longword to store result dataset and to print out for test
		longword tempRes;

		// and() works
		//lw1 AND lw2 = 0...00000100 -> valueInIdx(29) == 1
		tempRes = new longword(); // initialize to 0
		tempRes = lw1.and(lw2); // call mathod and() in longword class
		// success case: if tempRes[29].value == 1
		System.out.println("[Must Pass] lw1 AND lw2 : " + (tempRes.getBit(29).getValue() == 1 ? "pass" : "fail") );
		// failure case: if tempRes[29].value == 0
		System.out.println("[Must Fail] lw1 AND lw2 : " + (tempRes.getBit(29).getValue() == 0 ? "pass" : "fail") );

		/* just in case TO PRINT out tempRes*/
		// for(int i=0; i<longword_size; i++) {
		// 	System.out.print(tempRes.getBit(i).getValue());
		// }
		
		// or() works
		//lw1 OR lw2 = 0...11011101 -> valueInIdx(24, 25, 27, 28, 29, 31) == 1
		tempRes = new longword(); // initialize to 0
		tempRes = lw1.or(lw2); // call mathod or() in longword class

		// countBit1 MUST == countMatch in the next tests for operations(or, xor)
		int countBit1=0; // count number of bit value 1 in the returned longword after an operation called
		int countMatch=0; // count if the certain index's value is the expected value

		// count number of bit value 1
		for(int i=0; i<longword_size; i++) {
			if(tempRes.getBit(i).getValue() == 1)
				countBit1++;
		}

		int[] indexArray;
		indexArray = new int[] {24, 25, 27, 28, 29, 31}; // these are indexes to check if their values are 1
		for(int i=0; i<indexArray.length; i++) {
			if(tempRes.getBit(indexArray[i]).getValue() == 1) // if where 1 is the same as the idx
				countMatch++;
		}
		//if countBit1 == countMatch, it works correctly
		System.out.println("lw1 OR lw2 : " + (countBit1 == countMatch ? "pass" : "fail") );

		// xor() works
		// lw1 XOR lw2 : 0...11011001 -> valueInIdx(24, 25, 27, 28, 31) == 1
		countBit1 = 0; // reset for next use
		countMatch = 0;
		tempRes = new longword(); // initialize to 0
		tempRes = lw1.xor(lw2); // call mathod xor() in longword class
		for(int i=0; i<longword_size; i++) {
			if(tempRes.getBit(i).getValue() == 1)
				countBit1++;
		}

		indexArray = new int[] {24, 25, 27, 28, 31}; // these are indexes to check if their values are 1
		for(int i=0; i<indexArray.length; i++) {
			if(tempRes.getBit(indexArray[i]).getValue() == 1)
				countMatch++;
		}
		System.out.println("lw1 XOR lw2 : " + (countBit1 == countMatch ? "pass" : "fail") );

		// not() works
		// NOT lw1 : 1...01110010 -> valueInIdx(0-23, 25, 26, 27, 30) == 1
		countMatch = 0; // count if NOT match
		tempRes = new longword(); // initialize to 0
		tempRes = lw1.not(); // call mathod not() in longword class
		for(int i=0; i<longword_size; i++) {
			if( tempRes.getBit(i).getValue() != lw1.getBit(i).getValue() ) { // if both bit in same idx isn't same, working correctly
				countMatch++;
			}
		}
		System.out.println("lw1.NOT() : " + (countMatch == longword_size ? "pass" : "fail"));

		//Shift methods
		int amount = 3; // test with 3
		// rightShift(3) works
		tempRes = new longword(); // initialize to 0
		tempRes = lw1.rightShift(amount); 
		int bool = 1; // initially true
		for(int i=0; i<longword_size-amount; i++) { // tempRes[i+amount] == lw1[i]
			if(tempRes.getBit(i+amount).getValue() != lw1.getBit(i).getValue()) {
				bool = 0; // if ever false, break
				break;
			}
		}
		System.out.println("lw1 rightShift 3 : " + (bool == 1 ? "pass" : "fail"));

		// leftShift(3) works
		tempRes = new longword(); // initialize to 0
		tempRes = lw1.leftShift(amount);
		bool = 1; // initially true
		for(int i=0; i<longword_size-amount; i++) { // tempRes[i] == lw1[i+amount]
			if(tempRes.getBit(i).getValue() != lw1.getBit(i+amount).getValue()) {
				bool = 0; // if ever false, break
				break;
			}
		}
		System.out.println("lw1 leftShift 3 : " + (bool == 1 ? "pass" : "fail"));

		// toString() works
		String strFormOfLongword = lw1.toString(); // convert collection of int values of lw1 to a String

		countMatch = 0;
		char[] ch = new char[strFormOfLongword.length()]; // convert String to array of char
		int j=0; // will store int value converted from char
		//to ignore comma, loop for str.length/2
		for(int i=0; i<=strFormOfLongword.length()/2; i++) {
			ch[i] = strFormOfLongword.charAt(i*2); // fill char[] with values only with the even(i*2) value of idx

			j = Integer.parseInt(String.valueOf(ch[i])); // convert char to int

			if( lw1.getBit(i).getValue() == j) // compare lw1's value with the int converted from string
				countMatch++;
		}
		System.out.println("toString() : " + (countMatch == longword_size ? "pass" : "fail")); //if all matches, working.

		// getUnsigned() works
		long resOfgetUnsigned = lw1.getUnsigned(); 
		// check if result is 141 for lw1
		System.out.println("getUnsigned() : " + (resOfgetUnsigned == 141 ? "pass" : "fail"));

		// getSigned() - positive case works
		int resOfgetSignedForPos = lw1.getSigned(); 
		// check if result is 141 for lw1
		System.out.println("getSigned() - positive case : " + (resOfgetSignedForPos == 141 ? "pass" : "fail"));

		// getSigned() - negative case works
		lw1.setBit(0, bit1); // set first digit to 1 to check if it becomes negative
		int resOfgetSignedForNeg = lw1.getSigned();
		// check if result is -141 for edited lw1
		System.out.println("getSigned() - negative case : " + (resOfgetSignedForNeg == -141 ? "pass" : "fail"));
		lw1.setBit(0, bit0); // reset lw1 to before the sign gets changed to avoid dependenct affect on next test

		// copy(other) works
		tempRes = new longword(); // longword to store the copied longword
		tempRes.copy(lw1); // call method copy() to copy lw1 to tempRes
		bool = 1;
		for(int i=0; i<longword_size; i++) { // compare it they are the same values at each position
			if (tempRes.getBit(i).getValue() != lw1.getBit(i).getValue()) {
				bool = 0; // if ever false, break
				break;
			}
		}
		System.out.println("copy(other) : " + (bool == 1 ? "pass" : "fail"));

		// set(value)
		// success case : set(value)
		// (decimal) 2147483647 = (binary) 01111111111111111111111111111111
		longword test = new longword();
		test.setBit(0, bit0);
		for(int i=1; i<32; i++) {
			test.setBit(i, bit1);
		}

		tempRes = new longword(); // initialize to 0
		tempRes.set(2147483647); // test with integer 2147483647 = (2^32 - 1) - 2^31
		bool = 1; // initially true
		System.out.print("[Must Pass] set(value) - Integer to longword : ");
		// check if the result is same as longword 'test'
		for(int i=0; i<longword_size; i++) {
			if (tempRes.getBit(i).getValue() != test.getBit(i).getValue()) {
				bool = 0; // if ever false, break
				break;
			}
		}
		System.out.println( bool == 1 ? "pass" : "fail" );

		// fail case : set(value)
		// (decimal) 2147483647 != (binary) 11111111111111111111111111111111
		test = new longword();
		for(int i=0; i<32; i++) {
			test.setBit(i, bit1);
		}

		tempRes = new longword(); // initialize to 0
		tempRes.set(2147483647); // test with integer 2147483647 = (2^32 - 1) - 2^31
		bool = 1; // initially true
		System.out.print("[Must Fail] set(value) - Integer to longword : ");
		// check if the result is same as longword 'test'
		for(int i=0; i<longword_size; i++) {
			if (tempRes.getBit(i).getValue() != test.getBit(i).getValue()) {
				bool = 0; // if ever false, break
				break;
			}
		}
		System.out.println( bool == 1 ? "pass" : "fail" );
	}
}