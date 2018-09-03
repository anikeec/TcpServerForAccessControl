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
@Table(name = "info_messages")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InfoMessages.findAll", query = "SELECT i FROM InfoMessages i")
    , @NamedQuery(name = "InfoMessages.findByInfoMessID", query = "SELECT i FROM InfoMessages i WHERE i.infoMessID = :infoMessID")
    , @NamedQuery(name = "InfoMessages.findByDescription", query = "SELECT i FROM InfoMessages i WHERE i.description = :description")
    , @NamedQuery(name = "InfoMessages.findByDate", query = "SELECT i FROM InfoMessages i WHERE i.date = :date")})
public class InfoMessages implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "infoMessID")
    private Integer infoMessID;
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
    @JoinColumn(name = "deviceID", referencedColumnName = "deviceID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Devices deviceID;
    @JoinColumn(name = "eventID", referencedColumnName = "eventID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private EventTypes eventID;

    public InfoMessages() {
    }

    public InfoMessages(Integer infoMessID) {
        this.infoMessID = infoMessID;
    }

    public InfoMessages(Integer infoMessID, String description, Date date) {
        this.infoMessID = infoMessID;
        this.description = description;
        this.date = date;
    }

    public Integer getInfoMessID() {
        return infoMessID;
    }

    public void setInfoMessID(Integer infoMessID) {
        this.infoMessID = infoMessID;
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
        hash += (infoMessID != null ? infoMessID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InfoMessages)) {
            return false;
        }
        InfoMessages other = (InfoMessages) object;
        if ((this.infoMessID == null && other.infoMessID != null) || (this.infoMessID != null && !this.infoMessID.equals(other.infoMessID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.apu.TcpServerForAccessControl.entity.InfoMessages[ infoMessID=" + infoMessID + " ]";
    }
    
}
