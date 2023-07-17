package com.fubuki.fubukioa.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {
    public static String md5Digest(String source) {
        return DigestUtils.md5Hex(source);
    }

    public static String md5Digest(String source, Integer salt) {
        char[] chars = source.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            //加盐算法
//            System.out.println(chars[i]+":"+(char)(chars[i]+salt));
            chars[i] = (char) (chars[i] + salt);
        }
        String target = new String(chars);
//        System.out.println(target);
        return DigestUtils.md5Hex(target);
    }
}
