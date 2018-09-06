package com.apu.TcpServerForAccessControl.utils.engine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apu.TcpServerForAccessControlAPI.packet.MessageType;
import com.apu.TcpServerForAccessControlAPI.packet.RawPacket;
import com.apu.TcpServerForAccessControlDB.entity.AccessMessage;
import com.apu.TcpServerForAccessControlDB.entity.AccessMessageWrong;
import com.apu.TcpServerForAccessControlDB.entity.Card;
import com.apu.TcpServerForAccessControlDB.entity.Device;
import com.apu.TcpServerForAccessControlDB.entity.EventMessage;
import com.apu.TcpServerForAccessControlDB.entity.EventType;
import com.apu.TcpServerForAccessControlDB.entity.Rule;
import com.apu.TcpServerForAccessControlDB.entity.RuleType;
import com.apu.TcpServerForAccessControlDB.repository.AccessMessageRepository;
import com.apu.TcpServerForAccessControlDB.repository.AccessMessageWrongRepository;
import com.apu.TcpServerForAccessControlDB.repository.CardRepository;
import com.apu.TcpServerForAccessControlDB.repository.DeviceRepository;
import com.apu.TcpServerForAccessControlDB.repository.EventMessageRepository;
import com.apu.TcpServerForAccessControlDB.repository.EventTypeRepository;
import com.apu.TcpServerForAccessControlDB.repository.RuleRepository;
import com.apu.TcpServerForAccessControlDB.repository.RuleTypeRepository;
import com.apu.TcpServerForAccessControlDB.repository.UserRepository;
import com.apu.TcpServerForAccessControlAPI.packet.AccessPacket;
import com.apu.TcpServerForAccessControlAPI.packet.InfoPacket;

@Component
public class AccessRulesEngine {
    
    private static final Logger logger = LogManager.getLogger(AccessRulesEngine.class);
    
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
  
    
    public RawPacket engine(RawPacket message) {
        if(!(message instanceof AccessPacket))
            return null;
        if(!message.getMessageType().equals(MessageType.ACCESS))
            return null;
        
        RawPacket retPacket = null;
        
        logger.info("Time: " + System.currentTimeMillis() + " ms.");
        
        /*
         * here we have to:
         * - get packetId from message
         * - get deviceId from message
         * - get cardNumber from message
         * - get eventType from message
         * - get time from message
         * 
         * - check all parameters are not null
         * 
         * - compare packetId with lastPacketId from DB 
         * 
         * - get deviceId from DB
         * - get cardId from DB
         * - write message to DB to access_message table
         * - find in DB rules for combination deviceId+cardId
         *  - if nothing was found then access denied and
         *   - write access denied message (+cardNumber+deviceId) to DB to event_message table
         *   - return InfoPacket with access denied
         *  - if something was found
         *   - find all rules with appropriate eventType
         *    - if nothing was found then access denied and
         *     - write access denied message (+cardNumber+deviceId+eventType) to DB to event_message table
         *     - return InfoPacket with access denied
         *    - if something was found
         *     - find all rules with appropriate time borders and where time from message is inside this period of time
         *      - if nothing was found then access denied and
         *       - write access denied message (+cardNumber+deviceId+eventType+time) to DB to event_message table
         *       - return InfoPacket with access denied
         */
        
        AccessPacket mess = (AccessPacket)message;

        Integer packetNumber = mess.getPacketNumber();
        Integer deviceNumber = mess.getDeviceNumber();
        Integer eventId = mess.getEventId();
        String cardNumber = mess.getCardNumber();
        Date dateTime = mess.getTime();
        
        //check all parameters are not null
        //if some of the parameters is wrong return error message
        if((packetNumber == null) ||
           (deviceNumber == null) ||
           (cardNumber == null) ||
           (dateTime == null)) {
            retPacket = new InfoPacket("Received packet is wrong");
        }
        
        //check if packet wrong and write it to access_message_wrong table
        if(retPacket != null) {
            AccessMessageWrong accessMessWrong = 
                    new AccessMessageWrong(cardNumber, deviceNumber, eventId, dateTime, "wrong packet");
            accessMessageWrongRepository.save(accessMessWrong);
        }
        
        //get deviceId from DB
        Device device = null;
        Integer lastPacketIdStored = null;
        if(retPacket == null) {
            List<Device> deviceList = deviceRepository.findByDeviceNumber(deviceNumber);
            if(deviceList.size() != 0) {
                device = deviceList.get(0);//maybe I have to check to have only one deviceId with this deviceNumber    
                lastPacketIdStored = device.getLastPacketId();
            } else {
                AccessMessageWrong accessMessWrong = 
                        new AccessMessageWrong(cardNumber, deviceNumber, eventId, dateTime, "wrong deviceNumber");
                accessMessageWrongRepository.save(accessMessWrong);
                retPacket = new InfoPacket("Received deviceNumber is wrong");
            }
        }
        
        //compare packetId with lastPacketId from DB 
        if(retPacket == null) {
            if(lastPacketIdStored == null) {
                lastPacketIdStored = 0;
                device.setLastPacketId(lastPacketIdStored);
                deviceRepository.save(device);
            }
            //add some mathematics links with borders of type
//            if(packetNumber == (lastPacketIdStored + 1)) {
//                device.setLastPacketId(packetNumber);
//                deviceRepository.save(device);
//            } else {
//                AccessMessageWrong accessMessWrong = 
//                        new AccessMessageWrong(cardNumber, deviceNumber, eventId, dateTime, "wrong packetId");
//                accessMessageWrongRepository.save(accessMessWrong);
//                retPacket = new InfoPacket("Received packetId is wrong");
//            }
        }
        
        //get cardId from DB
        Card card = null;
        if(retPacket == null) {
            List<Card> cardList = cardRepository.findByCardNumber(cardNumber);
            if(cardList.size() != 0) {
                card = cardList.get(0);//maybe I have to check to have only one cardId with this cardNumber            
            } else {
                AccessMessageWrong accessMessWrong = 
                        new AccessMessageWrong(cardNumber, deviceNumber, eventId, dateTime, "wrong cardNumber");
                accessMessageWrongRepository.save(accessMessWrong);
                retPacket = new InfoPacket("Received cardNumber is wrong");
            }      
        }
        
        //get eventType
        EventType eventType = null;
        if(retPacket == null) {
            List<EventType> eventTypeList = eventTypeRepository.findByEventId(eventId);
            if(eventTypeList.size() == 0) {
                AccessMessageWrong accessMessWrong = 
                        new AccessMessageWrong(cardNumber, deviceNumber, eventId, dateTime, "wrong eventId for this cardNumber");
                accessMessageWrongRepository.save(accessMessWrong);
                retPacket = new InfoPacket("Access denied for this cardNumber on this device. Wrong eventId.");
            } else {
                eventType = eventTypeList.get(0);   //maybe its wrong
            }
        }
        
        //find in DB rules for combination deviceId+cardId - maybe it will be good to add eventId
        List<Rule> ruleList = null;
        if(retPacket == null) {
            ruleList = ruleRepository.findByDeviceIdAndCardId(device, card);
            if(ruleList.size() == 0) {
                AccessMessageWrong accessMessWrong = 
                        new AccessMessageWrong(cardNumber, deviceNumber, eventId, dateTime, "wrong rule for this cardNumber");
                accessMessageWrongRepository.save(accessMessWrong);
                retPacket = new InfoPacket("Access denied for this cardNumber on this device");
            }
        }
        
        //find rules with appropriated ruleType (eventType in packet)
        List<Rule> trueRuleList = new ArrayList<>();
        if(retPacket == null) {
            for(Rule rule:ruleList) {
                int ruleEventId = rule.getEventId().getEventId();
                if(ruleEventId == eventId) {
                    trueRuleList.add(rule);
                }
            }
            //check if we have true rules
            if(trueRuleList.size() == 0) {
                AccessMessageWrong accessMessWrong = 
                        new AccessMessageWrong(cardNumber, deviceNumber, eventId, dateTime, "wrong rule for this cardNumber");
                accessMessageWrongRepository.save(accessMessWrong);
                retPacket = new InfoPacket("Access denied for this cardNumber on this device");
            }
        }       
        
        //check time borders
        if(retPacket == null) {
            for(Rule rule:trueRuleList) {
                Date dateBegin = rule.getDateBegin();
                Date dateEnd = rule.getDateEnd();
                if((dateBegin != null) && 
                   (dateEnd != null)) {
                    if((dateTime.getTime() >= dateBegin.getTime()) && 
                       (dateTime.getTime() <= dateEnd.getTime())) {
                      //access allow
                        List<EventType> eventTypeListTemp = eventTypeRepository.findByDescription(
                                com.apu.TcpServerForAccessControlAPI.packet.EventType.ACCESS_ALLOW.toString());
                        
                        AccessPacket accessPacket = new AccessPacket();                    
                        accessPacket.setDeviceNumber(deviceNumber);
                        accessPacket.setPacketNumber(packetNumber);
                        accessPacket.setTime(new Date());
                        accessPacket.setEventId(eventTypeListTemp.get(0).getEventId());                    
                        retPacket = accessPacket;
                    }
                } else {
                    //access allow
                    List<EventType> eventTypeListTemp = eventTypeRepository.findByDescription(
                            com.apu.TcpServerForAccessControlAPI.packet.EventType.ACCESS_ALLOW.toString());
                    
                    AccessPacket accessPacket = new AccessPacket();                    
                    accessPacket.setDeviceNumber(deviceNumber);
                    accessPacket.setPacketNumber(packetNumber);
                    accessPacket.setTime(new Date());
                    accessPacket.setEventId(eventTypeListTemp.get(0).getEventId());                    
                    retPacket = accessPacket;
                    
                    //write to access_message
                    AccessMessage accessMess = 
                            new AccessMessage(device, card, eventType, dateTime, "Access OK");
                    accessMessageRepository.save(accessMess);
                    
                    //write to event_message
                    EventMessage eventMessage = 
                            new EventMessage(device, eventTypeListTemp.get(0), accessMess, rule, new Date(), "Access allow");                  
                    eventMessageRepository.save(eventMessage);
                }
            }
            
            
            
            if((retPacket != null) && (retPacket instanceof AccessPacket)) {
//                AccessMessage accessMess = 
//                        new AccessMessage(device, card, eventType, dateTime, "wrong rule for this cardNumber");
//                accessMessageRepository.save(accessMess);
            } else {
                AccessMessageWrong accessMessWrong = 
                        new AccessMessageWrong(cardNumber, deviceNumber, eventId, dateTime, "wrong rule for this cardNumber");
                accessMessageWrongRepository.save(accessMessWrong);
                retPacket = new InfoPacket("Access denied for this cardNumber on this device");
            }
        }
        
        
        logger.info("Time: " + System.currentTimeMillis() + " ms.");
        String msg;        
        msg = "AccessRulesEngine answer - PacketType: " + message.getMessageType();
        logger.info(msg);
        return retPacket;
    }

}
