package com.company.device;
import com.company.GenericHAL;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class PipeDevice implements Device {
    private static PipeDevice instance;
    private static final int MAX_NUM_OF_CONNECTIONS = 10;
    private final int MAX_DATA_MEMORY_SIZE = GenericHAL.RegisterSize/8; // Memory size limit per connection in byte

    private static HashMap<String, Integer> connectionIDMap = new HashMap<>(); // name : connectionID
    private static HashMap<String, Integer> connectionCountMap = new HashMap<>(); // name : count
    private static HashMap<String, ArrayList<Byte> > connectionBufferMap = new HashMap<>(); // name : buffer

    private PipeDevice() {
    }

    public static PipeDevice getInstance() {
        if (instance == null) {
            synchronized (PipeDevice.class) {
                if (instance == null) {
                    instance = new PipeDevice();
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
        if(s.length() > 12) {
            System.out.println("Device name should not be longer than 12.");
            return -1;
        }
        System.out.println("Opening Pipe Device (name=" + s + ")...");
        if (!(s.isEmpty() || s == null)) {
            // Convert s to long
            long seedLong = Long.parseLong(s, 36); // handle input String 0-9a-Z
            Random randomGenerator = new Random(seedLong); // generate a random number
            int connectionID = randomGenerator.nextInt();; // set it to the connectionID

            if (!connectionIDMap.containsKey(s)) { // map is null or name doesn't exist
                System.out.println("\tName doesn't exist. Creating new connection...");
                connectionIDMap.put(s, connectionID);
                for(int i=0; i<dataMap.length; i++) { // look for the next available index (holding empty data)
                    if(dataMap[i] == null) {
                        dataMap[i] = new byte[MAX_DATA_MEMORY_SIZE];
                        ArrayList<Byte> bufferData = new ArrayList<>();
                        for(int j = 0; j< GenericHAL.RegisterSize/8; j++) { // initialize the memory space
                            dataMap[i][j] = 0;
                            bufferData.add(dataMap[i][j]);
                        }
                        connectionBufferMap.put(s, bufferData);
                        connectionCountMap.put(s, 1); // count = 1;
                        System.out.println("\tCount = " + connectionCountMap.get(s));
                        dataMemoryIndex.put(connectionID, i);
                        break;
                    }
                }
                // return the id for the name s
                return connectionID;
            } else {
                System.out.println("\tName exists. Attaching to it...");
                connectionCountMap.replace(s, connectionCountMap.get(s)+1); // count++ for the given key;
                System.out.println("\tCount = " + connectionCountMap.get(s));
                return connectionIDMap.get(s); // attach to it, return the id for the name s
            }
        }
        return 0;
    }

    @Override
    public void Close(int id) {
        System.out.println("Closing Pipe Device (id=" + id + ")...");
        // set the array entry to NULL
        int targetIndex = dataMemoryIndex.get(id);

        // Get the name associated to the given id
        String targetName;
        for (Map.Entry<String, Integer> entry : connectionIDMap.entrySet()) {
            if (entry.getValue().equals(id)) {
                targetName = entry.getKey();
                connectionCountMap.replace(targetName, connectionCountMap.get(targetName)-1); // count--
                System.out.println("\tCount = " + connectionCountMap.get(targetName));
                if(connectionCountMap.get(targetName) == 0) { // remove the entry (destroy the pipe connection)
                    System.out.println("Destroying the pipe connection...");
                    connectionIDMap.remove(targetName);
                    connectionCountMap.remove(targetName);
                    connectionBufferMap.remove(targetName);
                    dataMap[targetIndex] = null; // set the array entry to NULL
                    dataMemoryIndex.remove(id); // delete the connection
                }
            } else {
                System.out.println("\tPipe not found for connectionID="+id);
            }
        }
    }

    @Override
    public byte[] Read(int id, int size) {
        System.out.println("Reading Pipe Device (id=" + id + ")...");
        if (size > GenericHAL.RegisterSize/8) {
            System.out.println("\t[Warning] Maximum Memory Block Size is " + GenericHAL.RegisterSize/8 + " bytes. Returns " + GenericHAL.RegisterSize/8 + " bytes / Requested " + size + " bytes.");
            size = GenericHAL.RegisterSize/8;
        }

        byte[] dataRead = new byte[size];

        // Get the name associated to the given id
        String targetName;
        for (Map.Entry<String, Integer> entry : connectionIDMap.entrySet()) {
            if (entry.getValue().equals(id)) {
                targetName = entry.getKey();
                if(connectionBufferMap.get(targetName) != null) {
                    for (int i = 0; i < size; i++) { // read data and copy to dataRead
                        dataRead[i] = connectionBufferMap.get(targetName).get(i);
                    }
                    connectionBufferMap.remove(targetName); // clear the buffer once it is read. TODO:- should I close this pipe?
                } else {
                    System.out.println("\tCannot read data from the pipe. ");
                    return new byte[0];
                }
            } else {
                System.out.println("\tPipe not found for connectionID="+id);
            }
        }

        return dataRead;
    }

    @Override
    public void Seek(int id, int to) {
        System.out.println("Seeking for Random Device (id=" + id + ")...");
        if (to > GenericHAL.RegisterSize/8) {
            System.out.println("\t[Warning] Maximum Memory Block Size is " + GenericHAL.RegisterSize/8 + " bytes. Returns " + GenericHAL.RegisterSize/8 + " bytes / Requested " + to + " bytes.");
            to = GenericHAL.RegisterSize/8;
        }

        byte[] dataRead = new byte[to];

        // Get the name associated to the given id
        String targetName;
        for (Map.Entry<String, Integer> entry : connectionIDMap.entrySet()) {
            if (entry.getValue().equals(id)) {
                targetName = entry.getKey();
                if(connectionBufferMap.get(targetName) != null) {
                    for (int i = 0; i < to; i++) {
                        dataRead[i] = connectionBufferMap.get(targetName).get(i);
                    }
                } else {
                    System.out.println("\tCannot seek data for the pipe. ");
                }
            } else {
                System.out.println("\tPipe not found for connectionID="+id);
            }
        }
    }

    @Override
    public int Write(int id, byte[] data) {
        System.out.println("Writing to Random Device (id=" + id + ")...");

        // Get the name associated to the given id
        String targetName;
        for (Map.Entry<String, Integer> entry : connectionIDMap.entrySet()) {
            if (entry.getValue().equals(id)) {
                targetName = entry.getKey();
                ArrayList<Byte> bufferData = new ArrayList<>();
                for(int i=0; i<data.length; i++) {
                    bufferData.add(data[i]);
                }
                connectionBufferMap.replace(targetName, bufferData); // used replace instead of put as buffer was initialized with 0 once it's opened.
            } else {
                System.out.println("\tPipe not found for connectionID="+id);
                return -1;
            }
        }
        return data.length; // return the length written
    }
}
