/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.posta.crm.entity.financiero;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.Data;

/**
 *
 * @author crowl
 */
@Entity
@Data
public class BusinessPlanFinancial {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    private PresupuestoVenta presupuestoVenta;
    @OneToMany
    private List<PresupuestoCompra> presupuestoCompra;
    
    private Double totalPresupuestoCompra=0.0;
    
    @OneToOne
    private GastoCosto gastoCosto;
    
    @OneToOne
    private PlanInversion planInversion;
    
    private Double IPC1;
    private Double IPC2;
    private Double IPC3;
    private Double IPC4;
    
    public void cuentas(){
        for (PresupuestoCompra presupuestoCompra1 : presupuestoCompra) {
            this.totalPresupuestoCompra+=presupuestoCompra1.getTotalAnual();
        }
    }
}
