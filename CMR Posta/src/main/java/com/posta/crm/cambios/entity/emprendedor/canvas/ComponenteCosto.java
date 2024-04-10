package com.procesos.demo.entity.emprendedor.canvas;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "componente_costo")
@Data
public class ComponenteCosto {

    private String nombre;
    private double monto;
}
