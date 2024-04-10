/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.posta.crm.entity.businessplan;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import lombok.Data;

/**
 *
 * @author crowl
 */
@Entity
@Data
public class BusinessPlan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
   @OneToOne
   private ProyectInformation proyectInformation;

    @OneToOne
    private InternalExternalAnalysis analisis;

    @OneToOne
    private DofaAnalisis dofaAnalisis;
    
    @Lob
    @Column(length = 2500)
    private String conclusion;

}
