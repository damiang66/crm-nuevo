package com.procesos.demo.entity.emprendedor.modelofinanciero;

import lombok.Data;

@Data
public class GastoCosto {
    private RequerimientoPersonal operativo;
    private RequerimientoPersonal administrativo;
    private RequerimientoPersonal comercialVenta;

}
