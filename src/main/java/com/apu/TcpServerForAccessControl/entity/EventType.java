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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author apu
 */
@Entity
@Table(name = "event_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventType.findAll", query = "SELECT e FROM EventType e")
    , @NamedQuery(name = "EventType.findByEventId", query = "SELECT e FROM EventType e WHERE e.eventId = :eventId")
    , @NamedQuery(name = "EventType.findByDescription", query = "SELECT e FROM EventType e WHERE e.description = :description")})
public class EventType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "event_id")
    private Integer eventId;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "eventId", fetch = FetchType.LAZY)
    private Collection<AccessMessage> accessMessageCollection;
    @OneToMany(mappedBy = "eventId", fetch = FetchType.LAZY)
    private Collection<EventMessage> eventMessageCollection;
    @OneToMany(mappedBy = "eventId", fetch = FetchType.LAZY)
    private Collection<InfoMessage> infoMessageCollection;

    public EventType() {
    }

    public EventType(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<AccessMessage> getAccessMessageCollection() {
        return accessMessageCollection;
    }

    public void setAccessMessageCollection(Collection<AccessMessage> accessMessageCollection) {
        this.accessMessageCollection = accessMessageCollection;
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
        hash += (eventId != null ? eventId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventType)) {
            return false;
        }
        EventType other = (EventType) object;
        if ((this.eventId == null && other.eventId != null) || (this.eventId != null && !this.eventId.equals(other.eventId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.apu.TcpServerForAccessControl.entity.EventType[ eventId=" + eventId + " ]";
    }
    
}
