package com.apu.TcpServerForAccessControl.utils.engine;

import java.text.SimpleDateFormat;
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
        
//        logger.info("Time: " + System.currentTimeMillis() + " ms.");
        
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
        
        //compare packetId with lastPacketId from DB 
        
        Integer amountRules = ruleRepository.findByCardDeviceEvent(cardNumber, deviceNumber, eventId);
        
        if(amountRules.intValue()>0) {
            AccessPacket accessPacket = new AccessPacket();                    
            accessPacket.setDeviceNumber(deviceNumber);
            accessPacket.setPacketNumber(packetNumber);
            accessPacket.setTime(new Date());
            accessPacket.setEventId(eventId);                    
            retPacket = accessPacket;
            //'2018-09-06 18:25:28'
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            df.setTimeZone(TIMEZONE_UTC);
            String dateStr = df.format(new Date());
            accessMessageRepository.insertMessage(cardNumber, deviceNumber, eventId, "Allow access", new Date());
        } else {
            AccessMessageWrong accessMessWrong = 
                    new AccessMessageWrong(cardNumber, deviceNumber, eventId, dateTime, "wrong deviceNumber");
            accessMessageWrongRepository.save(accessMessWrong);
            retPacket = new InfoPacket("Received deviceNumber is wrong");
        }
        return retPacket;
       
    }
    
}
