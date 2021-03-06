package com.apu.TcpServerForAccessControl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apu.TcpServerForAccessControlDB.entity.Device;
import com.apu.TcpServerForAccessControlDB.repository.DeviceRepository;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;
    
    public List<Device> findAll() {
        return deviceRepository.findAll();
    }
    
    public List<Device> findByActive(Boolean active) {
        return deviceRepository.findByActive(active);
    }
    
    public List<Device> findByDeviceNumber(Integer deviceNumber) {
        return deviceRepository.findByDeviceNumber(deviceNumber);
    }
    
    public List<Device> findByDeviceId(Integer deviceId) {
        return deviceRepository.findByDeviceId(deviceId);
    }    
    
    public <S extends Device> S save(S entity) {
        return deviceRepository.save(entity);
    }

    public void delete(Device entity) {
        deviceRepository.delete(entity);
    }
    
}
