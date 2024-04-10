package com.procesos.demo.entity.emprendedor.canvas;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;


@Data
public class FlujoDeIngreso {

    private String capitalPropio;
    private String capitalPrestamo;
    private String canalesPago;
    private String otros;


}
