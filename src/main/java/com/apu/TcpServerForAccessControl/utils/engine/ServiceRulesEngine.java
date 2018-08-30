package com.apu.TcpServerForAccessControl.utils.engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.apu.TcpServerForAccessControlAPI.packet.MessageType;
import com.apu.TcpServerForAccessControlAPI.packet.RawPacket;
import com.apu.TcpServerForAccessControlAPI.packet.ServicePacket;

@Component
public class ServiceRulesEngine {
    
    private static final Logger logger = LogManager.getLogger(ServiceRulesEngine.class);
    
    public byte[] engine(RawPacket message) {
        if(!(message instanceof ServicePacket))
            return null;
        if(!message.getMessageType().equals(MessageType.SERVICE))
            return null;
        
        String msg;        
        msg = "ServiceRulesEngine answer - PacketType: " + message.getMessageType();
        logger.info(msg);
        return msg.getBytes();
    }

}
