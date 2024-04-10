/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.posta.crm.entity.financiero;


import com.posta.crm.entity.financiero.partes.EstructuraCompras;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.Data;

/**
 *
 * @author crowl
 */
@Entity
@Data
public class PresupuestoCompra {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombreProcucto;
    private String tipoProducto;
    private Double cantidadProducto;
    
    @OneToMany
    private List<EstructuraCompras>estructuraCompras;
    @OneToMany
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
