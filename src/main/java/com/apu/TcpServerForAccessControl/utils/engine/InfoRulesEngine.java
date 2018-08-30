package com.apu.TcpServerForAccessControl.utils.engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.apu.TcpServerForAccessControlAPI.packet.InfoPacket;
import com.apu.TcpServerForAccessControlAPI.packet.MessageType;
import com.apu.TcpServerForAccessControlAPI.packet.RawPacket;

@Component
public class InfoRulesEngine {
    
    private static final Logger logger = LogManager.getLogger(InfoRulesEngine.class);
    
    public byte[] engine(RawPacket message) {
        if(!(message instanceof InfoPacket))
            return null;
        if(!message.getMessageType().equals(MessageType.INFO))
            return null;
        
        String msg;
        msg = "InfoRulesEngine answer - PacketType: " + message.getMessageType();
        logger.info(msg);
        return msg.getBytes();
    }

}
