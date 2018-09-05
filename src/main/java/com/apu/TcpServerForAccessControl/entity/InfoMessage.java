/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apu.TcpServerForAccessControl.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author apu
 */
@Entity
@Table(name = "info_message")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InfoMessage.findAll", query = "SELECT i FROM InfoMessage i")
    , @NamedQuery(name = "InfoMessage.findByInfoMessId", query = "SELECT i FROM InfoMessage i WHERE i.infoMessId = :infoMessId")
    , @NamedQuery(name = "InfoMessage.findByDescription", query = "SELECT i FROM InfoMessage i WHERE i.description = :description")
    , @NamedQuery(name = "InfoMessage.findByDate", query = "SELECT i FROM InfoMessage i WHERE i.date = :date")})
public class InfoMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "info_mess_id")
    private Integer infoMessId;
//    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @JoinColumn(name = "device_id", referencedColumnName = "device_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Device deviceId;
    @JoinColumn(name = "event_id", referencedColumnName = "event_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private EventType eventId;

    public InfoMessage() {
    }

    public InfoMessage(Integer infoMessId) {
        this.infoMessId = infoMessId;
    }

    public Integer getInfoMessId() {
        return infoMessId;
    }

    public void setInfoMessId(Integer infoMessId) {
        this.infoMessId = infoMessId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Device getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Device deviceId) {
        this.deviceId = deviceId;
    }

    public EventType getEventId() {
        return eventId;
    }

    public void setEventId(EventType eventId) {
        this.eventId = eventId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (infoMessId != null ? infoMessId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InfoMessage)) {
            return false;
        }
        InfoMessage other = (InfoMessage) object;
        if ((this.infoMessId == null && other.infoMessId != null) || (this.infoMessId != null && !this.infoMessId.equals(other.infoMessId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.apu.TcpServerForAccessControl.entity.InfoMessage[ infoMessId=" + infoMessId + " ]";
    }
    
}
