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
@Table(name = "rule")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rule.findAll", query = "SELECT r FROM Rule r")
    , @NamedQuery(name = "Rule.findByRuleID", query = "SELECT r FROM Rule r WHERE r.ruleID = :ruleID")
    , @NamedQuery(name = "Rule.findByDateBegin", query = "SELECT r FROM Rule r WHERE r.dateBegin = :dateBegin")
    , @NamedQuery(name = "Rule.findByDateEnd", query = "SELECT r FROM Rule r WHERE r.dateEnd = :dateEnd")})
public class Rule implements Serializable {

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
    @JoinColumn(name = "cardID", referencedColumnName = "cardID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Card cardID;
    @JoinColumn(name = "devideID", referencedColumnName = "deviceID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Device devideID;
    @JoinColumn(name = "ruleTypeID", referencedColumnName = "ruleTypeId")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private RulesType ruleTypeID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ruleID", fetch = FetchType.EAGER)
    private Collection<EventMessage> eventMessageCollection;

    public Rule() {
    }

    public Rule(Integer ruleID) {
        this.ruleID = ruleID;
    }

    public Rule(Integer ruleID, Date dateBegin, Date dateEnd) {
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

    public Card getCardID() {
        return cardID;
    }

    public void setCardID(Card cardID) {
        this.cardID = cardID;
    }

    public Device getDevideID() {
        return devideID;
    }

    public void setDevideID(Device devideID) {
        this.devideID = devideID;
    }

    public RulesType getRuleTypeID() {
        return ruleTypeID;
    }

    public void setRuleTypeID(RulesType ruleTypeID) {
        this.ruleTypeID = ruleTypeID;
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
        hash += (ruleID != null ? ruleID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rule)) {
            return false;
        }
        Rule other = (Rule) object;
        if ((this.ruleID == null && other.ruleID != null) || (this.ruleID != null && !this.ruleID.equals(other.ruleID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.apu.TcpServerForAccessControl.entity.Rule[ ruleID=" + ruleID + " ]";
    }
    
}
