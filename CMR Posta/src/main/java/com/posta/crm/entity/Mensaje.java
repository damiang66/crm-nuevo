/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.posta.crm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import java.util.Date;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

/**
 *
 * @author crowl
 */
@Entity
@Data
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String asunto;
    
    @Lob
    @Column(length = 2500)
    private String contenido;
    
    @ManyToOne
    private User remitente;
    
    @ManyToOne
    private User destinatario;
    
    @CreationTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone = "America/Bogota")
    private Date fehcaEnvio;
    
    private Boolean condicion;
    
    @PrePersist
    protected void onCreate() {
        this.condicion = true; // Inicializar la condición en true al momento de persistir
    }
    
    
}
