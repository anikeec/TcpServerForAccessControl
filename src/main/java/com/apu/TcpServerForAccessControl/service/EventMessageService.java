package com.apu.TcpServerForAccessControl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apu.TcpServerForAccessControlDB.entity.EventMessage;
import com.apu.TcpServerForAccessControlDB.repository.EventMessageRepository;

@Service
public class EventMessageService {

    @Autowired
    private EventMessageRepository emRepository;
    
    public List<EventMessage> findAll(){
        return emRepository.findAll();
    }
    
    public <S extends EventMessage> S save(S entity) {
        return emRepository.save(entity);
    }
    
}
