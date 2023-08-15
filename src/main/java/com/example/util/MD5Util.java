package com.example.util;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
@Component
public class MD5Util {
    public String encode(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(input.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getCause());
        }
    }
}
