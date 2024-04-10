package com.procesos.demo.enumeraciones;

public enum Genero {

    MALE("Masculino"),
    FEMALE("Femenino"),
    LGBTQ("LGBTQ+");

    private String label;

    Genero(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
