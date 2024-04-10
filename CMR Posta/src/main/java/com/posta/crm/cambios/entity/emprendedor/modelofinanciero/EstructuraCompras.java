package com.procesos.demo.entity.emprendedor.modelofinanciero;

import lombok.Data;

@Data
public class EstructuraCompras {
    private String materiaPrima;
    private String financieroTipo;
    private Double valorUnitario;
    private Double cantidadUnidad;
    private Double totalUnitario;
    public void calculoTotal(){
        this.totalUnitario=0.0;
        this.totalUnitario=(this.valorUnitario*this.cantidadUnidad);
    }
}
