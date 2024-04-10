package com.procesos.demo.entity.emprendedor.modelofinanciero;

import lombok.Data;

@Data
public class EstructuraMercado {
    private String producto;
    private Double cantidad;
    private String financieroTipo;
    private Double horasOperario;
    private Double unidadHoraHombre;

    private Double capacidadInstaladaPorOperario;

    private Double tiempoDecicacion;

    private Double capacidadInstaladaUnidades;



    private Double precioUnitario;

    private Double precioTotal;


    public void calculos(){
        this.unidadHoraHombre=(0.99*this.cantidad)/this.horasOperario;
        this.capacidadInstaladaPorOperario=this.horasOperario*this.unidadHoraHombre;
        this.capacidadInstaladaUnidades=this.capacidadInstaladaPorOperario*this.tiempoDecicacion;
        this.precioTotal=this.precioUnitario*this.cantidad;
    }
}


