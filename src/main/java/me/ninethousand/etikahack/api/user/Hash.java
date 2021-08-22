package me.ninethousand.etikahack.api.user;

import java.security.MessageDigest;

public class Hash {
    public static String getHash(String in) {
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(in.getBytes());
            StringBuffer hexString = new StringBuffer();

            byte byteData[] = md.digest();

            for (byte aByteData : byteData) {
                String hex = Integer.toHexString(0xff & aByteData);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
}
