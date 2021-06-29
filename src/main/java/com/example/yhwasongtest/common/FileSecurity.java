package com.example.yhwasongtest.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

public class FileSecurity {
    private static final Logger logger = LoggerFactory.getLogger(FileSecurity.class);

    public static String md5(String str){
        String md5= "";
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            byte bytes[] = messageDigest.digest();
            StringBuffer sb = new StringBuffer();
            for(int i=0; i< bytes.length; i++){
                sb.append((Integer.toString((bytes[i]&0xff)+ 0x100, 16).substring(1)));
            }
            md5=sb.toString();
            return md5;
        }catch (Exception e){
            logger.info("FileSecurity error => ", e.toString());
        }
        return null;
    }
}
