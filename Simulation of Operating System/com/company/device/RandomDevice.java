package com.company.device;

import com.company.GenericHAL;

import java.util.Random;

//FloppyDisk
public class RandomDevice implements Device {
    private static RandomDevice instance;
    private static final int MAX_NUM_OF_CONNECTIONS = 10;
    private final int MAX_DATA_MEMORY_SIZE = GenericHAL.RegisterSize/8; // Memory size limit per connection in byte

    private RandomDevice() {
        //instance = new RandomDevice();
    }

    public static RandomDevice getInstance() {
        if (instance == null) {
            synchronized (RandomDevice.class) {
                if (instance == null) {
                    instance = new RandomDevice();
                    for(int i = 0; i< MAX_NUM_OF_CONNECTIONS; i++) {
                        dataMap[i] = null;
                    }
                }
            }
        }

        return instance;
    }

    @Override
    public int Open(String s) {
        if(s.length() > 12) { // more than 12 characters cannot be converted to long
            System.out.println("Device name should not be longer than 12.");
            return -1;
        }

        System.out.println("Opening Random Device (name=" + s + ")...");
        if (!(s.isEmpty() || s == null)) {
            // Convert s to long
            long seedLong = Long.parseLong(s, 36); // handle input String 0-9a-Z
            Random randomGenerator = new Random(seedLong); // generate a random number
            int connectionID = randomGenerator.nextInt();; // set it to the connectionID
            for(int i=0; i<dataMap.length; i++) { // look for the next available index (holding empty data)
                if(dataMap[i] == null) {
                    dataMap[i] = new byte[MAX_DATA_MEMORY_SIZE];
                    for(int j=0; j<GenericHAL.RegisterSize/8; j++) { // initialize the memory space
                        dataMap[i][j] = 0;
                    }
                    dataMemoryIndex.put(connectionID, i);
                    break;
                }
            }
            return connectionID;
        }
        return 0;
    }

    @Override
    public void Close(int id) {
        System.out.println("Closing Random Device (id=" + id + ")...");
        // set the array entry to NULL
        int targetIndex = dataMemoryIndex.get(id);
        dataMap[targetIndex] = null; // set the array entry to NULL
        dataMemoryIndex.remove(id); // delete the connection
    }

    @Override
    public byte[] Read(int id, int size) {
        System.out.println("Reading Random Device (id=" + id + ")...");
        if (size > GenericHAL.RegisterSize/8) {
            System.out.println("[Warning] Maximum Memory Block Size is " + GenericHAL.RegisterSize/8 + " bytes. Returns " + GenericHAL.RegisterSize/8 + " bytes / Requested " + size + " bytes.");
            size = GenericHAL.RegisterSize/8;
        }

        int targetIndex;
        byte[] dataRead = new byte[size];
        try {
            targetIndex = dataMemoryIndex.get(id); // get the index associated to the given connection ID
            byte[] targetData = dataMap[targetIndex]; // data stored in the targetIndex
            dataRead = new byte[size]; // data to return
            for(int i=0; i<size; i++) { // copy data from the targetData for given size to return
                dataRead[i] = targetData[i];
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return dataRead;
    }

    @Override
    public void Seek(int id, int to) {
        System.out.println("Seeking for Random Device (id=" + id + ")...");
        int targetIndex = dataMemoryIndex.get(id); // get the index associated to the given connection ID
        byte[] targetData = dataMap[targetIndex]; // data stored in the targetIndex
        byte[] dataRead = new byte[to]; // data to return
        for(int i=0; i<to; i++) { // copy data from the targetData up to the given param "to"
            dataRead[i] = targetData[i];
        }
    }

    @Override
    public int Write(int id, byte[] data) {
        System.out.println("Writing to Random Device (id=" + id + ")...");
        return 0;
    }
}


