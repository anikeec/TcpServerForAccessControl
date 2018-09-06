package com.apu.TcpServerForAccessControl.utils.message;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.apu.TcpServerForAccessControlAPI.packet.InfoPacket;
import com.apu.TcpServerForAccessControlAPI.packet.RawPacket;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"/META-INF/spring/integration/integration.xml"})
public class MessageConverterTest {
    
//    @Autowired
//    MessageConverter mc;

    @Test
    public void testConvert() {
//        RawPacket srcPacket = new InfoPacket();
//        srcPacket.setDeviceNumber(2);
//        srcPacket.setPacketNumber(5);
//        
//        byte[] srcPacketStr = serializePacket(srcPacket);        
//        RawPacket resultPacket = mc.convert(srcPacketStr);       
//        
//        assertTrue(
//                (resultPacket.getDeviceNumber().equals(srcPacket.getDeviceNumber())) &&
//                (resultPacket.getPacketNumber().equals(srcPacket.getPacketNumber()))
//                );
    }
    
    private byte[] serializePacket(RawPacket srcPacket) {   
        byte[] resultBytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);   
            out.writeObject(srcPacket);
            out.flush();
            resultBytes = bos.toByteArray();
        } catch (IOException ex) {
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
            }
        }        
        return resultBytes;
    }

}
