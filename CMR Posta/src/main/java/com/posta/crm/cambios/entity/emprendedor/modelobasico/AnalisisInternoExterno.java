package com.procesos.demo.entity.emprendedor.modelobasico;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;


@Data
public class AnalisisInternoExterno {



    private String publicoObjetivo;
    private String actividadPrincipal;
    private String propuestaValor;
    private String comercializacion;
    private String operacion;
    private String equipoTrabajo;
    private String competencia;

    //private String mediosDigitales;

    private String legal;
    private String fuenteFinanciacion;
}
