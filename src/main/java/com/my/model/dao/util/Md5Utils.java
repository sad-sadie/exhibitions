package com.my.model.dao.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Md5Utils {

    public static String toMd5(String info) {

        byte[] secretByte;
        try {
            secretByte = MessageDigest.getInstance("md5")
                    .digest(info.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Cannot perform the Md5 algorithm");
        }
        StringBuilder md5Code = new StringBuilder(new BigInteger(1, secretByte).toString(16));
        for (int i = 0; i < 32 - md5Code.length(); i++) {
            md5Code.insert(0, "0");
        }
        return md5Code.toString();
    }

    public static void main(String[] args) {
        System.out.println(Md5Utils.toMd5("123"));
    }
}
