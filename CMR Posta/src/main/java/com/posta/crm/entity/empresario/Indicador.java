/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.posta.crm.entity.empresario;

import com.posta.crm.enums.DiagEmpr;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Indicador {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(length = 2500)
    private String mes1;
    @Lob
    @Column(length = 2500)
    private String mes2;
    @Lob
    @Column(length = 2500)
    private String mes3;
    @Lob
    @Column(length = 2500)
    private String mes4;
    @Lob
    @Column(length = 2500)
    private String mes5;
    @Lob
    @Column(length = 2500)
    private String observaciones;
    
    
}
