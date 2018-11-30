package com.apu.TcpServerForAccessControl.endpoint;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;

@Component
public class HttpInboundEndpoint {

    public String get( LinkedMultiValueMap payload, @Headers Map<String, Object> headerMap )
    {
        // IntegrationRequestMappingHandlerMapping

        System.out.println( "Starting process the message [reciveing]" );

        return "{HelloMessage: \"Hello\"}";
    }
    
    public Message<?> get(Message<?> msg) {
        Object result = msg.getPayload();
//        log.info("GET method");
//        List<Customer> custLst = custService.getAll();
        return MessageBuilder
                .withPayload("Server answer")
                .copyHeadersIfAbsent(msg.getHeaders())
                .setHeader("http_statusCode", HttpStatus.OK)
                .build();
    }
    
    public String get(String msg) {
//      Map result = msg.getPayload();
//      log.info("GET method");
//      List<Customer> custLst = custService.getAll();
//      return MessageBuilder
//              .withPayload("Server answer")
//              .copyHeadersIfAbsent(msg.getHeaders())
//              .setHeader("http_statusCode", HttpStatus.OK)
//              .build();
        return "TRUE";
  }
    
    
    
}
