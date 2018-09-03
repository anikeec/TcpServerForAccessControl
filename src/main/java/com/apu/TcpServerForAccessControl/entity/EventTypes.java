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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author apu
 */
@Entity
@Table(name = "event_types")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventTypes.findAll", query = "SELECT e FROM EventTypes e")
    , @NamedQuery(name = "EventTypes.findByEventID", query = "SELECT e FROM EventTypes e WHERE e.eventID = :eventID")
    , @NamedQuery(name = "EventTypes.findByDescription", query = "SELECT e FROM EventTypes e WHERE e.description = :description")})
public class EventTypes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "eventID")
    private Integer eventID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventID", fetch = FetchType.EAGER)
    private Collection<EventMessages> eventMessagesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventID", fetch = FetchType.EAGER)
    private Collection<AccessMessages> accessMessagesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventID", fetch = FetchType.EAGER)
    private Collection<InfoMessages> infoMessagesCollection;

    public EventTypes() {
    }

    public EventTypes(Integer eventID) {
        this.eventID = eventID;
    }

    public EventTypes(Integer eventID, String description) {
        this.eventID = eventID;
        this.description = description;
    }

    public Integer getEventID() {
        return eventID;
    }

    public void setEventID(Integer eventID) {
        this.eventID = eventID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventID != null ? eventID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventTypes)) {
            return false;
        }
        EventTypes other = (EventTypes) object;
        if ((this.eventID == null && other.eventID != null) || (this.eventID != null && !this.eventID.equals(other.eventID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.apu.TcpServerForAccessControl.entity.EventTypes[ eventID=" + eventID + " ]";
    }
    
}
