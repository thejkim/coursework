package com.company.device;
import com.company.GenericHAL;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Random;

public class FilesystemDevice implements Device {
    private static FilesystemDevice instance;
    private static final int MAX_NUM_OF_CONNECTIONS = 10;
    private final int MAX_DATA_MEMORY_SIZE = GenericHAL.RegisterSize/8; // Memory size limit per connection in byte
    private static HashMap<Integer, RandomAccessFile> randomAccessFileMap = new HashMap<>();
    private FilesystemDevice() {
    }

    public static FilesystemDevice getInstance() {
        if (instance == null) {
            synchronized (FilesystemDevice.class) {
                if (instance == null) {
                    instance = new FilesystemDevice();
                    for(int i = 0; i< MAX_NUM_OF_CONNECTIONS; i++) {
                        dataMap[i] = null;
                    }
                }
            }
        }
        return instance;
    }

    @Override
    public int Open(String s) throws IOException {
        if(s.length() > 12) {
            System.out.println("Device name should not be longer than 12.");
            return -1;
        }
        System.out.println("Opening Fake File System Device (name=" + s + ")...");
        if (!(s.isEmpty() || s == null)) {
            String fileName = s.substring(0, s.indexOf(".")); // character "." cannot be converted to long.
            String extension = s.substring(s.indexOf(".") + 1, s.length());
            // Convert s to long
            long seedLong = Long.parseLong(fileName + extension, 36); // use fileName+extension to generate a random number
            Random randomGenerator = new Random(seedLong); // generate a random number
            int connectionID = randomGenerator.nextInt();

            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile(s, "rw"); // create. mode: read and write
                randomAccessFileMap.put(connectionID, randomAccessFile); // record
                return connectionID;
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        } else {
            throw new IOException("File not found.");
        }
        return -1;
    }

    @Override
    public void Close(int id) {
        System.out.println("Closing Fake File System Device (id=" + id + ")...");
        RandomAccessFile targetFile = randomAccessFileMap.get(id);
        if(targetFile != null) {
            try {
                targetFile.close(); // close RandomAccessFile
                randomAccessFileMap.remove(id); // clear out array
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public byte[] Read(int id, int size) {
        System.out.println("Reading Fake File System Device (id=" + id + ")...");
        byte[] readBuffer = new byte[size];
        RandomAccessFile targetFile = randomAccessFileMap.get(id);
        if(targetFile != null) {
            Integer buf; // return value of read()
            for(int i=0; i<size; i++) {
                try {
                    buf = targetFile.read(); // reads a byte and return the next byte of data in the range of 0 to 255
                    if(buf != -1) { // has next byte
                        readBuffer[i] = buf.byteValue();
                    } else { // EOF
                        byte[] byteArray = new byte[i]; // given size(param) is larger than the number of byte left(size=i) to read
                        for(int j=0; j<i; j++) {
                            byteArray[j] = readBuffer[j]; // copy the bytes read
                        }
                        return byteArray;
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

            }
        }
        return readBuffer; // will be returned if given size is less than targetFile's size
    }

    @Override
    public void Seek(int id, int to) {
        System.out.println("Seeking for Fake File System Device (id=" + id + ")...");
        RandomAccessFile targetFile = randomAccessFileMap.get(id);
        if(targetFile != null) {
            try {
                targetFile.seek(to); // sets the file-pointer offset measured from the beginning
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public int Write(int id, byte[] data) {
        System.out.println("Writing to Fake File System Device (id=" + id + ")...");
        RandomAccessFile targetFile = randomAccessFileMap.get(id);
        if(targetFile != null) {
            try {
                targetFile.write(data); // write data.length bytes starting at the current file pointer
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return -1;
            }
        }
        return data.length; // return the length written
    }
}
