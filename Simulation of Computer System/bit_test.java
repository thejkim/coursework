public class bit_test {
	public static void main(String[] args) {
		bit bit1 = new bit();
   		bit bit2 = new bit();

   		runTests(bit1, bit2);
	}

	static public void runTests(bit bit1, bit bit2) {

       // test set(int)
       System.out.println("Test set(int)-------");
       bit1.set(0);
       System.out.println("set to 0 : " + (bit1.getValue() == 0 ? "pass": "fail"));
       bit1.set(1);
       System.out.println("set to 1 : " + (bit1.getValue() == 1 ? "pass": "fail"));

       // test toggle()
       System.out.println("Test toggle()-------");
       bit1.set(0);
       bit1.toggle();
       System.out.println("0 to 1: " + (bit1.getValue() == 1 ? "pass": "fail"));
       bit1.set(1);
       bit1.toggle();
       System.out.println("1 to 0: " + (bit1.getValue() == 0 ? "pass": "fail"));

       // test clear(), set()
       System.out.println("Test clear(), set()-------");
       bit1.clear();
       System.out.println("clear : " + (bit1.getValue() == 0 ? "pass": "fail"));
       bit1.set();
       System.out.println("set() : " + (bit1.getValue() == 1 ? "pass": "fail"));
      
       // test 'and'
       System.out.println("Test and(bit other)-------");
       bit1.clear();
       bit2.clear();
       System.out.println("0 AND 0 : " + (bit1.and(bit2).getValue() == 0 ? "pass": "fail"));
       bit2.set();
       System.out.println("0 AND 1 : " + (bit1.and(bit2).getValue() == 0 ? "pass": "fail"));
       bit1.set();
       System.out.println("1 AND 1 : " + (bit1.and(bit2).getValue() == 1 ? "pass": "fail"));
       bit2.clear();
       System.out.println("1 AND 0 : " + (bit1.and(bit2).getValue() == 0 ? "pass": "fail"));

       // test 'or'
       System.out.println("Test or(bit other)-------");
       bit1.clear();
       System.out.println("0 OR 0 : " + (bit1.or(bit2).getValue() == 0 ? "pass": "fail"));
       bit2.set();
       System.out.println("0 OR 1 : " + (bit1.or(bit2).getValue() == 1 ? "pass": "fail"));
       bit1.set();
       System.out.println("1 OR 1 : " + (bit1.or(bit2).getValue() == 1 ? "pass": "fail"));
       bit2.clear();
       System.out.println("1 OR 0 : " + (bit1.or(bit2).getValue() == 1 ? "pass": "fail"));
      

       // test 'xor'
       System.out.println("Test xor(bit other)-------");
       bit1.clear();
       System.out.println("0 XOR 0 : " + (bit1.xor(bit2).getValue() == 0 ? "pass": "fail"));
       bit2.set();
       System.out.println("0 XOR 1 : " + (bit1.xor(bit2).getValue() == 1 ? "pass": "fail"));
       bit1.set();
       System.out.println("1 XOR 1 : " + (bit1.xor(bit2).getValue() == 0 ? "pass": "fail"));
       bit2.clear();
       System.out.println("1 XOR 0 : " + (bit1.xor(bit2).getValue() == 1 ? "pass": "fail"));


       // test 'not'
       System.out.println("Test not()-------");
       bit1.clear();
       System.out.println("NOT 0 : " + (bit1.not().getValue() == 1 ? "pass": "fail"));

       bit1.set();
       System.out.println("NOT 1 : " + (bit1.not().getValue() == 0 ? "pass": "fail"));
      
       try
       {
           bit1.set(2);
           System.out.println(bit1);
       } catch(IllegalArgumentException e)
       {
           System.out.print(e.getMessage());
       }
   }
}