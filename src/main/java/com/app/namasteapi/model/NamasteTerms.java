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
    private String NAMC_CODE;

    @Column(name = "NAMC_NAME")
    private String NAMC_NAME;

    @Column(name = "Ethnic_term")
    private String Ethnic_term;

    @Column(name = "Short_definitions")
    private String Short_definitions;

    @Column(name = "Long_definitions")
    private String Long_definitions;

    @Column(name = "TYPE")
    private String TYPE;

    public String getNAMC_CODE() {
        return NAMC_CODE;
    }

    public void setNAMC_CODE(String NAMC_CODE) {
        this.NAMC_CODE = NAMC_CODE;
    }

    public String getNAMC_NAME() {
        return NAMC_NAME;
    }

    public void setNAMC_NAME(String NAMC_NAME) {
        this.NAMC_NAME = NAMC_NAME;
    }

    public String getEthnic_term() {
        return Ethnic_term;
    }

    public void setEthnic_term(String ethnic_term) {
        Ethnic_term = ethnic_term;
    }

    public String getShort_definitions() {
        return Short_definitions;
    }

    public void setShort_definitions(String short_definitions) {
        Short_definitions = short_definitions;
    }

    public String getLong_definitions() {
        return Long_definitions;
    }

    public void setLong_definitions(String long_definitions) {
        Long_definitions = long_definitions;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }
}
