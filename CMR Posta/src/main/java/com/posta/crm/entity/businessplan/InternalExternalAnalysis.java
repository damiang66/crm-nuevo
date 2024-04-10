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
public class InternalExternalAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(length = 2500)
    private String publicoObjetivo;

    @Lob
    @Column(length = 2500)
    private String actividadPrincipal;

    @Lob
    @Column(length = 2500)
    private String propuestaValor;

    @Lob
    @Column(length = 2500)
    private String comercializacion;

    @Lob
    @Column(length = 2500)
    private String operacion;

    @Lob
    @Column(length = 2500)
    private String equipoTrabajo;

    @Lob
    @Column(length = 2500)
    private String competencias;

//    @Lob
//    @Column(length = 5000)
//    private String mediosDigitales;

    @Lob
    @Column(length = 2500)
    private String recursosNecesarios;

    @Lob
    @Column(length = 2500)
    private String legal;

    @Lob
    @Column(length = 2500)
    private String fuenteFinanciacion;
}
