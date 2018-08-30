package com.apu.TcpServerForAccessControl.utils.message;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import org.junit.Test;

import com.apu.TcpServerForAccessControlAPI.packet.InfoPacket;
import com.apu.TcpServerForAccessControlAPI.packet.RawPacket;

public class MessageConverterTest {
    
    private byte[] serializePacket(RawPacket srcPacket) {
        String ret;   
        byte[] resultBytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);   
            out.writeObject(srcPacket);
            out.flush();
            resultBytes = bos.toByteArray();
            ret = new String(resultBytes);
        } catch (IOException ex) {
            ret = null;
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                ret = null;
            }
        }        
        return resultBytes;
    }

    @Test
    public void testConvert() {
        RawPacket srcPacket = new InfoPacket();
        srcPacket.setDeviceId(2);
        srcPacket.setPacketNumber(5);
        //fill packet
        
        byte[] srcPacketStr = serializePacket(srcPacket);
        
        MessageConverter mc = new MessageConverter();
        RawPacket resultPacket = mc.convert(srcPacketStr);        
        
        
        assertTrue(resultPacket.equals(srcPacket));
    }

}
