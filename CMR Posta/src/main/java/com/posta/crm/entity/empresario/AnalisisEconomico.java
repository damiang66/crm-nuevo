/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.posta.crm.entity.empresario;

import com.posta.crm.enums.DiagEmpr;
import jakarta.persistence.*;

import java.util.List;
import lombok.Data;

/**
 *
 * @author crowl
 */
@Entity
@Data
public class AnalisisEconomico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @OneToOne
    private Indicador ventasMes;
    @OneToOne
    private Indicador aumentoVentas;
    @OneToOne
    private Indicador empleosFormales;
    @OneToOne
    private Indicador empleosInformales;
    @OneToOne
    private Indicador empleosNuevos;

    @OneToOne
    private Indicador empresaExportando;
    @OneToOne
    private Indicador ventassExportacion;
    @OneToOne
    private Indicador diversificacionProductos;
    @OneToOne
    private Indicador aperturaNuevosMercados;
    @OneToOne
    private Indicador accesoOtrasFuentes;

}
