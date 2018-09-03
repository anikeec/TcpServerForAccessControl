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
@Table(name = "event_message")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventMessage.findAll", query = "SELECT e FROM EventMessage e")
    , @NamedQuery(name = "EventMessage.findByEventMessId", query = "SELECT e FROM EventMessage e WHERE e.eventMessId = :eventMessId")
    , @NamedQuery(name = "EventMessage.findByDescription", query = "SELECT e FROM EventMessage e WHERE e.description = :description")
    , @NamedQuery(name = "EventMessage.findByDate", query = "SELECT e FROM EventMessage e WHERE e.date = :date")})
public class EventMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "event_mess_id")
    private Integer eventMessId;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @JoinColumn(name = "base_access_mess_id", referencedColumnName = "access_mess_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccessMessage baseAccessMessId;
    @JoinColumn(name = "device_id", referencedColumnName = "device_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Device deviceId;
    @JoinColumn(name = "event_id", referencedColumnName = "event_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private EventType eventId;
    @JoinColumn(name = "rule_id", referencedColumnName = "rule_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Rule ruleId;

    public EventMessage() {
    }

    public EventMessage(Integer eventMessId) {
        this.eventMessId = eventMessId;
    }

    public Integer getEventMessId() {
        return eventMessId;
    }

    public void setEventMessId(Integer eventMessId) {
        this.eventMessId = eventMessId;
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

    public AccessMessage getBaseAccessMessId() {
        return baseAccessMessId;
    }

    public void setBaseAccessMessId(AccessMessage baseAccessMessId) {
        this.baseAccessMessId = baseAccessMessId;
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

    public Rule getRuleId() {
        return ruleId;
    }

    public void setRuleId(Rule ruleId) {
        this.ruleId = ruleId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventMessId != null ? eventMessId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventMessage)) {
            return false;
        }
        EventMessage other = (EventMessage) object;
        if ((this.eventMessId == null && other.eventMessId != null) || (this.eventMessId != null && !this.eventMessId.equals(other.eventMessId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.apu.TcpServerForAccessControl.entity.EventMessage[ eventMessId=" + eventMessId + " ]";
    }
    
}
