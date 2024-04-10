package com.procesos.demo.entity.emprendedor.modelofinanciero;

import lombok.Data;

import java.util.List;

@Data
public class PlanInversion {
    private List<Inversion>activoFijo;
    private Double activoCredito=0.0;
    private List<Inversion> maquinariaEquipo;
    private Double maquinariaPropio=0.0;
    private Double maquinariaInversion=0.0;
    private Double maquinariaCredito=0.0;
    private List<Inversion> mueblesEnseres;
    private Double mueblesPropio=0.0;
    private Double mueblesInversion=0.0;
    private Double muebleCredito=0.0;
    private List<Inversion> vehiculos;
    private Double vehiculosPropio=0.0;
    private Double vehiculosInversion=0.0;
    private Double vehiculosCredito=0.0;
    private Double totalPropio=0.0;
    private Double totalInversion=0.0;
    private Double totalCredito=0.0;
    public void fijo(){
        for (Inversion inversion : activoFijo) {
            this.activoCredito+=inversion.getCreditoRequerido();
        }
    }
    public void maquinaria (){
        for (Inversion maquinaria : maquinariaEquipo) {
            this.maquinariaPropio+=maquinaria.getAportesPropios();
            this.maquinariaInversion+=maquinaria.getInversionRequerida();
            this.maquinariaCredito+=maquinaria.getCreditoRequerido();
        }
    }
    public void muebles(){
        for (Inversion mueblesEnsere : mueblesEnseres) {
            this.mueblesPropio+=mueblesEnsere.getAportesPropios();
            this.mueblesInversion+=mueblesEnsere.getInversionRequerida();
            this.muebleCredito+=mueblesEnsere.getCreditoRequerido();
        }
    }
    public void vehiculos(){
        for (Inversion vehiculo : vehiculos) {
            this.vehiculosPropio+=vehiculo.getAportesPropios();
            this.vehiculosInversion+=vehiculo.getInversionRequerida();
            this.vehiculosCredito+=vehiculo.getCreditoRequerido();
        }
    }
    public void calculoTotal(){
        this.totalPropio=(this.maquinariaPropio+this.mueblesPropio+this.vehiculosPropio);
        this.totalInversion=(this.maquinariaInversion+this.mueblesInversion+this.vehiculosInversion);
        this.totalCredito=(this.activoCredito+this.totalPropio);
    }


}
