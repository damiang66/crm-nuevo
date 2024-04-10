/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.posta.crm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.posta.crm.entity.empresario.AnalisisEconomico;
import com.posta.crm.entity.empresario.AnalisisResultados;
import com.posta.crm.entity.empresario.DiagnosticoEmpresarial;
import com.posta.crm.entity.empresario.PlanDeAccion;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
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
public class ProcessEmpresario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private User user;
    @OneToOne
    private Client client;
    private String estado;
    private boolean terminado;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Bogota")
    private Date fechaAlta;
    
    @OneToOne
    private DiagnosticoEmpresarial diagnosticoEmpresarial;
    @OneToOne
    private PlanDeAccion planDeAccion;
    private Boolean informeDiagnostico;
    

    @PrePersist
    public void prePersist(){
        this.terminado=false;
        this.fechaAlta= new Date();
        
    }
}
