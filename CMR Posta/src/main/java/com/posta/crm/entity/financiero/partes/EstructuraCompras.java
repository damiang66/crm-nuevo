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

/**
 *
 * @author crowl
 */
@Entity
@Data
public class EstructuraCompras {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String materiaPrima;
    @Enumerated(value = EnumType.STRING)
    private Financiero tipo;
    private Double valorUnitario;
    private Double cantidadUbnidad;
    private Double totalUnitario;
    
   public void calculoTotal(){
       this.totalUnitario=0.0;
       this.totalUnitario=(this.valorUnitario*this.cantidadUbnidad);
   }
    
    
}
