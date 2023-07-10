package com.fubuki.fubukioa.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MD5UtilsTest {

    @Test
    void md5Digest() {
        String md5=MD5Utils.md5Digest("123456");
        System.out.println(md5);
        System.out.println(MD5Utils.md5Digest("123456", 103996));
    }
}