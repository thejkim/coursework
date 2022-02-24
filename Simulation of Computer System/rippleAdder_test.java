/*
runTests() : test rippleAdder
countBitOne() : helper function to count the number of bits with value 1 from result in binary
countMatch() : helper function to count the number of matches between result and array that stores index where 1 should be in result
*/

public class rippleAdder_test {
	public static void main(String[] args) {
		longword lw1 = new longword();
		longword lw2 = new longword();
		bit bit1 = new bit();
		bit bit0 = new bit();

		runTests(bit0, bit1, lw1, lw2); // call the test class
	}

	public static void runTests(bit bit0, bit bit1, longword lw1, longword lw2) {
		int longword_size = 32;
		int countBit1 = 0;
		int countMatch = 0;
		// temporary longword to store result dataset and to print out for test
		longword tempRes;
		// index(es) to check if their values are 1 in order to match with tempRes
		int[] indexArray;

		bit1.set(); // bit with value 1
		bit0.clear(); // bit with value 0

		/* Addition */
		System.out.println("--------Addition--------");
		/*	0+0=0
			0+1=1
			1+0=1
			1+1=10	*/

		// lw1 = 00000000
		// lw2 = 00000001 : idx of 1 = 31
		// res = 00000001 : 31
		// set up for the test case above
		// 1. reset by initializing to 0
		tempRes = new longword();
		lw1 = new longword();
		lw2 = new longword();
		countBit1 = 0;
		countMatch = 0;
		// 2. set value to 1 in appropriate position(s)
		lw2.setBit(31, bit1);
		indexArray = new int[] {31}; // expected idx(es) with value of 1 in result
		tempRes = rippleAdder.add(lw1, lw2); // call method add() of rippleAdder class

		// count number of bits with value 1 from tempRes
		countBit1 = countBitOne(tempRes, indexArray, longword_size);
		// count number of matches with indexArray
		countMatch = countMatch(tempRes, indexArray);

		//if countBit1 == countMatch, it works correctly
		System.out.println("00000000 + 00000001 = 00000001 : " + (countBit1 == countMatch ? "pass" : "fail") );

		// 	00000001 : 31
		// +00000001 : 31
		// =00000010 : 30
		// set up for the test case above
		tempRes = new longword();
		lw1 = new longword();
		lw2 = new longword();
		countBit1 = 0;
		countMatch = 0;

		lw1.setBit(31, bit1);
		lw2.setBit(31, bit1);

		indexArray = new int[] {30};
		tempRes = rippleAdder.add(lw1, lw2);

		// count number of bits with value 1 from tempRes
		countBit1 = countBitOne(tempRes, indexArray, longword_size);
		// count number of matches with indexArray
		countMatch = countMatch(tempRes, indexArray);
		//if countBit1 == countMatch, it works correctly
		System.out.println("00000001 + 00000001 = 00000010 : " + (countBit1 == countMatch ? "pass" : "fail") );


		//  00011101 : 31, 29, 28, 27
		// +00011011 : 31, 30, 28, 27
		// =00111000 : 28, 27, 26
		tempRes = new longword();
		lw1 = new longword();
		lw2 = new longword();
		countBit1 = 0;
		countMatch = 0;

		lw1.setBit(31, bit1);
		lw1.setBit(29, bit1);
		lw1.setBit(28, bit1);
		lw1.setBit(27, bit1);

		lw2.setBit(31, bit1);
		lw2.setBit(30, bit1);
		lw2.setBit(28, bit1);
		lw2.setBit(27, bit1);

		indexArray = new int[] {28, 27, 26};
		tempRes = rippleAdder.add(lw1, lw2);

		// count number of bits with value 1 from tempRes
		countBit1 = countBitOne(tempRes, indexArray, longword_size);
		// count number of matches with indexArray
		countMatch = countMatch(tempRes, indexArray);
		//if countBit1 == countMatch, it works correctly
		System.out.println("00011101 + 00011011 = 00111000 : " + (countBit1 == countMatch ? "pass" : "fail") );


		// 	1000...0000 : 0
		// +1000...0000 : 0
		// =0000...0000
		tempRes = new longword();
		lw1 = new longword();
		lw2 = new longword();
		countBit1 = 0;
		countMatch = 0;

		lw1.setBit(0, bit1);
		lw2.setBit(0, bit1);

		tempRes = rippleAdder.add(lw1, lw2);

		for(int i=0; i<longword_size; i++) {
			if(tempRes.getBit(i).getValue() == 1)
				countBit1++;
		}
		// if there is no 1, it works correctly
		System.out.println("10...000 + 10...000 = 00...000 : " + (countBit1 == 0 ? "pass" : "fail") );


		/* Subtraction */
		System.out.println("--------Subtraction--------");
		/*	0-0=0
			1-0=1
			1-1=0
			10-1=1	*/
		
		//	00001100 : 29, 28
		// -00001010 : 30, 28
		// =00000010 : 30
		tempRes = new longword();
		lw1 = new longword();
		lw2 = new longword();
		countBit1 = 0;
		countMatch = 0;

		lw1.setBit(29, bit1);
		lw1.setBit(28, bit1);
		lw2.setBit(30, bit1);
		lw2.setBit(28, bit1);

		indexArray = new int[] {30};
		tempRes = rippleAdder.subtract(lw1, lw2);

		// count number of bits with value 1 from tempRes
		countBit1 = countBitOne(tempRes, indexArray, longword_size);
		// count number of matches with indexArray
		countMatch = countMatch(tempRes, indexArray);
		//if countBit1 == countMatch, it works correctly
		System.out.println("00001100 - 00001010 = 00000010 : " + (countBit1 == countMatch ? "pass" : "fail") );


		//	1000101 : 31, 29, 25 
		// -0101100 : 29, 28, 26
		// =0011001 : 31, 28, 27
		tempRes = new longword();
		lw1 = new longword();
		lw2 = new longword();
		countBit1 = 0;
		countMatch = 0;

		lw1.setBit(31, bit1);
		lw1.setBit(29, bit1);
		lw1.setBit(25, bit1);

		lw2.setBit(29, bit1);
		lw2.setBit(28, bit1);
		lw2.setBit(26, bit1);

		indexArray = new int[] {31, 28, 27};
		tempRes = rippleAdder.subtract(lw1, lw2);

		// count number of bits with value 1 from tempRes
		countBit1 = countBitOne(tempRes, indexArray, longword_size);
		// count number of matches with indexArray
		countMatch = countMatch(tempRes, indexArray);
		//if countBit1 == countMatch, it works correctly
		System.out.println("1000101 - 0101100 = 0011001 : " + (countBit1 == countMatch ? "pass" : "fail") );


		//	1110110 : 30, 29, 27, 26, 25
		// -1010111 : 31, 30, 29, 27, 25
		// =0011111 : 31, 30, 29, 28, 27
		tempRes = new longword();
		lw1 = new longword();
		lw2 = new longword();
		countBit1 = 0;
		countMatch = 0;

		lw1.setBit(30, bit1);
		lw1.setBit(29, bit1);
		lw1.setBit(27, bit1);
		lw1.setBit(26, bit1);
		lw1.setBit(25, bit1);

		lw2.setBit(31, bit1);
		lw2.setBit(30, bit1);
		lw2.setBit(29, bit1);
		lw2.setBit(27, bit1);
		lw2.setBit(25, bit1);

		indexArray = new int[] {31, 30, 29, 28, 27};
		tempRes = rippleAdder.subtract(lw1, lw2);

		// count number of bits with value 1 from tempRes
		countBit1 = countBitOne(tempRes, indexArray, longword_size);
		// count number of matches with indexArray
		countMatch = countMatch(tempRes, indexArray);
		//if countBit1 == countMatch, it works correctly
		System.out.println("1110110 - 1010111 = 0011111 : " + (countBit1 == countMatch ? "pass" : "fail") );
	}

	// helper function to count the number of bits with value 1 from tempRes
	public static int countBitOne(longword res, int []idx, int lwSize) {
		int countBit1 = 0;
		for(int i=0; i<lwSize; i++) {
			if(res.getBit(i).getValue() == 1)
				countBit1++;
		}
		return countBit1;
	}

	// helper function to count the number of matches between tempRes and indexArray
	public static int countMatch(longword res, int []idx) {
		int countMatch = 0;
		for(int i=0; i<idx.length; i++) {
			if(res.getBit(idx[i]).getValue() == 1) // if where 1 is the same as the idx
				countMatch++;
		}
		return countMatch;
	}
}