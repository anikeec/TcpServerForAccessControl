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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "cards")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cards.findAll", query = "SELECT c FROM Cards c")
    , @NamedQuery(name = "Cards.findByCardID", query = "SELECT c FROM Cards c WHERE c.cardID = :cardID")
    , @NamedQuery(name = "Cards.findByCardNumber", query = "SELECT c FROM Cards c WHERE c.cardNumber = :cardNumber")})
public class Cards implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cardID")
    private Integer cardID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "cardNumber")
    private String cardNumber;
    @JoinColumn(name = "userID", referencedColumnName = "userId")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Users userID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cardID", fetch = FetchType.EAGER)
    private Collection<AccessMessages> accessMessagesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cardID", fetch = FetchType.EAGER)
    private Collection<Rules> rulesCollection;

    public Cards() {
    }

    public Cards(Integer cardID) {
        this.cardID = cardID;
    }

    public Cards(Integer cardID, String cardNumber) {
        this.cardID = cardID;
        this.cardNumber = cardNumber;
    }

    public Integer getCardID() {
        return cardID;
    }

    public void setCardID(Integer cardID) {
        this.cardID = cardID;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Users getUserID() {
        return userID;
    }

    public void setUserID(Users userID) {
        this.userID = userID;
    }

    @XmlTransient
    public Collection<AccessMessages> getAccessMessagesCollection() {
        return accessMessagesCollection;
    }

    public void setAccessMessagesCollection(Collection<AccessMessages> accessMessagesCollection) {
        this.accessMessagesCollection = accessMessagesCollection;
    }

    @XmlTransient
    public Collection<Rules> getRulesCollection() {
        return rulesCollection;
    }

    public void setRulesCollection(Collection<Rules> rulesCollection) {
        this.rulesCollection = rulesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cardID != null ? cardID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cards)) {
            return false;
        }
        Cards other = (Cards) object;
        if ((this.cardID == null && other.cardID != null) || (this.cardID != null && !this.cardID.equals(other.cardID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.apu.TcpServerForAccessControl.entity.Cards[ cardID=" + cardID + " ]";
    }
    
}
