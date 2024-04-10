package com.procesos.demo.entity.emprendedor.modelofinanciero;

import lombok.Data;

@Data
public class OtrosCostos {
    private String concepto;
    private Double gastoMensual;
    private Double gastoAnual;

    public void anual() {
        this.gastoAnual = (this.gastoMensual * 12);
    }
}
