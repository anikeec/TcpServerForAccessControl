package com.apu.TcpServerForAccessControl.message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ErrorHandler {

    private static final Logger logger = LogManager.getLogger(ErrorHandler.class);

    public byte[] handle(byte[] message) {
        logger.error("Error: " + message);
        return message;
    }

    public Object handle(Object message) {
        logger.error("Error: " + message);
        return message;
    }

    public String handle(String message) {
        logger.error("Error: " + message);
        return message;
    }

}
