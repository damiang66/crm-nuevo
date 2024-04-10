package com.procesos.demo.entity.emprendedor.modelofinanciero;

import lombok.Data;

import java.util.List;

@Data
public class PresupuestoCompra {
    private String nombreProcucto;
    private String tipoProducto;
    private Double cantidadProducto;


    private List<EstructuraCompras> estructuraCompras;

    private List<EstructuraCompras>otrosInsumos;

    private Double subtotal=0.0;
    private Double subtotal2=0.0;
    private Double total=0.0;
    private Double totalAnual=0.0;

    public void sacarTotales(){
        subtotal=0.0;
        subtotal2=0.0;

        total=0.0;
        totalAnual=0.0;
        for (EstructuraCompras estructuraCompra : estructuraCompras) {
            this.subtotal+=estructuraCompra.getTotalUnitario();
        }


        for (EstructuraCompras estructuraCompra : otrosInsumos) {
            this.subtotal2+=estructuraCompra.getTotalUnitario();
        }
        this.total=this.subtotal+this.subtotal2;
        this.totalAnual=(this.total*this.cantidadProducto);
    }
}
