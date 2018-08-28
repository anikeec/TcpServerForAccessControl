package com.apu.TcpServerForAccessControl.utils.message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class MessageDecoder {
    
    private static final Logger logger = LogManager.getLogger(MessageDecoder.class);
    
    public String decode(String message) {
        logger.info("Message: " + message);
        return message;
    }

}
