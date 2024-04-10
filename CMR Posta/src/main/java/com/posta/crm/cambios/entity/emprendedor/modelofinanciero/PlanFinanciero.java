package com.procesos.demo.entity.emprendedor.modelofinanciero;

import lombok.Data;

import java.util.List;

@Data
public class PlanFinanciero {

    private PresupuestoVenta presupuestoVenta;

    private List<PresupuestoCompra> presupuestoCompra;

    private Double totalPresupuestoCompra=0.0;


    private GastoCosto gastoCosto;


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
