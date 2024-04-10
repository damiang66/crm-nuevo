/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.posta.crm.entity.empresario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author crowl
 */
@Entity
@Data
@Getter
@Setter
public class AnalisisResultados {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Lob
    @Column(length = 2500)
    private String gestionEstrategica;
    @Lob
    @Column(length = 2500)
    private String gestionProductividad;
    @Lob
    @Column(length = 2500)
    private String gestionOperacional;
    @Lob
    @Column(length = 2500)
    private String gestionCalidad;
    @Lob
    @Column(length = 2500)
    private String gestionInnovacion;
    @Lob
    @Column(length = 2500)
    private String gestionFinanciera;
    @Lob
    @Column(length = 2500)
    private String gestionLogistica;
    @Lob
    @Column(length = 2500)
    private String gestionDigital;
    @Lob
    @Column(length = 2500)
    private String gestionAmbiental;
    @Lob
    @Column(length = 2500)
    private String gestionIntelectual;
    
}
