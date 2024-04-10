/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.posta.crm.entity.financiero.partes;

import jakarta.persistence.*;

import java.util.List;
import lombok.Data;

/**
 *
 * @author crowl
 */
@Entity
@Data
public class  RequerimientosPersonal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //Personal
    @ManyToMany
    private List<Personal> personal;

    private Double subTotal = 0.0;
    private Double subTotalAnual = 0.0;

    private Double parafiscales;
    private Double totalParafiscales;

    private Double seguridadSocial;
    private Double totalSeguridadSocial;

    private Double cesantias;
    private Double totalCesantias;

    private Double interesesCesantias;
    private Double totalInteresesCesantias;

    private Double primaServicios;
    private Double totalPrimaServicios;

    private Double vacaciones;
    private Double totalVacaciones;

    private Double totalMensual;
    private Double totalAnual;
    
    //Costos
    @ManyToMany
    private List<OtrosCostos> costos;
    
    private Double totalCostoMensual=0.0;
    private Double totalCostoAnual=0.0;
    
    //Calculos Personal
    public void parafiscalesCalculos() {
        for (Personal personal1 : this.personal) {
            this.subTotal += personal1.getSalarioMensual();
            this.subTotalAnual += personal1.getSalariaAnual();
        }

        //Parafiscales Mensuales
        this.parafiscales = (this.subTotal * 0.04);
        this.seguridadSocial = (this.subTotal * 0.205);
        this.cesantias = (this.subTotal * 0.0833);
        this.interesesCesantias = (this.cesantias * 0.0012);
        this.primaServicios = (this.subTotal * 0.0833);
        this.vacaciones = (this.subTotal * 0.0417);
        // Parafiscales Anuales
        this.totalParafiscales = this.parafiscales * 12;
        this.totalSeguridadSocial = this.seguridadSocial * 12;
        this.totalCesantias = this.cesantias * 12;
        this.totalInteresesCesantias = this.interesesCesantias * 12;
        this.totalPrimaServicios = this.primaServicios * 12;
        this.totalVacaciones = this.vacaciones * 12;
        //Totales sumas
        this.totalMensual = (this.subTotal + this.parafiscales + this.seguridadSocial + this.cesantias + this.interesesCesantias + this.primaServicios + this.vacaciones);
        this.totalAnual = (this.subTotalAnual + this.totalParafiscales + this.totalSeguridadSocial + this.totalCesantias + this.totalInteresesCesantias + this.totalPrimaServicios + this.totalVacaciones);

    }
    public void totalCostos(){
        for (OtrosCostos costo : costos) {
            this.totalCostoMensual+=costo.getGastoMensual();
            this.totalCostoAnual+=costo.getGastoAnual();
        }
    }

}
