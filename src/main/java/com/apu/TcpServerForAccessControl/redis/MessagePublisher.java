package com.apu.TcpServerForAccessControl.redis;

public interface MessagePublisher {

    void publish(final String message);
    
}
