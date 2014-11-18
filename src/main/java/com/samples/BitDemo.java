package com.samples;

public class BitDemo {
    public static void main(String[] args) {
        int bitmask = 0x000F;
        int val = 0x2222;
        // prints "2"
        System.out.println(bitmask);
        System.out.println(val);
        
        byte b = (byte)(-2 >>>2);
        System.out.println(b);
          
    }
}