package com.apu.TcpServerForAccessControl.message;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class MessageChecker {
    
    private static final Logger logger = LogManager.getLogger(MessageChecker.class);
    
    public byte[] check(byte[] message) {
        return message;
    }
    
    public byte[] check(String message) {
        return message.getBytes();
    }
    
    public byte[] check(Map<String, String> message) {
        return new byte[10];
    }
    
    public byte[] check(Object message) {
        return new byte[10];
    }
    
    

}
