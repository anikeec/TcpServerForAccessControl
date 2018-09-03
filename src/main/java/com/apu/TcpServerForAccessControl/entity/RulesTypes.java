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
@Table(name = "rules_types")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RulesTypes.findAll", query = "SELECT r FROM RulesTypes r")
    , @NamedQuery(name = "RulesTypes.findByRuleTypeId", query = "SELECT r FROM RulesTypes r WHERE r.ruleTypeId = :ruleTypeId")
    , @NamedQuery(name = "RulesTypes.findByDescription", query = "SELECT r FROM RulesTypes r WHERE r.description = :description")})
public class RulesTypes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ruleTypeId")
    private Integer ruleTypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ruleTypeID", fetch = FetchType.EAGER)
    private Collection<Rules> rulesCollection;

    public RulesTypes() {
    }

    public RulesTypes(Integer ruleTypeId) {
        this.ruleTypeId = ruleTypeId;
    }

    public RulesTypes(Integer ruleTypeId, String description) {
        this.ruleTypeId = ruleTypeId;
        this.description = description;
    }

    public Integer getRuleTypeId() {
        return ruleTypeId;
    }

    public void setRuleTypeId(Integer ruleTypeId) {
        this.ruleTypeId = ruleTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        hash += (ruleTypeId != null ? ruleTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RulesTypes)) {
            return false;
        }
        RulesTypes other = (RulesTypes) object;
        if ((this.ruleTypeId == null && other.ruleTypeId != null) || (this.ruleTypeId != null && !this.ruleTypeId.equals(other.ruleTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.apu.TcpServerForAccessControl.entity.RulesTypes[ ruleTypeId=" + ruleTypeId + " ]";
    }
    
}
