package com.company;
import com.company.device.FilesystemDevice;
import com.company.device.PipeDevice;
import com.company.device.RandomDevice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Kernel {
    public class PCB { // Process control block. Needs to be filled in.
        PCB() {
            pid = maxPid++;
        }
        byte[] registerData = new byte[hal.RegisterSize];
        int pid;
        HashMap<Integer, Integer> vfsMap = new HashMap<>(); // connectionID : deviceAccessNumber(1 : random, 2 : pipe, 3 : filesystem)
    }

    public GenericHAL hal = new GenericHAL(this); // the HAL our kernel is running on
    public PCB current; // the currently running process
    int maxPid = 1;

    public ArrayList<PCB> activePCBS = new ArrayList<>();

    public void Init() { // called once when the system is started
        final PCB pcb = new PCB(); // make a PCB for the Init process
        current = pcb; // make Init the running process
        activePCBS.add(pcb);
    }

    public int Open(String input) throws IOException {
        int connectionID = 0;
        String device = input.substring(0, input.indexOf(" "));
        String reminder = input.substring(input.indexOf(" ") + 1, input.length());
        switch (device) {
            case "random":
                connectionID = RandomDevice.getInstance().Open(reminder);
                current.vfsMap.put(connectionID, 1); // to look up the file descriptor number to get the device access number
                return connectionID;
            case "pipe":
                connectionID = PipeDevice.getInstance().Open(reminder);
                current.vfsMap.put(connectionID, 2);
                return connectionID;
            case "file":
                connectionID = FilesystemDevice.getInstance().Open(reminder);
                current.vfsMap.put(connectionID, 3);
                return connectionID;
            default:
                throw new IllegalStateException("Cannot open: " + input);
        }
    }

    public void Close(String input) {
        String device = input.substring(0, input.indexOf(" "));
        String reminder = input.substring(input.indexOf(" ") + 1, input.length());
        int connectionID = Integer.parseInt(reminder); // convert connectionID passed as String to int
        switch (device) {
            case "random":
                RandomDevice.getInstance().Close(connectionID);
                current.vfsMap.remove(connectionID); // remove the entry from the PCB
                break;
            case "pipe":
                PipeDevice.getInstance().Close(connectionID);
                current.vfsMap.remove(connectionID);
                break;
            case "file":
                FilesystemDevice.getInstance().Close(connectionID);
                current.vfsMap.remove(connectionID);
                break;
            default:
                throw new IllegalStateException("Cannot close: " + input);
        }
    }

    public byte[] Read(int id, int size) {
        byte[] dataRead;
        // look up the file descriptor number to get the device access number
        int deviceAccessNumber = 0;
        if(current.vfsMap.containsKey(id)) {
            deviceAccessNumber = current.vfsMap.get(id);
            switch (deviceAccessNumber) {
                case 1:
                    dataRead = RandomDevice.getInstance().Read(id, size);
                    return dataRead;
                case 2:
                    dataRead = PipeDevice.getInstance().Read(id, size);
                    return dataRead;
                case 3:
                    dataRead = FilesystemDevice.getInstance().Read(id, size);
                    return dataRead;
                default:
                    throw new IllegalStateException("Cannot read: " + id);
            }
        } else {
            System.out.println("Cannot find connectionID = "+id);
            return new byte[0];
        }
    }

    public void Seek(int id, int to) {
        int deviceAccessNumber = 0;
        if(current.vfsMap.containsKey(id)) {
            deviceAccessNumber = current.vfsMap.get(id);
            switch (deviceAccessNumber) {
                case 1:
                    RandomDevice.getInstance().Seek(id, to);
                    break;
                case 2:
                    PipeDevice.getInstance().Seek(id, to);
                    break;
                case 3:
                    FilesystemDevice.getInstance().Seek(id, to);
                    break;
                default:
                    throw new IllegalStateException("Cannot seek for: " + id);
            }
        } else {
            System.out.println("Cannot find connectionID = "+id);
        }

    }

    public int Write(int id, byte[] data) {
        int deviceAccessNumber = 0;
        if(current.vfsMap.containsKey(id)) {
            deviceAccessNumber = current.vfsMap.get(id);
        } else {
            System.out.println("Cannot find connectionID="+id);
        }
        switch (deviceAccessNumber) {
            case 1:
                return RandomDevice.getInstance().Write(id, data);
            case 2:
                return PipeDevice.getInstance().Write(id, data);
            case 3:
                return FilesystemDevice.getInstance().Write(id, data);
            default:
                throw new IllegalStateException("Cannot seek for: " + id);
        }
    }

    public int CreateProcess() {
        PCB newOne = new PCB();
        activePCBS.add(newOne);
        current = newOne;
        System.out.println("Process created. pid=" + newOne.pid);
        return newOne.pid;
    }

    public Object DeleteProcess(int pid) {
        for (int i=0;i<activePCBS.size(); i++) {
            if (activePCBS.get(i).pid == pid) {
                activePCBS.remove(i);
                break;
            }
        }

        if (activePCBS.size() == 0)
            System.exit(0); // We deleted the last of the process. We are finished.
        return null;
    }

    public Object Reschedule(int pid) {
        hal.StoreProgramData(current.registerData);
        current = null;
        for (PCB pcb : activePCBS) {
            if (pcb.pid == pid)
                current = pcb;
        }
        if (current == null)
        {
            current = activePCBS.get(0);
        }
        // Update the segment registers in the HAL here.
        hal.RestoreProgramData(current.registerData);
        return null;
    }

    public Object Exit() { // Exit syscall. Needs more work when we finish processes
        DeleteProcess(current.pid);
        return 0;
    }

    public Object Print(String s) { // Print a line
        System.out.println(s);
        return null;
    }
}
