package com.apu.TcpServerForAccessControl.utils.redis;

public interface MessagePublisher {

    void publish(final String message);
    
}
