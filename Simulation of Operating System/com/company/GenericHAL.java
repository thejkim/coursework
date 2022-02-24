package com.company;

public class GenericHAL {

    public static final int RegisterSize = 64; // How big our register storage is
    public static Kernel kernel; // the kernel we are supporting

    public GenericHAL(Kernel k) { // HAL knows kernel; kernel knows HAL
        kernel = k;
    }

    public static void StoreProgramData(byte [] data) {} // Store registers to PCB. Does nothing in Java
    public static void RestoreProgramData(byte [] data) {} // Loads registers from PCB. IBID
}
