package com.apu.TcpServerForAccessControl.utils.message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class MessageChecker {
    
    private static final Logger logger = LogManager.getLogger(MessageChecker.class);
    
    public byte[] check(byte[] message) {
//        logger.info("Time: " + System.currentTimeMillis() + " ms.");
        return message;
    }

}
