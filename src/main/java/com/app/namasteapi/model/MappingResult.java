package com.app.namasteapi.model;

public class MappingResult {

    private String namasteCode;
    private String namasteName;
    private String ethnicTerm;
    private String shortDefinition;
    private String longDefinition;
    private String type;

    private String icd11Id;
    private String icd11Title;
    private String icd11Definition;

    public MappingResult() {}

    public MappingResult(NamasteTerms namaste, String icd11Id, String icd11Title, String icd11Definition) {
        this.namasteCode = namaste.getNamcCode();
        this.namasteName = namaste.getNamcName();
        this.ethnicTerm = namaste.getEthnicTerm();
        this.shortDefinition = namaste.getShortDefinition();
        this.longDefinition = namaste.getLongDefinition();
        this.type = namaste.getType();
        this.icd11Id = icd11Id;
        this.icd11Title = icd11Title;
        this.icd11Definition = icd11Definition;
    }

    public String getNamasteCode() { return namasteCode; }
    public void setNamasteCode(String namasteCode) { this.namasteCode = namasteCode; }

    public String getNamasteName() { return namasteName; }
    public void setNamasteName(String namasteName) { this.namasteName = namasteName; }

    public String getEthnicTerm() { return ethnicTerm; }
    public void setEthnicTerm(String ethnicTerm) { this.ethnicTerm = ethnicTerm; }

    public String getShortDefinition() { return shortDefinition; }
    public void setShortDefinition(String shortDefinition) { this.shortDefinition = shortDefinition; }

    public String getLongDefinition() { return longDefinition; }
    public void setLongDefinition(String longDefinition) { this.longDefinition = longDefinition; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getIcd11Id() { return icd11Id; }
    public void setIcd11Id(String icd11Id) { this.icd11Id = icd11Id; }

    public String getIcd11Title() { return icd11Title; }
    public void setIcd11Title(String icd11Title) { this.icd11Title = icd11Title; }

    public String getIcd11Definition() { return icd11Definition; }
    public void setIcd11Definition(String icd11Definition) { this.icd11Definition = icd11Definition; }
}
