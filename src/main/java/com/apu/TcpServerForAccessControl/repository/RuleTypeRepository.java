/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apu.TcpServerForAccessControl.repository;

import com.apu.TcpServerForAccessControl.entity.RuleType;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author apu
 */
public interface RuleTypeRepository extends CrudRepository<RuleType, Integer>{
    
}
