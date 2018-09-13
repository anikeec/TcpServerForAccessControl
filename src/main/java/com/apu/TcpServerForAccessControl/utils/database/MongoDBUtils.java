package com.apu.TcpServerForAccessControl.utils.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apu.TcpServerForAccessControlMongoDB.entity.Card;
import com.apu.TcpServerForAccessControlMongoDB.entity.Device;
import com.apu.TcpServerForAccessControlMongoDB.entity.EventType;
import com.apu.TcpServerForAccessControlMongoDB.entity.Rule;
import com.apu.TcpServerForAccessControlMongoDB.entity.RuleType;
import com.apu.TcpServerForAccessControlMongoDB.entity.User;
import com.apu.TcpServerForAccessControlMongoDB.repository.AccessMessageRepository;
import com.apu.TcpServerForAccessControlMongoDB.repository.AccessMessageWrongRepository;
import com.apu.TcpServerForAccessControlMongoDB.repository.CardRepository;
import com.apu.TcpServerForAccessControlMongoDB.repository.DeviceRepository;
import com.apu.TcpServerForAccessControlMongoDB.repository.EventMessageRepository;
import com.apu.TcpServerForAccessControlMongoDB.repository.EventTypeRepository;
import com.apu.TcpServerForAccessControlMongoDB.repository.RuleRepository;
import com.apu.TcpServerForAccessControlMongoDB.repository.RuleTypeRepository;
import com.apu.TcpServerForAccessControlMongoDB.repository.UserRepository;

@Component
public class MongoDBUtils {
    
    @Autowired
    private AccessMessageRepository accessMessageRepository;
    
    @Autowired
    private AccessMessageWrongRepository accessMessageWrongRepository;
    
    @Autowired
    private CardRepository cardRepository;
    
    @Autowired
    private DeviceRepository deviceRepository;
    
    @Autowired
    private EventMessageRepository eventMessageRepository;
    
    @Autowired
    private EventTypeRepository eventTypeRepository;
    
    @Autowired
    private RuleRepository ruleRepository;
    
    @Autowired
    private RuleTypeRepository ruleTypeRepository;
    
    @Autowired
    private UserRepository userRepository;

    public void fillRepositoriesTestData() {
        User user = new User(1, "Peter", "Petrov", "0501234567", "petrov@gmail.com");
        userRepository.save(user);
        
        Card card = new Card(1, "11111111", 1);
        cardRepository.save(card);
        
        Device device = new Device(1, 11, 1);
        deviceRepository.save(device);
        device = new Device(2, 12, 1);
        deviceRepository.save(device);
        device = new Device(3, 13, 1);
        deviceRepository.save(device);
        device = new Device(4, 14, 1);
        deviceRepository.save(device);
        device = new Device(5, 15, 1);
        deviceRepository.save(device);
        device = new Device(6, 16, 1);
        deviceRepository.save(device);
        
        EventType eventType = new EventType(1,"ACCESS_ALLOW");
        eventTypeRepository.save(eventType);
        eventType = new EventType(2,"ACCESS_DENIED");
        eventTypeRepository.save(eventType);
        eventType = new EventType(3,"ENTER_QUERY");
        eventTypeRepository.save(eventType);
        eventType = new EventType(4,"EXIT_QUERY");
        eventTypeRepository.save(eventType);
        
        RuleType ruleType = new RuleType(1, "ENTER");
        ruleTypeRepository.save(ruleType);
        
        Rule rule = new Rule(1, 1, 1, 3, 1, null, null);
        ruleRepository.save(rule);
        rule = new Rule(1, 1, 2, 3, 1, null, null);
        ruleRepository.save(rule);
        rule = new Rule(1, 1, 3, 3, 1, null, null);
        ruleRepository.save(rule);
        rule = new Rule(1, 1, 4, 3, 1, null, null);
        ruleRepository.save(rule);
        rule = new Rule(1, 1, 5, 3, 1, null, null);
        ruleRepository.save(rule);
        rule = new Rule(1, 1, 6, 3, 1, null, null);
        ruleRepository.save(rule);
    }
    
}
