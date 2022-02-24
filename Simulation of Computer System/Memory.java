/*
Our assumptions and limitations
    1. every data is in 4 bytes
    2. data can be stored in any position of our memory from 0 to 1023
        - the first byte of data must be stored between memory address 0 and 1020
    3. read and write methods behavior
        - write overwrites data even if there is data stored at the given memory address
        - read initializes the memory at the given address with 0, if no data is stored at the given address
 */

import javax.swing.plaf.synth.SynthTextAreaUI;

public class Memory {
    // Size in Byte
    static final int memorySize = 1024; // memory address values can hold from 0 to 1023 (in bytes)
    static final int defaultDataSize = 4; // 4 bytes = 32 bits

    private bit[] memorySpace; // array of bits

    // allocate memory space with amount of 8192 bits.
    public Memory() {
        memorySpace = new bit[memorySize * 8]; //our memory == 1024 bytes == 8192 bits.
    }

    public bit[] dump(int start, int end) {
        int length = end-start+1;
        bit[] bits = new bit[length];
        for(int i=0; i<length; i++) {
            bits[i] = memorySpace[start+i];
        }
        return bits;
    }

    // method to read the passed address from memory
    public longword read(longword address) {
        longword result = new longword();
        // convert binary address to integer in order to get the value of address (0-1023)
        int valueOfAddress = (int)address.getUnsigned();
        // The last value for address should not exceed 1020 because we need 4 address spaces(8 bits for each) to store 1 data(32 bits)
        if(valueOfAddress <= memorySize-defaultDataSize) {
            int memoryIndex = valueOfAddress * 8; // 1 byte == 8 bits. Address value "1" points to index "8" in memorySpace.
            // read each bit at a time for 32 times. Result in longword starts index from 0 but memorySpace starts from memoryIndex
            for (int i = 0; i < defaultDataSize * 8; i++) {
                // if there is no data written to yet, initialize the bit with value 0
                // otherwise, set the result with the values at the corresponding positions in memorySpace
                result.setBit(i, (memorySpace[memoryIndex + i] == null) ? new bit() : memorySpace[memoryIndex + i]);
            }
        } else { // valuesOfAddress > 1020 => out of bound
            System.out.println("[read] valueOfAddress = " + valueOfAddress + " : Out of bound.");
        }
        return result;
    }

    // method to write the passed value to the passed address in the memory
    public void write(longword address, longword value) {

        int valueOfAddress = (int)address.getUnsigned();
        if(valueOfAddress <= memorySize-defaultDataSize) {
            int memoryIndex = valueOfAddress * 8; // index in memorySpace == valueOfAddress * 8 since 1 byte == 8 bits
            for(int i=0; i<defaultDataSize * 8; i++) {
                // assign each bit of passed longword 'value' to the corresponding position in memorySpace
                bit bit = new bit();
                bit.set(value.getBit(i).getValue());
                memorySpace[memoryIndex + i] = bit;
                        //value.getBit(i);
            }
            System.out.println();
        } else { // out of bound
            System.out.println("[write] valueOfAddress = " + valueOfAddress + " : Out of bound.");
        }
    }

    public void printLongword(longword data) {
        for(int i=0; i<data.size(); i++) {
            System.out.print(data.getBit(i).getValue());
        }
        System.out.println();
    }

    public void logLn(String str) {
        System.out.println(str);
    }

    public void log(String str) {
        System.out.print(str);
    }
}
