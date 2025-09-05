package com.app.namasteapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "NamasteDB")
public class NamasteTerms {

    @Id
    @Column(name = "NAMC_CODE")
    private String namcCode;

    @Column(name = "NAMC_NAME")
    private String namcName;

    @Column(name = "Ethnic_term")
    private String ethnicTerm;

    @Column(name = "Short_definition")
    private String shortDefinition;

    @Column(name = "Long_definition")
    private String longDefinition;

    @Column(name = "TYPE")
    private String type;

    // Getters & Setters
    public String getNamcCode() {
        return namcCode;
    }

    public void setNamcCode(String namcCode) {
        this.namcCode = namcCode;
    }

    public String getNamcName() {
        return namcName;
    }

    public void setNamcName(String namcName) {
        this.namcName = namcName;
    }

    public String getEthnicTerm() {
        return ethnicTerm;
    }

    public void setEthnicTerm(String ethnicTerm) {
        this.ethnicTerm = ethnicTerm;
    }

    public String getShortDefinition() {
        return shortDefinition;
    }

    public void setShortDefinition(String shortDefinition) {
        this.shortDefinition = shortDefinition;
    }

    public String getLongDefinition() {
        return longDefinition;
    }

    public void setLongDefinition(String longDefinition) {
        this.longDefinition = longDefinition;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
