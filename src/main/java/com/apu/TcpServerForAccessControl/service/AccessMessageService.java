package com.apu.TcpServerForAccessControl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.apu.TcpServerForAccessControlDB.entity.AccessMessage;
import com.apu.TcpServerForAccessControlDB.repository.AccessMessageRepository;

@Service
public class AccessMessageService {

    @Autowired
    private AccessMessageRepository amRepository;
    
    public Page<AccessMessage> findAll(Pageable pageable) {
        return amRepository.findAll(pageable);
    }
    
    public List<AccessMessage> findAllByPage(Integer page){
        return amRepository.findAllByPage(page);
    }
    
    public <S extends AccessMessage> S save(S entity) {
        return amRepository.save(entity);
    }
    
}
