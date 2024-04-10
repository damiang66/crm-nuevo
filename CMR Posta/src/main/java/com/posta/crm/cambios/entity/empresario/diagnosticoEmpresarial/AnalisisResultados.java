package com.procesos.demo.entity.empresario.diagnosticoEmpresarial;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = "analisis_resultados")
@Data
public class AnalisisResultados {


    private String gestionEstrategica;

    private String gestionProductividad;

    private String gestionOperacional;

    private String gestionCalidad;

    private String gestionInnovacion;

    private String gestionFinanciera;

    private String gestionLogistica;

    private String gestionDigital;

    private String gestionAmbiental;

    private String gestionIntelectual;
}
