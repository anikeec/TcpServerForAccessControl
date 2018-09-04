/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apu.TcpServerForAccessControl.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author apu
 */
@Entity
@Table(name = "device")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Device.findAll", query = "SELECT d FROM Device d")
    , @NamedQuery(name = "Device.findByDeviceId", query = "SELECT d FROM Device d WHERE d.deviceId = :deviceId")
    , @NamedQuery(name = "Device.findByDeviceNumber", query = "SELECT d FROM Device d WHERE d.deviceNumber = :deviceNumber")
    , @NamedQuery(name = "Device.findByLastPacketId", query = "SELECT d FROM Device d WHERE d.lastPacketId = :lastPacketId")})
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "device_id")
    private Integer deviceId;
    @Column(name = "device_number")
    private Integer deviceNumber;
    @Column(name = "last_packet_id")
    private Integer lastPacketId;
    @OneToMany(mappedBy = "deviceId", fetch = FetchType.LAZY)
    private Collection<AccessMessage> accessMessageCollection;
    @OneToMany(mappedBy = "devideId", fetch = FetchType.LAZY)
    private Collection<Rule> ruleCollection;
    @OneToMany(mappedBy = "deviceId", fetch = FetchType.LAZY)
    private Collection<EventMessage> eventMessageCollection;
    @OneToMany(mappedBy = "deviceId", fetch = FetchType.LAZY)
    private Collection<InfoMessage> infoMessageCollection;

    public Device() {
    }

    public Device(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }
    
    public Integer getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(Integer deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public Integer getLastPacketId() {
        return lastPacketId;
    }

    public void setLastPacketId(Integer lastPacketId) {
        this.lastPacketId = lastPacketId;
    }

    @XmlTransient
    public Collection<AccessMessage> getAccessMessageCollection() {
        return accessMessageCollection;
    }

    public void setAccessMessageCollection(Collection<AccessMessage> accessMessageCollection) {
        this.accessMessageCollection = accessMessageCollection;
    }

    @XmlTransient
    public Collection<Rule> getRuleCollection() {
        return ruleCollection;
    }

    public void setRuleCollection(Collection<Rule> ruleCollection) {
        this.ruleCollection = ruleCollection;
    }

    @XmlTransient
    public Collection<EventMessage> getEventMessageCollection() {
        return eventMessageCollection;
    }

    public void setEventMessageCollection(Collection<EventMessage> eventMessageCollection) {
        this.eventMessageCollection = eventMessageCollection;
    }

    @XmlTransient
    public Collection<InfoMessage> getInfoMessageCollection() {
        return infoMessageCollection;
    }

    public void setInfoMessageCollection(Collection<InfoMessage> infoMessageCollection) {
        this.infoMessageCollection = infoMessageCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deviceId != null ? deviceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Device)) {
            return false;
        }
        Device other = (Device) object;
        if ((this.deviceId == null && other.deviceId != null) || (this.deviceId != null && !this.deviceId.equals(other.deviceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.apu.TcpServerForAccessControl.entity.Device[ deviceId=" + deviceId + " ]";
    }
    
}
