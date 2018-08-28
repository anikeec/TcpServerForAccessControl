package com.apu.TcpServerForAccessControl.utils.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class LogWriter {
    
    private static final Logger logger = LogManager.getLogger(LogWriter.class);
    
    public String writeToLog(String message) {
        String msg = null;
        if(message.equals("info") || message.equals("service"))
            msg = null;
        else
            msg = "Logger Message: " + message;
        logger.info(msg);
        return msg;
    }

}
