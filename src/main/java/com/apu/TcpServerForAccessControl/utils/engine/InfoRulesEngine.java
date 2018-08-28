package com.apu.TcpServerForAccessControl.utils.engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class InfoRulesEngine {
    
    private static final Logger logger = LogManager.getLogger(InfoRulesEngine.class);
    
    public String engine(String message) {
        String msg = null;
        if(message.equals("info"))
            msg = "InfoRulesEngine answer - Message: " + message;
        logger.info(msg);
        return msg;
    }

}
