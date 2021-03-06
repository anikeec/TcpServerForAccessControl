package com.apu.TcpServerForAccessControl.engine;

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
import com.apu.TcpServerForAccessControl.service.AccessMessageService;
import com.apu.TcpServerForAccessControl.service.AccessMessageWrongService;
import com.apu.TcpServerForAccessControl.service.CardService;
import com.apu.TcpServerForAccessControl.service.DeviceService;
import com.apu.TcpServerForAccessControl.service.EventMessageService;
import com.apu.TcpServerForAccessControl.service.EventTypeService;
import com.apu.TcpServerForAccessControl.service.RuleService;
import com.apu.TcpServerForAccessControl.service.RuleTypeService;
import com.apu.TcpServerForAccessControl.service.UserService;
import com.apu.TcpServerForAccessControlAPI.packet.AccessPacket;
import com.apu.TcpServerForAccessControlAPI.packet.InfoPacket;

@Component
public class AccessRulesEngine {
    
    private static final Logger logger = LogManager.getLogger(AccessRulesEngine.class);
    
    @Autowired
    private AccessMessageService accessMessageService;
    
    @Autowired
    private AccessMessageWrongService accessMessageWrongService;
    
    @Autowired
    private CardService cardService;
    
    @Autowired
    private DeviceService deviceService;
    
    @Autowired
    private EventMessageService eventMessageService;
    
    @Autowired
    private EventTypeService eventTypeService;
    
    @Autowired
    private RuleService ruleService;
    
    @Autowired
    private RuleTypeService ruleTypeService;
    
    @Autowired
    private UserService userService;
  
    
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
        
        long timeReceiveMesage = System.nanoTime();
        
        //check all parameters are not null
        //if some of the parameters is wrong return error message
        if((packetNumber == null) ||
           (deviceNumber == null) ||
           (cardNumber == null) ||
           (dateTime == null)) {
            retPacket = new InfoPacket(deviceNumber, packetNumber, "Received packet is wrong");
        }       
        
//        //compare packetId with lastPacketId from DB 
//        
//        Integer amountRules = ruleRepository.findByCardDeviceEvent(cardNumber, deviceNumber, eventId);
//        
//        if(amountRules.intValue()>0) {
//            AccessPacket accessPacket = new AccessPacket();                    
//            accessPacket.setDeviceNumber(deviceNumber);
//            accessPacket.setPacketNumber(packetNumber);
//            accessPacket.setTime(new Date());
//            accessPacket.setEventId(eventId);                    
//            retPacket = accessPacket;
//            //'2018-09-06 18:25:28'
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////            df.setTimeZone(TIMEZONE_UTC);
//            String dateStr = df.format(new Date());
////            accessMessageRepository.insertMessage(deviceNumber, eventId, "Allow access", new Date());
//        } else {
//            AccessMessageWrong accessMessWrong = 
//                    new AccessMessageWrong(cardNumber, deviceNumber, eventId, dateTime, "wrong deviceNumber");
//            accessMessageWrongRepository.save(accessMessWrong);
//            retPacket = new InfoPacket("Received deviceNumber is wrong");
//        }
//        return retPacket;
       
      //check if packet wrong and write it to access_message_wrong table
        if(retPacket != null) {
            AccessMessageWrong accessMessWrong = 
                    new AccessMessageWrong(cardNumber, deviceNumber, eventId, dateTime, "wrong packet");
            accessMessageWrongService.save(accessMessWrong);
        }
        
        long timeBeforeGetDeviceFromDB = System.nanoTime();
        
        //get deviceId from DB
        Device device = null;
        Integer lastPacketIdStored = null;
        if(retPacket == null) {
            List<Device> deviceList = deviceService.findByDeviceNumber(deviceNumber);
            if(deviceList.size() != 0) {
                device = deviceList.get(0);//maybe I have to check to have only one deviceId with this deviceNumber    
                lastPacketIdStored = device.getLastPacketId();
            } else {
                AccessMessageWrong accessMessWrong = 
                        new AccessMessageWrong(cardNumber, deviceNumber, eventId, dateTime, "wrong deviceNumber");
                accessMessageWrongService.save(accessMessWrong);
                retPacket = new InfoPacket(deviceNumber, packetNumber, "Received deviceNumber is wrong");
            }
        }
        
        //compare packetId with lastPacketId from DB 
        if(retPacket == null) {
            if(lastPacketIdStored == null) {
                lastPacketIdStored = 0;
//                device.setLastPacketId(lastPacketIdStored);
//                deviceRepository.save(device);
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
        
        long timeBeforeGetCardIdFromDB = System.nanoTime();
        
        //get cardId from DB
        Card card = null;
        if(retPacket == null) {
            List<Card> cardList = cardService.findByCardNumber(cardNumber);
            if(cardList.size() != 0) {
                card = cardList.get(0);//maybe I have to check to have only one cardId with this cardNumber            
            } else {
                AccessMessageWrong accessMessWrong = 
                        new AccessMessageWrong(cardNumber, deviceNumber, eventId, dateTime, "wrong cardNumber");
                accessMessageWrongService.save(accessMessWrong);
                retPacket = new InfoPacket(deviceNumber, packetNumber, "Received cardNumber is wrong");
            }      
        }
        
        long timeBeforeGetEventTypeFromDB = System.nanoTime();
        
        //get eventType
        EventType eventType = null;
        if(retPacket == null) {
            List<EventType> eventTypeList = eventTypeService.findByEventId(eventId);
            if(eventTypeList.size() == 0) {
                AccessMessageWrong accessMessWrong = 
                        new AccessMessageWrong(cardNumber, deviceNumber, eventId, dateTime, "wrong eventId for this cardNumber");
                accessMessageWrongService.save(accessMessWrong);
                retPacket = new InfoPacket(deviceNumber, packetNumber, "Access denied for this cardNumber on this device. Wrong eventId.");
            } else {
                eventType = eventTypeList.get(0);   //maybe its wrong
            }
        }
        
        long timeBeforeGetRuleFromDB = System.nanoTime();
        
        //find in DB rules for combination deviceId+cardId - maybe it will be good to add eventId
        List<Rule> ruleList = null;
        if(retPacket == null) {
            ruleList = ruleService.findByDeviceIdAndCardId(device, card);
            if(ruleList.size() == 0) {
                AccessMessageWrong accessMessWrong = 
                        new AccessMessageWrong(cardNumber, deviceNumber, eventId, dateTime, "wrong rule for this cardNumber");
                accessMessageWrongService.save(accessMessWrong);
                retPacket = new InfoPacket(deviceNumber, packetNumber, "Access denied for this cardNumber on this device");
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
                accessMessageWrongService.save(accessMessWrong);
                retPacket = new InfoPacket(deviceNumber, packetNumber, "Access denied for this cardNumber on this device");
            }
        }       

        long timeBeforeCheckTimeBorders = System.nanoTime();
        
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
                        List<EventType> eventTypeListTemp = eventTypeService.findByDescription(
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
                        accessMessageService.save(accessMess);
                        
                        //write to event_message
                        EventMessage eventMessage = 
                                new EventMessage(device, eventTypeListTemp.get(0), accessMess, rule, new Date(), "Access allow");                  
                        eventMessageService.save(eventMessage);
                    }
                } else {
                    //access allow
                    List<EventType> eventTypeListTemp = eventTypeService.findByDescription(
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
                    accessMessageService.save(accessMess);
                    
                    //write to event_message
                    EventMessage eventMessage = 
                            new EventMessage(device, eventTypeListTemp.get(0), accessMess, rule, new Date(), "Access allow");                  
                    eventMessageService.save(eventMessage);
                }
            }
            
            
            
            if((retPacket != null) && (retPacket instanceof AccessPacket)) {
//                AccessMessage accessMess = 
//                        new AccessMessage(device, card, eventType, dateTime, "wrong rule for this cardNumber");
//                accessMessageRepository.save(accessMess);
            } else {
                AccessMessageWrong accessMessWrong = 
                        new AccessMessageWrong(cardNumber, deviceNumber, eventId, dateTime, "wrong rule for this cardNumber");
                accessMessageWrongService.save(accessMessWrong);
                retPacket = new InfoPacket(deviceNumber, packetNumber, "Access denied for this cardNumber on this device");
            }
        }
        
        
//        logger.info("Time: " + System.currentTimeMillis() + " ms.");
        String msg;        
        msg = "AccessRulesEngine answer - PacketType: " + message.getMessageType();
//        logger.info(msg);
        
        long timeReturnMessageReady = System.nanoTime();
//        logger.info("Time inside accessRulesEngine: " + (timeReturnMessageReady - timeReceiveMesage)/1000 + " us" +
//                    "\r\n" + "Times: " 
//                    + timeReceiveMesage/1000 + ";" 
//                    + timeBeforeGetDeviceFromDB/1000 + ";" 
//                    + timeBeforeGetCardIdFromDB/1000 + ";"
//                    + timeBeforeGetEventTypeFromDB/1000 + ";"
//                    + timeBeforeGetRuleFromDB/1000 + ";"
//                    + timeBeforeCheckTimeBorders/1000 + ";"
//                    + timeReturnMessageReady/1000 + ";");
        
        return retPacket;

        
    }
    
}
