package com.minerva.business.settings.model;

public class OptionsBean {
    private String value;
    private String name;

    public OptionsBean(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value == null ? "" : value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
