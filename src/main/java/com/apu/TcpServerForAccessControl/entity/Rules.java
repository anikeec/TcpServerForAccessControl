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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author apu
 */
@Entity
@Table(name = "rules")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rules.findAll", query = "SELECT r FROM Rules r")
    , @NamedQuery(name = "Rules.findByRuleID", query = "SELECT r FROM Rules r WHERE r.ruleID = :ruleID")
    , @NamedQuery(name = "Rules.findByDateBegin", query = "SELECT r FROM Rules r WHERE r.dateBegin = :dateBegin")
    , @NamedQuery(name = "Rules.findByDateEnd", query = "SELECT r FROM Rules r WHERE r.dateEnd = :dateEnd")})
public class Rules implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ruleID")
    private Integer ruleID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DateBegin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateBegin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DateEnd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnd;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ruleID", fetch = FetchType.EAGER)
    private Collection<EventMessages> eventMessagesCollection;
    @JoinColumn(name = "cardID", referencedColumnName = "cardID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Cards cardID;
    @JoinColumn(name = "devideID", referencedColumnName = "deviceID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Devices devideID;
    @JoinColumn(name = "ruleTypeID", referencedColumnName = "ruleTypeId")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private RulesTypes ruleTypeID;

    public Rules() {
    }

    public Rules(Integer ruleID) {
        this.ruleID = ruleID;
    }

    public Rules(Integer ruleID, Date dateBegin, Date dateEnd) {
        this.ruleID = ruleID;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }

    public Integer getRuleID() {
        return ruleID;
    }

    public void setRuleID(Integer ruleID) {
        this.ruleID = ruleID;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
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

    public Devices getDevideID() {
        return devideID;
    }

    public void setDevideID(Devices devideID) {
        this.devideID = devideID;
    }

    public RulesTypes getRuleTypeID() {
        return ruleTypeID;
    }

    public void setRuleTypeID(RulesTypes ruleTypeID) {
        this.ruleTypeID = ruleTypeID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ruleID != null ? ruleID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rules)) {
            return false;
        }
        Rules other = (Rules) object;
        if ((this.ruleID == null && other.ruleID != null) || (this.ruleID != null && !this.ruleID.equals(other.ruleID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.apu.TcpServerForAccessControl.entity.Rules[ ruleID=" + ruleID + " ]";
    }
    
}
