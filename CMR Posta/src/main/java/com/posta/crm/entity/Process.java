/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.posta.crm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.posta.crm.entity.businessplan.BusinessPlan;
import com.posta.crm.entity.canvas.CanvasModel;
import com.posta.crm.entity.financiero.BusinessPlanFinancial;
import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.Data;

import java.util.Date;
import org.hibernate.annotations.UpdateTimestamp;

/**
 *
 * @author crowl
 */
@Entity
@Data
public class Process {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    private SelfAssessment selfAssessment;
    @OneToOne
    private CanvasModel canvasModel;
    @OneToOne
    private BusinessPlan businessPlan;
    @OneToOne
    private BusinessPlanFinancial businessPlanFinancial;
    @OneToOne
    private ProcessEmpresario processEmpresario;
    
    @ManyToOne
    private User user;
    @OneToOne
    private Client client;
    private String estado;

    private String estadoAnteriorEmpresario;

    private String estadoAnteriorEmprendedor;
    private boolean terminado;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Bogota")
    private Date fechaAlta;
    @UpdateTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone = "America/Bogota")
    private Timestamp updatedate;
    
    private Date fechaFinalizacion;
    
    private String documentoCompromiso;
    private String encuestaSatisfaccion;
    private String actaCierre;
    private String impacto;
    
    //La gilada para que cuando convierta funque
    private Boolean cambio;

    @PrePersist
    public void prePersist(){
        this.terminado=false;
        this.fechaAlta= new Date();
        //esto esta de mas
//        this.cambio=false;
    }
}
