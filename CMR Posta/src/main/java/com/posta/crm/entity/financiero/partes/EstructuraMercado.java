/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.posta.crm.entity.financiero.partes;

import com.posta.crm.enums.Financiero;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class EstructuraMercado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String producto;
    
    private Double cantidad;
    
    @Enumerated(value = EnumType.STRING)
    private Financiero tipo;

    private Double horasOperario;

    private Double unidadHoraHombre;

    private Double capacidadInstaladaPorOperario;

    private Double tiempoDecicacion;

    private Double capacidadInstaladaUnidades;
    
    //porque es la misma que total de productos. 
    //private Double capacidadRequeridaUnidades;
    
    private Double precioUnitario;
    
    private Double precioTotal;

    
    public void calculos(){
        this.unidadHoraHombre=(0.99*this.cantidad)/this.horasOperario;
        this.capacidadInstaladaPorOperario=this.horasOperario*this.unidadHoraHombre;
        this.capacidadInstaladaUnidades=this.capacidadInstaladaPorOperario*this.tiempoDecicacion;
        this.precioTotal=this.precioUnitario*this.cantidad;
    }
}
