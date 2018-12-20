package com.ks4pl.oasvr;

import java.util.regex.Pattern;

public class MyUtils {
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("^[0-9][0-9]*");
        return pattern.matcher(str).matches();
    }
}
