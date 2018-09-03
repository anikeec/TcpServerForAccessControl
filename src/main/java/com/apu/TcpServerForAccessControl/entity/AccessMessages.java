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
@Table(name = "access_messages")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccessMessages.findAll", query = "SELECT a FROM AccessMessages a")
    , @NamedQuery(name = "AccessMessages.findByAccessMessID", query = "SELECT a FROM AccessMessages a WHERE a.accessMessID = :accessMessID")
    , @NamedQuery(name = "AccessMessages.findByDescription", query = "SELECT a FROM AccessMessages a WHERE a.description = :description")
    , @NamedQuery(name = "AccessMessages.findByDate", query = "SELECT a FROM AccessMessages a WHERE a.date = :date")})
public class AccessMessages implements Serializable {

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "baseAccessMessID", fetch = FetchType.EAGER)
    private Collection<EventMessages> eventMessagesCollection;
    @JoinColumn(name = "cardID", referencedColumnName = "cardID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Cards cardID;
    @JoinColumn(name = "deviceID", referencedColumnName = "deviceID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Devices deviceID;
    @JoinColumn(name = "eventID", referencedColumnName = "eventID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private EventTypes eventID;

    public AccessMessages() {
    }

    public AccessMessages(Integer accessMessID) {
        this.accessMessID = accessMessID;
    }

    public AccessMessages(Integer accessMessID, String description, Date date) {
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

    @XmlTransient
    public Collection<EventMessages> getEventMessagesCollection() {
        return eventMessagesCollection;
    }

    public void setEventMessagesCollection(Collection<EventMessages> eventMessagesCollection) {
        this.eventMessagesCollection = eventMessagesCollection;
    }

    public Cards getCardID() {
        return cardID;
    }

    public void setCardID(Cards cardID) {
        this.cardID = cardID;
    }

    public Devices getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Devices deviceID) {
        this.deviceID = deviceID;
    }

    public EventTypes getEventID() {
        return eventID;
    }

    public void setEventID(EventTypes eventID) {
        this.eventID = eventID;
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
        if (!(object instanceof AccessMessages)) {
            return false;
        }
        AccessMessages other = (AccessMessages) object;
        if ((this.accessMessID == null && other.accessMessID != null) || (this.accessMessID != null && !this.accessMessID.equals(other.accessMessID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.apu.TcpServerForAccessControl.entity.AccessMessages[ accessMessID=" + accessMessID + " ]";
    }
    
}
