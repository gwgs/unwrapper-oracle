package com.salvis.unwrapper.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * @author Philipp Salvisberg (https://www.salvis.com/blog/plsql-unwrapper-for-sql-developer)
 */
public class HashCalculator {
    protected static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    public HashCalculator() {
    }

    public static byte[] getSHA1(final byte[] b) throws NoSuchAlgorithmException {
        final MessageDigest md = MessageDigest.getInstance("SHA1");
        md.update(b);
        return md.digest();
    }

    public static String bytesToHex(final byte[] bytes) {
        final char[] hexChars = new char[bytes.length * 2];

        for (int j = 0; j < bytes.length; ++j) {
            final int v = bytes[j] & 255;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 15];
        }

        return new String(hexChars);
    }
}
