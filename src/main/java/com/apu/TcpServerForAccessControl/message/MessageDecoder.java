package com.apu.TcpServerForAccessControl.message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class MessageDecoder {
    
    private static final Logger logger = LogManager.getLogger(MessageDecoder.class);
    
    public byte[] decode(byte[] message) {
        return message;
    }
    
    public byte[] encode(byte[] message) {
        return message;
    }

}
