/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.posta.crm.entity.financiero;



import com.posta.crm.entity.financiero.partes.CiclicidadVentas;
import com.posta.crm.entity.financiero.partes.EstructuraMercado;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

import lombok.Data;

/**
 *
 * @author crowl
 */
@Entity
@Data
public class PresupuestoVenta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany
    private List<EstructuraMercado>estructuraMercado;
    
    private Double totalProductos=0.0;
    
    private Double totalCapacidadOperario=0.0;
    
    private Double totalCapacidadInstalada=0.0;
    
    private Double totalPrecioUnitario=0.0;
    
    private Double totalTotal=0.0;
    
    @OneToMany
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
