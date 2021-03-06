package com.apu.TcpServerForAccessControl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apu.TcpServerForAccessControlDB.entity.SystemUser;
import com.apu.TcpServerForAccessControlDB.repository.SystemUserRepository;

@Service
public class UserService {

    @Autowired
    private SystemUserRepository userRepository;

    public List<SystemUser> findAll() {
        return userRepository.findAll();
    }
    
    public List<SystemUser> findByUserId(Integer userId) {
        return userRepository.findByUserId(userId);
    }
    
    public List<SystemUser> findByActive(Boolean active) {
        return userRepository.findByActive(active);
    }
    
    public List<SystemUser> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public SystemUser findUserByEmail(String email) {
        List<SystemUser> userList = userRepository.findByEmail(email);
        if((userList != null) && (userList.size() > 0))
            return userList.get(0);
        return null;
    }
    
    public <S extends SystemUser> S save(S entity) {
        return userRepository.save(entity);
    }
    
    public void delete(SystemUser entity) {
        userRepository.delete(entity);
    }
    
}
