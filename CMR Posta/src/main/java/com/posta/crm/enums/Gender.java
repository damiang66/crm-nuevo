
package com.posta.crm.enums;


public enum Gender {
    MALE("Masculino"),
    FEMALE("Femenino"),
    LGBTQ("LGBTQ+");

    private String label;

    Gender(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
