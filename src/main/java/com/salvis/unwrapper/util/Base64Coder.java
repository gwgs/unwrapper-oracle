package com.salvis.unwrapper.util;

/**
 * @author Philipp Salvisberg (https://www.salvis.com/blog/plsql-unwrapper-for-sql-developer/)
 */
public class Base64Coder {
    private static final String systemLineSeparator = System.getProperty("line.separator");

    private static final char[] map1 = new char[64];

    private static final byte[] map2;

    static {
        int i = 0;

        char c;
        for (c = 'A'; c <= 'Z'; c++) {
            map1[i++] = c;
        }

        for (c = 'a'; c <= 'z'; c++) {
            map1[i++] = c;
        }

        for (c = '0'; c <= '9'; c++) {
            map1[i++] = c;
        }

        map1[i++] = '+';
        map1[i++] = '/';

        map2 = new byte[128];

        for (i = 0; i < map2.length; ++i) {
            map2[i] = -1;
        }

        for (i = 0; i < 64; ++i) {
            map2[map1[i]] = (byte) i;
        }

    }

    private Base64Coder() {
    }

    public static String encodeString(final String s) {
        return new String(encode(s.getBytes()));
    }

    public static String encodeLines(final byte[] in) {
        return encodeLines(in, 0, in.length, 76, systemLineSeparator);
    }

    public static String encodeLines(final byte[] in, final int iOff, final int iLen, final int lineLen, final String lineSeparator) {
        final int blockLen = lineLen * 3 / 4;
        if (blockLen <= 0) {
            throw new IllegalArgumentException();
        } else {
            final int lines = (iLen + blockLen - 1) / blockLen;
            final int bufLen = (iLen + 2) / 3 * 4 + lines * lineSeparator.length();
            final StringBuilder buf = new StringBuilder(bufLen);

            int l;
            for (int ip = 0; ip < iLen; ip += l) {
                l = Math.min(iLen - ip, blockLen);
                buf.append(encode(in, iOff + ip, l));
                buf.append(lineSeparator);
            }

            return buf.toString();
        }
    }

    public static char[] encode(final byte[] in) {
        return encode(in, 0, in.length);
    }

    public static char[] encode(final byte[] in, final int iLen) {
        return encode(in, 0, iLen);
    }

    public static char[] encode(final byte[] in, final int iOff, final int iLen) {
        final int oDataLen = (iLen * 4 + 2) / 3;
        final int oLen = (iLen + 2) / 3 * 4;
        final char[] out = new char[oLen];
        int ip = iOff;
        final int iEnd = iOff + iLen;

        for (int op = 0; ip < iEnd; ++op) {
            final int i0 = in[ip++] & 255;
            final int i1 = ip < iEnd ? in[ip++] & 255 : 0;
            final int i2 = ip < iEnd ? in[ip++] & 255 : 0;
            final int o0 = i0 >>> 2;
            final int o1 = (i0 & 3) << 4 | i1 >>> 4;
            final int o2 = (i1 & 15) << 2 | i2 >>> 6;
            final int o3 = i2 & 63;
            out[op++] = map1[o0];
            out[op++] = map1[o1];
            out[op] = op < oDataLen ? map1[o2] : 61;
            ++op;
            out[op] = op < oDataLen ? map1[o3] : 61;
        }

        return out;
    }

    public static String decodeString(final String s) {
        return new String(decode(s));
    }

    public static byte[] decodeLines(final String s) {
        final char[] buf = new char[s.length()];
        int p = 0;

        for (int ip = 0; ip < s.length(); ++ip) {
            final char c = s.charAt(ip);
            if (c != ' ' && c != '\r' && c != '\n' && c != '\t') {
                buf[p++] = c;
            }
        }

        return decode(buf, 0, p);
    }

    public static byte[] decode(final String s) {
        return decode(s.toCharArray());
    }

    public static byte[] decode(final char[] in) {
        return decode(in, 0, in.length);
    }

    public static byte[] decode(final char[] in, final int iOff, int iLen) {
        if (iLen % 4 != 0) {
            throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
        } else {
            while (iLen > 0 && in[iOff + iLen - 1] == '=') {
                --iLen;
            }

            final int oLen = iLen * 3 / 4;
            final byte[] out = new byte[oLen];
            int ip = iOff;
            final int iEnd = iOff + iLen;
            int op = 0;

            while (ip < iEnd) {
                final int i0 = in[ip++];
                final int i1 = in[ip++];
                final int i2 = ip < iEnd ? in[ip++] : 65;
                final int i3 = ip < iEnd ? in[ip++] : 65;
                if (i0 <= 127 && i1 <= 127 && i2 <= 127 && i3 <= 127) {
                    final int b0 = map2[i0];
                    final int b1 = map2[i1];
                    final int b2 = map2[i2];
                    final int b3 = map2[i3];
                    if (b0 >= 0 && b1 >= 0 && b2 >= 0 && b3 >= 0) {
                        final int o0 = b0 << 2 | b1 >>> 4;
                        final int o1 = (b1 & 15) << 4 | b2 >>> 2;
                        final int o2 = (b2 & 3) << 6 | b3;
                        out[op++] = (byte) o0;
                        if (op < oLen) {
                            out[op++] = (byte) o1;
                        }

                        if (op < oLen) {
                            out[op++] = (byte) o2;
                        }
                        continue;
                    }

                    throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
                }

                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }

            return out;
        }
    }
}
