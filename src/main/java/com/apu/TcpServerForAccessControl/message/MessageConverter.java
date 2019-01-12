package com.apu.TcpServerForAccessControl.message;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.apu.TcpServerForAccessControl.converter.JsonSerializer;
import com.apu.TcpServerForAccessControlAPI.packet.RawPacket;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class MessageConverter {
    
    private static final Logger logger = LogManager.getLogger(MessageConverter.class);
    
    @Autowired
    JsonSerializer jsonSerializer;
    
    public RawPacket convert(byte[] packet) {
        
        RawPacket pkt = new RawPacket();
        try {
            pkt = jsonSerializer.deserializeBytes(packet);
        } catch(Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        
        return pkt;
    }
    
    public byte[] convertBack(RawPacket packet) {
        byte[] packetBytes = new byte[0];
        try {
            packetBytes = jsonSerializer.serializeBytes(packet);
        } catch(Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        
        return packetBytes;
    }

}
