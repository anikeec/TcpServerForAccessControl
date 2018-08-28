package com.apu.TcpServerForAccessControl.utils.engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ServiceRulesEngine {
    
    private static final Logger logger = LogManager.getLogger(ServiceRulesEngine.class);
    
    public String engine(String message) {
        String msg = null;
        if(message.equals("service"))
            msg = "ServiceRulesEngine answer - Message: " + message;
        logger.info(msg);
        return msg;
    }

}
