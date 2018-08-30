package com.apu.TcpServerForAccessControl.utils.engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.apu.TcpServerForAccessControlAPI.packet.MessageType;
import com.apu.TcpServerForAccessControlAPI.packet.RawPacket;

@Component
public class ServiceRulesEngine {
    
    private static final Logger logger = LogManager.getLogger(ServiceRulesEngine.class);
    
    public String engine(RawPacket message) {
        String msg = null;
        if(message.getMessageType().equals(MessageType.SERVICE))
            return null;
        msg = "ServiceRulesEngine answer - PacketType: " + message.getMessageType();
        logger.info(msg);
        return msg;
    }

}
