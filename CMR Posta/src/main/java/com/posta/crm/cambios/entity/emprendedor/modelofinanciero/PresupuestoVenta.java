package com.procesos.demo.entity.emprendedor.modelofinanciero;

import lombok.Data;

import java.util.List;

@Data
public class PresupuestoVenta {
    private List<EstructuraMercado>estructuraMercado;

    private Double totalProductos=0.0;

    private Double totalCapacidadOperario=0.0;

    private Double totalCapacidadInstalada=0.0;

    private Double totalPrecioUnitario=0.0;

    private Double totalTotal=0.0;


    private List<CiclicidadVentas> ciclicidadVentas;
    //agrego para push de mi puta rama
    private Double totalCalificacion = 0.0;

    private Double totalUnidadesAno=0.0;



    public void calcular(){
        this.totalProductos=0.0;
        this.totalCapacidadOperario=0.0;
        this.totalCapacidadInstalada=0.0;
        this.totalPrecioUnitario=0.0;
        this.totalTotal=0.0;

        for (EstructuraMercado estructuraMercado1 : this.estructuraMercado) {
            this.totalProductos+=estructuraMercado1.getCantidad();
            this.totalCapacidadOperario+=estructuraMercado1.getCapacidadInstaladaPorOperario();
            this.totalCapacidadInstalada+=estructuraMercado1.getCapacidadInstaladaUnidades();
            this.totalPrecioUnitario+=estructuraMercado1.getPrecioUnitario();
            this.totalTotal+=estructuraMercado1.getPrecioTotal();
        }
    }

    public void calculosCiclicidad(){
        this.totalCalificacion = 0.0;
        this.totalUnidadesAno=0.0;
        for (CiclicidadVentas ciclicidadVenta : ciclicidadVentas) {
            this.totalCalificacion+=ciclicidadVenta.getCalificacion();
        }

        for (CiclicidadVentas ciclicidadVenta : ciclicidadVentas) {
            ciclicidadVenta.setUnidadesAnio((ciclicidadVenta.getCalificacion()/this.totalCalificacion)*this.totalProductos);
            ciclicidadVenta.setVentasAnio((this.totalTotal*ciclicidadVenta.getUnidadesAnio())/this.totalProductos);
            this.totalUnidadesAno+=ciclicidadVenta.getUnidadesAnio();
        }

    }

}
