/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.posta.crm.entity.businessplan;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

/**
 *
 * @author crowl
 */
@Entity
@Data
public class ProyectInformation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(length = 2500)
    private String resumen;

    @Lob
    @Column(length = 2500)
    private String problemas;

    @Lob
    @Column(length = 2500)
    private String objetivos;

    @Lob
    @Column(length = 2500)
    private String mision;

    @Lob
    @Column(length = 2500)
    private String vision;

    @Lob
    @Column(length = 2500)
    private String valoresCorporativos;

    @Lob
    @Column(length = 2500)
    private String impactoAmbiental;

    @Lob
    @Column(length = 2500)
    private String impactoSocial;
    
}
