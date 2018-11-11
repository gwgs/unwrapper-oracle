package com.salvis.unwrapper.util;


import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.zip.DataFormatException;

import static org.junit.Assert.assertEquals;


public class UnwrapperTest {

    @Test
    public void unwrap() throws NoSuchAlgorithmException, IOException, DataFormatException {
        final String INPUT = "STUFF_TO_BE_IGNORED\n" +
                "create or replace PROCEDURE sample_procedure wrapped\n" +
                "a000000\n" +
                "1\n" +
                "abcd\n" +
                "abcd\n" +
                "abcd\n" +
                "abcd\n" +
                "abcd\n" +
                "abcd\n" +
                "abcd\n" +
                "abcd\n" +
                "abcd\n" +
                "abcd\n" +
                "abcd\n" +
                "abcd\n" +
                "abcd\n" +
                "abcd\n" +
                "abcd\n" +
                "7\n" +
                "75 a6\n" +
                "FSfycu7ZcUm1cP88FuVQDiSeSqgwg5nnm7+fMr2ywFwW3EdihaHwlhaXrtwuPmLyXKV0i8DA\n" +
                "Mv7ShglpaedKdDzHUpuySv4osr3nsrMdBjAsriTqsjJ0eSaUJvpjbCY+I/zsPHHiP9Ger0q8\n" +
                "+yLiCPt8E9iIpqeNFPQ=\n" +
                "\n" +
                "STUFF_TO_BE_IGNORED";

        final String OUTPUT = "PROCEDURE sample_procedure IS\n" +
                "BEGIN\n" +
                "   SYS.DBMS_OUTPUT.PUT_LINE('sample_procedure executed.');\n" +
                "END SAMPLE_PROCEDURE;";

        assertEquals(OUTPUT, Unwrapper.unwrap(INPUT));
    }
}
