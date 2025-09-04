package com.app.namasteapi.model;

public class Icd11Terms {
    private String id;
    private String title;
    private String definition;

    public Icd11Terms(String id, String title, String definition) {
        this.id = id;
        this.title = title;
        this.definition = definition;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDefinition() { return definition; }
}
