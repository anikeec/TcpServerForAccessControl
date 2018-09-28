package com.apu.TcpServerForAccessControl.utils.message;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class JsonMessageConverter {

    public Object toJson(Message<?> message) {
        // TODO Auto-generated method stub
        return null;
    }

    public Object toObject(byte[] message) {
        // TODO Auto-generated method stub
        return message;
    }
    
}
