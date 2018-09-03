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
import javax.persistence.CascadeType;
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
import javax.validation.constraints.NotNull;
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
    , @NamedQuery(name = "AccessMessage.findByAccessMessID", query = "SELECT a FROM AccessMessage a WHERE a.accessMessID = :accessMessID")
    , @NamedQuery(name = "AccessMessage.findByDescription", query = "SELECT a FROM AccessMessage a WHERE a.description = :description")
    , @NamedQuery(name = "AccessMessage.findByDate", query = "SELECT a FROM AccessMessage a WHERE a.date = :date")})
public class AccessMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "accessMessID")
    private Integer accessMessID;
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
    @JoinColumn(name = "cardID", referencedColumnName = "cardID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Card cardID;
    @JoinColumn(name = "deviceID", referencedColumnName = "deviceID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Device deviceID;
    @JoinColumn(name = "eventID", referencedColumnName = "eventID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private EventType eventID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "baseAccessMessID", fetch = FetchType.EAGER)
    private Collection<EventMessage> eventMessageCollection;

    public AccessMessage() {
    }

    public AccessMessage(Integer accessMessID) {
        this.accessMessID = accessMessID;
    }

    public AccessMessage(Integer accessMessID, String description, Date date) {
        this.accessMessID = accessMessID;
        this.description = description;
        this.date = date;
    }

    public Integer getAccessMessID() {
        return accessMessID;
    }

    public void setAccessMessID(Integer accessMessID) {
        this.accessMessID = accessMessID;
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

    public Card getCardID() {
        return cardID;
    }

    public void setCardID(Card cardID) {
        this.cardID = cardID;
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
        hash += (accessMessID != null ? accessMessID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccessMessage)) {
            return false;
        }
        AccessMessage other = (AccessMessage) object;
        if ((this.accessMessID == null && other.accessMessID != null) || (this.accessMessID != null && !this.accessMessID.equals(other.accessMessID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.apu.TcpServerForAccessControl.entity.AccessMessage[ accessMessID=" + accessMessID + " ]";
    }
    
}
