package com.apu.TcpServerForAccessControl.security.GOST28147;

import java.io.UnsupportedEncodingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.engines.GOST28147Engine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.*;
import org.bouncycastle.crypto.params.KeyParameter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GOST28147Encription {
    
//    @Value("${tcp-server-ac.security.key}")
    private byte[] security_key;
    
    private static final Logger logger = LogManager.getLogger(GOST28147Encription.class);
    
    public byte[] encodeBytes(byte[] srcBytes) throws UnsupportedEncodingException {
        byte[] encodedBytesZP = this.EncodeZeroPadding(srcBytes, security_key);
        return encodedBytesZP;
    }
    
//    public String encodeString(String src) throws UnsupportedEncodingException {
//        byte[] strBytesZP = src.getBytes("UTF-8");
//        byte[] encodedBytesZP = this.EncodeZeroPadding(strBytesZP, security_key.getBytes("UTF-8"));
//        return new String(encodedBytesZP, "UTF-8");
//    }
    
    public byte[] decodeBytes(byte[] srcBytes) throws UnsupportedEncodingException {
        byte[] decodedBytesZP = this.DecodeZeroPadding(srcBytes, security_key);
        return decodedBytesZP;
    }
    
//    public String decodeString(String src) throws UnsupportedEncodingException {
//        byte[] strBytesZP = src.getBytes("UTF-8");
//        byte[] decodedBytesZP = this.DecodeZeroPadding(strBytesZP, security_key.getBytes("UTF-8"));
//        return new String(decodedBytesZP, "UTF-8");
//    }
    
    public byte[] removeZeroEnd(byte[] srcBytes) {       
        int nonZeroIndex = srcBytes.length - 1;
        while(nonZeroIndex>0) {
            if(srcBytes[nonZeroIndex] != 0)
                break;
            nonZeroIndex--;
        }
        int resBufLength = nonZeroIndex + 1;
        byte[] retBytes = new byte[resBufLength];
        for(int i=0; i<resBufLength; i++) {
            retBytes[i] = srcBytes[i];
        }
        return retBytes;
    }

    public GOST28147Encription(byte[] security_key) {
        this.security_key = security_key;
    }

    private String Encode(String str, byte[] key) {
        return processEncoding(true, str, key);
    }

    private byte[] Encode(byte[] str, byte[] key) {
        return processEncoding(true, str, key);
    }
    
    private byte[] EncodeZeroPadding(byte[] str, byte[] key) {
        return processEncodingZeroPadding(true, str, key);
    }
    
    private String Decode(String str, byte[] key) {
        return processEncoding(false, str, key);
    }

    private byte[] Decode(byte[] str, byte[] key) {
        return processEncoding(false, str, key);
    }
    
    private byte[] DecodeZeroPadding(byte[] str, byte[] key) {
        return processEncodingZeroPadding(false, str, key);
    }
    
    private String Decode(String str, byte[] key, int length) {
        return processEncoding(false, str, key, length);
    }

    private byte[] Decode(byte[] str, byte[] key, int length) {
        return trimBytes(processEncoding(false, str, key), length);
    }

    private String processEncoding(boolean ende, String str, byte[] key) {
        byte[] bytes = processEncoding(ende, str.getBytes(), key);
        return new String(bytes);
    }

    private String processEncoding(boolean ende, String str, byte[] key, int length) {
        byte[] bytes = trimBytes(processEncoding(ende, str.getBytes(), key), length);
        return new String(bytes);
    }
    
    private byte[] processEncodingZeroPadding(boolean ende, byte[] inBytes, byte[] key) {
        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(
                new GOST28147Engine()), new ZeroBytePadding());
        cipher.init(ende, new KeyParameter(key));
        byte[] outBytes = new byte[cipher.getOutputSize(inBytes.length)];
        int len = cipher.processBytes(inBytes, 0, inBytes.length, outBytes, 0);
        try {
            cipher.doFinal(outBytes, len);
        } catch (CryptoException e) {
            logger.error("Exception: " + e.toString());
        }
        return outBytes;
    }
    
    private byte[] processEncoding(boolean ende, byte[] inBytes, byte[] key) {
        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(
                new GOST28147Engine()));
        cipher.init(ende, new KeyParameter(key));
        byte[] outBytes = new byte[cipher.getOutputSize(inBytes.length)];
        int len = cipher.processBytes(inBytes, 0, inBytes.length, outBytes, 0);
        try {
            cipher.doFinal(outBytes, len);
        } catch (CryptoException e) {
            logger.error("Exception: " + e.toString());
        }
        return outBytes;
    }

    private byte[] trimBytes(byte[] bytes, int length) {
        byte[] outBytesTrimmed = new byte[length];
        for (int i = 0; i < length; i++) {
            outBytesTrimmed[i] = bytes[i];
        }
        return outBytesTrimmed;
    }
    
}
