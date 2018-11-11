package com.salvis.unwrapper.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;


/**
 * @author Philipp Salvisberg (https://www.salvis.com/blog/plsql-unwrapper-for-sql-developer/)
 */
public class Unzipper {
    public Unzipper() {
    }

    public static byte[] unzip(final byte[] zipped) throws DataFormatException, IOException {
        final Inflater inflater = new Inflater();
        inflater.setInput(zipped);
        final ByteArrayOutputStream os = new ByteArrayOutputStream(zipped.length);
        final byte[] buffer = new byte[1024];

        while (!inflater.finished()) {
            final int count = inflater.inflate(buffer);
            os.write(buffer, 0, count);
        }

        os.close();
        return os.toByteArray();
    }
}
