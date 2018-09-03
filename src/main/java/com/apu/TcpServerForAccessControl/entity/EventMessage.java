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
import javax.validation.constraints.NotNull;
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
    , @NamedQuery(name = "EventMessage.findByEventMessID", query = "SELECT e FROM EventMessage e WHERE e.eventMessID = :eventMessID")
    , @NamedQuery(name = "EventMessage.findByDescription", query = "SELECT e FROM EventMessage e WHERE e.description = :description")
    , @NamedQuery(name = "EventMessage.findByDate", query = "SELECT e FROM EventMessage e WHERE e.date = :date")})
public class EventMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "eventMessID")
    private Integer eventMessID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @JoinColumn(name = "baseAccessMessID", referencedColumnName = "accessMessID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private AccessMessage baseAccessMessID;
    @JoinColumn(name = "deviceID", referencedColumnName = "deviceID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Device deviceID;
    @JoinColumn(name = "eventID", referencedColumnName = "eventID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private EventType eventID;
    @JoinColumn(name = "ruleID", referencedColumnName = "ruleID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Rule ruleID;

    public EventMessage() {
    }

    public EventMessage(Integer eventMessID) {
        this.eventMessID = eventMessID;
    }

    public EventMessage(Integer eventMessID, String description, Date date) {
        this.eventMessID = eventMessID;
        this.description = description;
        this.date = date;
    }

    public Integer getEventMessID() {
        return eventMessID;
    }

    public void setEventMessID(Integer eventMessID) {
        this.eventMessID = eventMessID;
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

    public AccessMessage getBaseAccessMessID() {
        return baseAccessMessID;
    }

    public void setBaseAccessMessID(AccessMessage baseAccessMessID) {
        this.baseAccessMessID = baseAccessMessID;
    }

    public Device getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Device deviceID) {
        this.deviceID = deviceID;
    }

    public EventType getEventID() {
        return eventID;
    }

    public void setEventID(EventType eventID) {
        this.eventID = eventID;
    }

    public Rule getRuleID() {
        return ruleID;
    }

    public void setRuleID(Rule ruleID) {
        this.ruleID = ruleID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventMessID != null ? eventMessID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventMessage)) {
            return false;
        }
        EventMessage other = (EventMessage) object;
        if ((this.eventMessID == null && other.eventMessID != null) || (this.eventMessID != null && !this.eventMessID.equals(other.eventMessID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.apu.TcpServerForAccessControl.entity.EventMessage[ eventMessID=" + eventMessID + " ]";
    }
    
}
