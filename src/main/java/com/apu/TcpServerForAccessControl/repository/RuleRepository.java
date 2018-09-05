/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apu.TcpServerForAccessControl.repository;

import com.apu.TcpServerForAccessControl.entity.Card;
import com.apu.TcpServerForAccessControl.entity.Device;
import com.apu.TcpServerForAccessControl.entity.Rule;

import java.util.List;

import javax.persistence.NamedQuery;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author apu
 */
public interface RuleRepository extends CrudRepository<Rule, Integer>{
    
    List<Rule> findByDeviceIdAndCardId(@Param("deviceId") Device deviceId, @Param("cardId") Card cardId);
    
}
