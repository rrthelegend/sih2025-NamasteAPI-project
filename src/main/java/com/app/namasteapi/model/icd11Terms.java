package com.app.namasteapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "icd11db")
public class icd11Terms {

    @Id
    @Column(name = "code")
    private String icdCode;

    @Column(name = "title")
    private String icdTitle;

    public icd11Terms() {}

    public icd11Terms(String icdCode, String icdTitle) {
        this.icdCode = icdCode;
        this.icdTitle = icdTitle;
    }

    public String getIcdCode() { return icdCode; }
    public void setIcdCode(String icdCode) { this.icdCode = icdCode; }

    public String getIcdTitle() { return icdTitle; }
    public void setIcdTitle(String icdTitle) { this.icdTitle = icdTitle; }
}
