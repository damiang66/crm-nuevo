/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.posta.crm.entity.empresario;

import jakarta.persistence.*;

import java.util.List;
import lombok.Data;

/**
 *
 * @author crowl
 */
@Entity
@Data
public class PlanDeAccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    private AreaIntervenir lineamientosBasicos;
    @OneToOne
    private AreaIntervenir mercadeoVentas;
    @OneToOne
    private AreaIntervenir produccionOperaciones;
    @OneToOne
    private AreaIntervenir talentoHumano;
}
