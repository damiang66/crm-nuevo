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

import java.time.LocalDate;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author crowl
 */
@Entity
@Data
public class Calendario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "America/Bogota")
    private Date fecha;
    
 
    
    private String titulo;
    @Lob
    @Column(length = 2500)
    private String contenido;
    
    
    @ManyToOne
    private User usuario;
    @ManyToOne
    private Client cliente;
    
}
