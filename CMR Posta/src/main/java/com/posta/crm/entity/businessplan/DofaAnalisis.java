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
public class DofaAnalisis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(length = 2500)
    private String debilidades;
    @Lob
    @Column(length = 2500)
    private String oportunidades;
   @Lob
    @Column(length = 2500)
    private String fotalezas;
    @Lob
    @Column(length = 2500)
    private String amenazas;
    
}
