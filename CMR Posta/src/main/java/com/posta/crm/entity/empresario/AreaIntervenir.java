/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.posta.crm.entity.empresario;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author crowl
 */
@Entity
@Data
public class AreaIntervenir {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Lob
    @Column(length = 2500)
    private String objetivoEstrategico;
    @Lob
    @Column(length = 2500)
    private String actividadEstrategica;
    @Lob
    @Column(length = 2500)
    private String responsable;
    @Lob
    @Column(length = 2500)
    private String aliadosEstrategicos;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Bogota")
    private Date fechaInicio;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Bogota")
    private Date fechaCierre;
    @Lob
    @Column(length = 2500)
    private String cumplimiento;
    @Lob
    @Column(length = 2500)
    private String observaciones;
    
    
   
    
}
