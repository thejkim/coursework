package com.company;

import java.io.IOException;
import java.util.concurrent.Callable;

public class KernelBindings {
    public static Kernel k;
    public KernelBindings() {
        if (k == null)
            k = new Kernel();
    }

    // Assignment 5: Device
    public static int Open(String s) throws IOException {
        return k.Open(s);
    }
    public static void Close(String s) {
        k.Close(s);
    }
    public static byte[] Read(int id, int size) {
        return k.Read(id, size);
    }
    public static void Seek(int id, int to) {
        k.Seek(id, to);
    }
    public static int Write(int id, byte[] data) {
        return k.Write(id, data);
    }


    // From Skeleton of Assignment 3.
    public static Object Exit() {
        return k.Exit();
    }
    public static Object Print(String s ) {
        return k.Print(s);
    }
    public static Object CreateProcess()  {
        return k.CreateProcess();
    }
    public static Object DeleteProcess(int pid)  {
        return k.DeleteProcess(pid);
    }
    public static Object Reschedule(int pid) {
        return k.Reschedule(pid);
    }
}
