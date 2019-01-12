package com.apu.TcpServerForAccessControl.message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class MessageEcho {
    
    private static final Logger logger = LogManager.getLogger(MessageEcho.class);
    
    public byte[] echo(byte[] message) {
        return message;
    }

}
