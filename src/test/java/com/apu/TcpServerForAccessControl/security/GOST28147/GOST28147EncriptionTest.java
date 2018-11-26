/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apu.TcpServerForAccessControl.security.GOST28147;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author apu
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class GOST28147EncriptionTest {
    
//    private static String DECODED_STRING = "It is a test string.";
    
    private static byte[] SECURITY_KEY_BYTES = new byte[] {
                                                        84, 104, 105, 115, 32, 105, 
                                                        115, 32, 115, 101, 99, 114, 
                                                        101, 116, 32, 107, 101, 121, 
                                                        32, 102, 111, 114, 32, 116, 
                                                        101, 115, 116, 32, 112, 114, 111, 99};
    
    private static byte[] DECODED_STRING_BYTES = new byte[] {
                                                        73, 116, 32, 105, 115, 32, 
                                                        97, 32, 116, 101, 115, 116, 
                                                        32, 115, 116, 114, 105, 110, 103, 46 };
    
    private static byte[] ENCODED_STRING_BYTES = new byte[] {
                                                        82, -98, -35, 20, -85, -27, 
                                                        70, 71, -34, -47, 49, -81, 
                                                        -109, 16, 34, 74, -8, -112, 
                                                        6, -118, 108, 12, 9, -92 };
    
    public GOST28147EncriptionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of encodeString method, of class GOST28147Encription.
     * @throws UnsupportedEncodingException 
     */
    @Test
    public void testEncodeBytes() throws UnsupportedEncodingException {
        System.out.println("encodeBytes");
        GOST28147Encription instance = new GOST28147Encription(SECURITY_KEY_BYTES);
        byte[] expResult = ENCODED_STRING_BYTES;
        byte[] result = instance.encodeBytes(DECODED_STRING_BYTES);
        for(int i=0; i<result.length; i++)
            assertEquals(expResult[i], result[i]);
    }

    /**
     * Test of encodeString method, of class GOST28147Encription.
     * @throws UnsupportedEncodingException 
     */
//    @Test
//    public void testEncodeString() throws UnsupportedEncodingException {
//        System.out.println("encodeString");
//        String src = DECODED_STRING;
//        GOST28147Encription instance = new GOST28147Encription(SECURITY_KEY_BYTES);
//        String expResult = new String(ENCODED_STRING_BYTES);
//        String result = instance.encodeString(src);
//        assertEquals(expResult, result);
//    }
    
    /**
     * Test of decodeString method, of class GOST28147Encription.
     * @throws UnsupportedEncodingException 
     */
    @Test
    public void testDecodeBytes() throws UnsupportedEncodingException {
        System.out.println("decodeBytes");
        GOST28147Encription instance = new GOST28147Encription(SECURITY_KEY_BYTES);
        byte[] expResult = DECODED_STRING_BYTES;
        byte[] result = instance.decodeBytes(ENCODED_STRING_BYTES);
        result = instance.removeZeroEnd(result);
        for(int i=0; i<result.length; i++)
            assertEquals(expResult[i], result[i]);
    }

    /**
     * Test of decodeString method, of class GOST28147Encription.
     * @throws UnsupportedEncodingException 
     */
//    @Test
//    public void testDecodeString() throws UnsupportedEncodingException {
//        System.out.println("decodeString");
//        String src = new String(ENCODED_STRING_BYTES, StandardCharsets.UTF_16);
//        GOST28147Encription instance = new GOST28147Encription(SECURITY_KEY_BYTES);
//        String expResult = DECODED_STRING;
//        String result = instance.decodeString(src);
//        assertEquals(expResult, result);
//    }
    
}
