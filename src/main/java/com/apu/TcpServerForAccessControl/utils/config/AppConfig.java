package com.apu.TcpServerForAccessControl.utils.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.support.json.JsonObjectMapper;
import org.springframework.integration.support.json.JsonObjectMapperProvider;

import com.apu.TcpServerForAccessControl.utils.redis.MessagePublisher;
import com.apu.TcpServerForAccessControl.utils.redis.RedisMessagePublisher;
import com.apu.TcpServerForAccessControl.utils.redis.RedisMessageSubscriber;
import com.apu.TcpServerForAccessControlAPI.packet.AccessPacket;
import com.apu.TcpServerForAccessControlAPI.packet.RawPacket;
import com.apu.TcpServerForAccessControlAPI.packet.ServicePacket;
import com.apu.TcpServerForAccessControlDB.entity.RuleType;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@ComponentScan({"com.apu.TcpServerForAccessControlDB","com.apu.TcpServerForAccessControl.utils"})
@EnableRedisRepositories(basePackages= {"com.apu.TcpServerForAccessControlDB.repository"})
public class AppConfig {
    
//    @Bean("jsonObjectMapper")
    public JsonObjectMapper<JsonNode, JsonParser> jsonObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
        mapper.registerSubtypes(RawPacket.class);
        mapper.registerSubtypes(AccessPacket.class);
        mapper.registerSubtypes(ServicePacket.class);
        return new Jackson2JsonObjectMapper(mapper);
    }
    
//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setSerializationInclusion(Include.NON_NULL);
//        objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
//        objectMapper.registerSubtypes(RawPacket.class);
//        objectMapper.registerSubtypes(AccessPacket.class);
//        objectMapper.registerSubtypes(ServicePacket.class);
//        return objectMapper;
//    }
    
    @Bean("jsonToObjectTransformer")
//    @Transformer(inputChannel="tcpInputChannel", outputChannel="tcpInputObjectChannel")
    JsonToObjectTransformer jsonToObjectTransformer() {
        return new JsonToObjectTransformer(jsonObjectMapper());
    }
    
//    @Bean
//    @Transformer(inputChannel="input", outputChannel="output")
//    JsonToObjectTransformer jsonToObjectTransformer() {
//        JsonObjectMapper<?, ?> mapper = JsonObjectMapperProvider.newInstance();
//        mapper.populateJavaTypes(map, object);
//        return new JsonToObjectTransformer(mapper);
//    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return template;
    }

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new RedisMessageSubscriber());
    }

    @Bean
    RedisMessageListenerContainer redisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(messageListener(), topic());
        return container;
    }

    @Bean
    MessagePublisher redisPublisher() {
        return new RedisMessagePublisher(redisTemplate(), topic());
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("pubsub:queue");
    }
    
}
