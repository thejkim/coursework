package com.company.device;

import com.company.GenericHAL;

import java.io.IOException;
import java.util.HashMap;

public interface Device {
    int Open(String s) throws IOException;
    void Close(int id);
    byte[] Read(int id, int size);
    void Seek(int id, int to);
    int Write(int id, byte[] data);

    byte[][] dataMap = new byte[10][GenericHAL.RegisterSize/8]; // holds up to 10 of 64-bit (8 bytes) connections
    // keep track of the data memory array index for each connected device
    HashMap<Object, Integer> dataMemoryIndex = new HashMap<Object, Integer>(); // <Key : Value> = <Device ID : data Index>
}
