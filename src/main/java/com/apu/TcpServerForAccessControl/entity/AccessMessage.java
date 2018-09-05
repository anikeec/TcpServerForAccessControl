/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apu.TcpServerForAccessControl.entity;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author apu
 */
@Entity
@Table(name = "access_message")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccessMessage.findAll", query = "SELECT a FROM AccessMessage a")
    , @NamedQuery(name = "AccessMessage.findByAccessMessId", query = "SELECT a FROM AccessMessage a WHERE a.accessMessId = :accessMessId")
    , @NamedQuery(name = "AccessMessage.findByDescription", query = "SELECT a FROM AccessMessage a WHERE a.description = :description")
    , @NamedQuery(name = "AccessMessage.findByDate", query = "SELECT a FROM AccessMessage a WHERE a.date = :date")})
public class AccessMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "access_mess_id")
    private Integer accessMessId;
//    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @JoinColumn(name = "card_id", referencedColumnName = "card_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Card cardId;
    @JoinColumn(name = "device_id", referencedColumnName = "device_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Device deviceId;
    @JoinColumn(name = "event_id", referencedColumnName = "event_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private EventType eventId;
    @OneToMany(mappedBy = "baseAccessMessId", fetch = FetchType.LAZY)
    private Collection<EventMessage> eventMessageCollection;

    public AccessMessage() {
    }

    public AccessMessage(Device deviceId, Card cardId, EventType eventId, Date date, String description) {
        super();
        this.description = description;
        this.date = date;
        this.cardId = cardId;
        this.deviceId = deviceId;
        this.eventId = eventId;
    }

    public AccessMessage(Integer accessMessId) {
        this.accessMessId = accessMessId;
    }

    public Integer getAccessMessId() {
        return accessMessId;
    }

    public void setAccessMessId(Integer accessMessId) {
        this.accessMessId = accessMessId;
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

    public Card getCardId() {
        return cardId;
    }

    public void setCardId(Card cardId) {
        this.cardId = cardId;
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

    @XmlTransient
    public Collection<EventMessage> getEventMessageCollection() {
        return eventMessageCollection;
    }

    public void setEventMessageCollection(Collection<EventMessage> eventMessageCollection) {
        this.eventMessageCollection = eventMessageCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accessMessId != null ? accessMessId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccessMessage)) {
            return false;
        }
        AccessMessage other = (AccessMessage) object;
        if ((this.accessMessId == null && other.accessMessId != null) || (this.accessMessId != null && !this.accessMessId.equals(other.accessMessId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.apu.TcpServerForAccessControl.entity.AccessMessage[ accessMessId=" + accessMessId + " ]";
    }
    
}
