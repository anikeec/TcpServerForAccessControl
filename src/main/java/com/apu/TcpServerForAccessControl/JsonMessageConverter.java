package com.apu.TcpServerForAccessControl;

import org.springframework.integration.support.converter.MapMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class JsonMessageConverter extends MapMessageConverter {

    @Override
    public Object fromMessage(Message<?> message, Class<?> clazz) {
        // TODO Auto-generated method stub
        return super.fromMessage(message, clazz);
    }
    
}
