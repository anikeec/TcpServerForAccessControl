package com.apu.TcpServerForAccessControl.utils.engine;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apu.TcpServerForAccessControlAPI.packet.MessageType;
import com.apu.TcpServerForAccessControlAPI.packet.RawPacket;
import com.apu.TcpServerForAccessControl.entity.AccessMessage;
import com.apu.TcpServerForAccessControl.entity.AccessMessageWrong;
import com.apu.TcpServerForAccessControl.entity.Card;
import com.apu.TcpServerForAccessControl.entity.Device;
import com.apu.TcpServerForAccessControl.repository.AccessMessageRepository;
import com.apu.TcpServerForAccessControl.repository.AccessMessageWrongRepository;
import com.apu.TcpServerForAccessControl.repository.CardRepository;
import com.apu.TcpServerForAccessControl.repository.DeviceRepository;
import com.apu.TcpServerForAccessControl.repository.EventMessageRepository;
import com.apu.TcpServerForAccessControl.repository.EventTypeRepository;
import com.apu.TcpServerForAccessControl.repository.RuleRepository;
import com.apu.TcpServerForAccessControl.repository.RuleTypeRepository;
import com.apu.TcpServerForAccessControl.repository.UserRepository;
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
         * 
         * - write message to DB to access_message table
         * - find in DB rules for combination deviceId+cardNumber
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

        Integer packetId = mess.getPacketNumber();
        Integer deviceNumber = mess.getDeviceNumber();
        Integer eventId = mess.getEventId();
        String cardNumber = mess.getCardNumber();
        Date dateTime = mess.getTime();
        
        //check all parameters are not null
        //if some of the parameters is wrong return error message
        if((packetId == null) ||
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
        if(retPacket == null) {
            List<Device> deviceList = deviceRepository.findByDeviceNumber(deviceNumber);
            if(deviceList.size() != 0) {
                device = deviceList.get(0);//maybe I have to check to have only one deviceId with this deviceNumber            
            } else {
                AccessMessageWrong accessMessWrong = 
                        new AccessMessageWrong(cardNumber, deviceNumber, eventId, dateTime, "wrong deviceNumber");
                accessMessageWrongRepository.save(accessMessWrong);
                retPacket = new InfoPacket("Received deviceNumber is wrong");
            }
        }
        
        //compare packetId with lastPacketId from DB 
        Integer lastPacketIdStored = device.getLastPacketId();
        if(lastPacketIdStored == null) {
            lastPacketIdStored = 0;
            device.setLastPacketId(lastPacketIdStored);
            deviceRepository.save(device);
        }
        //add some mathematics links with borders of type
        if(packetId == (lastPacketIdStored + 1)) {
            device.setLastPacketId(packetId);
            deviceRepository.save(device);
        } else {
            AccessMessageWrong accessMessWrong = 
                    new AccessMessageWrong(cardNumber, deviceNumber, eventId, dateTime, "wrong packetId");
            accessMessageWrongRepository.save(accessMessWrong);
            retPacket = new InfoPacket("Received packetId is wrong");
        }
        
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
        
        if(retPacket == null) {
            
        }
        
        String msg;        
        msg = "AccessRulesEngine answer - PacketType: " + message.getMessageType();
        logger.info(msg);
        return message;
    }

}