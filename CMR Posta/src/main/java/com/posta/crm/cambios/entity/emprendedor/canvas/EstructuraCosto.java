package com.procesos.demo.entity.emprendedor.canvas;

import lombok.Data;



import java.util.List;


@Data
public class EstructuraCosto {

    private List<ComponenteCosto> costoFIjo;
    private List<ComponenteCosto> costoVariable;
    private Double totalCostoFijo;
    private Double totalCostoVariable;
}
