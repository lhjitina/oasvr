package com.ks4pl.oasvr.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
    public static String md5(String msg) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("md5");
        byte[] digest = messageDigest.digest(msg.getBytes());
        String result = new BigInteger(1, digest).toString(16);
        for (int i = 0; i < 32-result.length(); i++){
            result = "0" + result;
        }
        return result;
    }
}
