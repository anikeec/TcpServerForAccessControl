package com.apu.TcpServerForAccessControl;

import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.support.json.JsonObjectMapper;

import com.apu.TcpServerForAccessControlAPI.packet.AccessPacket;
import com.apu.TcpServerForAccessControlAPI.packet.RawPacket;
import com.apu.TcpServerForAccessControlAPI.packet.ServicePacket;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperFactory {

    public static JsonObjectMapper<JsonNode, JsonParser> getMapper() {
        ObjectMapper mapper = new ObjectMapper();
//      mapper.registerModule(new JavaTimeModule());
        mapper.registerSubtypes(RawPacket.class);
        mapper.registerSubtypes(AccessPacket.class);
        mapper.registerSubtypes(ServicePacket.class);
        return new Jackson2JsonObjectMapper(mapper);
    }
    
}
