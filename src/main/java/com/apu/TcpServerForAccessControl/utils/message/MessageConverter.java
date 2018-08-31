package com.apu.TcpServerForAccessControl.utils.message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.apu.TcpServerForAccessControlAPI.packet.InfoPacket;
import com.apu.TcpServerForAccessControlAPI.packet.RawPacket;
import com.apu.TcpServerForAccessControlAPI.packet.ServicePacket;

@Component
public class MessageConverter {
    
    private static final Logger logger = LogManager.getLogger(MessageConverter.class);
    
    public RawPacket convert(byte[] packet) {
        
        RawPacket pkt = new RawPacket();
        try {
            pkt = deserialize(packet);
        } catch(ClassNotFoundException | IOException e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        
//        logger.info("Message: " + message);
        return pkt;
    }
    
    public byte[] convertBack(RawPacket packet) {
        byte[] packetBytes = new byte[0];
        try {
            packetBytes = serializePacket(packet);
        } catch(IOException e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        
//        logger.info("Message: " + message);
        return packetBytes;
    }
    
    private RawPacket deserialize(byte[] srcPacketStr) throws ClassNotFoundException, IOException {
        RawPacket resultPacket = null;
        ByteArrayInputStream bis = new ByteArrayInputStream(srcPacketStr);
        ObjectInput in = null;
        try {
          in = new ObjectInputStream(bis);
          Object o = in.readObject(); 
          if(o instanceof RawPacket) {
              if(o instanceof ServicePacket) {
                  resultPacket = (ServicePacket)o;
              } else if(o instanceof InfoPacket) {
                  resultPacket = (InfoPacket)o;
              } else {
                  resultPacket = (RawPacket)o;
              }
          }
        } finally {
                if (in != null) {
                  in.close();
                }
        }
        return resultPacket;
    }
    
    private static byte[] serializePacket(RawPacket srcPacket) throws IOException {   
        byte[] resultBytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);   
            out.writeObject(srcPacket);
            out.flush();
            resultBytes = bos.toByteArray();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
            }
        }        
        return resultBytes;
    }

}
