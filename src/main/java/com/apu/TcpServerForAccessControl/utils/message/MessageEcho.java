package com.apu.TcpServerForAccessControl.utils.message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class MessageEcho {
    
    private static final Logger logger = LogManager.getLogger(MessageEcho.class);
    
    public byte[] echo(byte[] message) {
//        logger.info("Time: " + System.currentTimeMillis() + " ms.");
        return message;
    }

}
