/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apu.TcpServerForAccessControl.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.validation.constraints.NotNull;
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
    , @NamedQuery(name = "Device.findByDeviceID", query = "SELECT d FROM Device d WHERE d.deviceID = :deviceID")
    , @NamedQuery(name = "Device.findByLastPacketID", query = "SELECT d FROM Device d WHERE d.lastPacketID = :lastPacketID")})
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "deviceID")
    private Integer deviceID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lastPacketID")
    private int lastPacketID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deviceID", fetch = FetchType.EAGER)
    private Collection<AccessMessage> accessMessageCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "devideID", fetch = FetchType.EAGER)
    private Collection<Rule> ruleCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deviceID", fetch = FetchType.EAGER)
    private Collection<EventMessage> eventMessageCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deviceID", fetch = FetchType.EAGER)
    private Collection<InfoMessage> infoMessageCollection;

    public Device() {
    }

    public Device(Integer deviceID) {
        this.deviceID = deviceID;
    }

    public Device(Integer deviceID, int lastPacketID) {
        this.deviceID = deviceID;
        this.lastPacketID = lastPacketID;
    }

    public Integer getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Integer deviceID) {
        this.deviceID = deviceID;
    }

    public int getLastPacketID() {
        return lastPacketID;
    }

    public void setLastPacketID(int lastPacketID) {
        this.lastPacketID = lastPacketID;
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
        hash += (deviceID != null ? deviceID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Device)) {
            return false;
        }
        Device other = (Device) object;
        if ((this.deviceID == null && other.deviceID != null) || (this.deviceID != null && !this.deviceID.equals(other.deviceID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.apu.TcpServerForAccessControl.entity.Device[ deviceID=" + deviceID + " ]";
    }
    
}
