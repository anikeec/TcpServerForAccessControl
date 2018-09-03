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
@Table(name = "devices")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Devices.findAll", query = "SELECT d FROM Devices d")
    , @NamedQuery(name = "Devices.findByDeviceID", query = "SELECT d FROM Devices d WHERE d.deviceID = :deviceID")
    , @NamedQuery(name = "Devices.findByLastPacketID", query = "SELECT d FROM Devices d WHERE d.lastPacketID = :lastPacketID")})
public class Devices implements Serializable {

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
    private Collection<EventMessages> eventMessagesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deviceID", fetch = FetchType.EAGER)
    private Collection<AccessMessages> accessMessagesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deviceID", fetch = FetchType.EAGER)
    private Collection<InfoMessages> infoMessagesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "devideID", fetch = FetchType.EAGER)
    private Collection<Rules> rulesCollection;

    public Devices() {
    }

    public Devices(Integer deviceID) {
        this.deviceID = deviceID;
    }

    public Devices(Integer deviceID, int lastPacketID) {
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
    public Collection<EventMessages> getEventMessagesCollection() {
        return eventMessagesCollection;
    }

    public void setEventMessagesCollection(Collection<EventMessages> eventMessagesCollection) {
        this.eventMessagesCollection = eventMessagesCollection;
    }

    @XmlTransient
    public Collection<AccessMessages> getAccessMessagesCollection() {
        return accessMessagesCollection;
    }

    public void setAccessMessagesCollection(Collection<AccessMessages> accessMessagesCollection) {
        this.accessMessagesCollection = accessMessagesCollection;
    }

    @XmlTransient
    public Collection<InfoMessages> getInfoMessagesCollection() {
        return infoMessagesCollection;
    }

    public void setInfoMessagesCollection(Collection<InfoMessages> infoMessagesCollection) {
        this.infoMessagesCollection = infoMessagesCollection;
    }

    @XmlTransient
    public Collection<Rules> getRulesCollection() {
        return rulesCollection;
    }

    public void setRulesCollection(Collection<Rules> rulesCollection) {
        this.rulesCollection = rulesCollection;
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
        if (!(object instanceof Devices)) {
            return false;
        }
        Devices other = (Devices) object;
        if ((this.deviceID == null && other.deviceID != null) || (this.deviceID != null && !this.deviceID.equals(other.deviceID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.apu.TcpServerForAccessControl.entity.Devices[ deviceID=" + deviceID + " ]";
    }
    
}
