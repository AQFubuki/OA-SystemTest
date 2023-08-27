package com.fubuki.fubukioa;

import java.util.Random;

public class FloatTrst {
    public static void main(String[] args) {
        int i = new Random().nextInt(70) + 30;
        System.out.println(i);
        for (int j = 1; j < 1001; j++) {
            if (isZhi(j)) System.out.println(j);
        }
    }

    public static boolean isZhi(int i) {
        if (i == 1) return false;
        for (int j = 2; j < i; j++) {
            if (i % j == 0) return false;
        }
        return true;
    }
}
