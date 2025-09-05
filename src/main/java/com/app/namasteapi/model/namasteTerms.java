package com.app.namasteapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "namastedb")
public class namasteTerms {

    @Id
    @Column(name = "\"namc_code\"")
    private String namcCode;

    @Column(name = "\"namc_term\"")
    private String namcTerm;

    @Column(name = "\"ethnic_term\"")
    private String ethnicTerm;

    @Column(name = "\"short_definition\"")
    private String shortDefinition;

    @Column(name = "\"long_definition\"")
    private String longDefinition;

    @Column(name = "\"type\"")
    private String type;

    public String getNamcCode() {
        return namcCode;
    }

    public void setNamcCode(String namcCode) {
        this.namcCode = namcCode;
    }

    public String getNamcTerm() {
        return namcTerm;
    }

    public void setNamcTerm(String namcTerm) {
        this.namcTerm = namcTerm;
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
