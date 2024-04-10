package com.procesos.demo.entity.emprendedor.modelofinanciero;

import lombok.Data;
;

@Data
public class Inversion {
    private String tipo;

    private Double aportesPropios=0.0;

    private Double inversionRequerida=0.0;

    private Double creditoRequerido=0.0;

    public void totalCredito(){
        this.creditoRequerido=this.inversionRequerida-this.aportesPropios;
    }


}
