package com.procesos.demo.entity.emprendedor.modelofinanciero;

import lombok.Data;

@Data
public class Personal {
    private String cargo;
    private Double salarioMensual;
    private Double salariaAnual;

    public void anual(){
        this.salariaAnual=(this.salarioMensual*12);
    }
}
