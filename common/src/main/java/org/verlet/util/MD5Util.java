package org.verlet.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

public class MD5Util {

    private static Logger logger = LoggerFactory.getLogger(MD5Util.class);

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private static String byteArrayToHexString(byte b[]) {
        StringBuilder resultSb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    private static String MD5Encode(String origin,String charsetname){
        String resultString = null;
        try{
            resultString = new String(origin);
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            if(charsetname == null || "".equals(charsetname)){
                resultString = byteArrayToHexString(messageDigest.digest(resultString.getBytes()));
            }else{
                resultString = byteArrayToHexString(messageDigest.digest(resultString.getBytes(charsetname)));
            }
        }catch (Exception e){
            logger.error("MD5加密失败",e);
            e.printStackTrace();
        }
        return resultString.toUpperCase();
    }

    public static String MD5EncodeUtf8(String orgin){
        orgin = orgin + PropertiesUtil.getProperty("password.salt","");
        return MD5Encode(orgin,"utf-8");
    }
}
