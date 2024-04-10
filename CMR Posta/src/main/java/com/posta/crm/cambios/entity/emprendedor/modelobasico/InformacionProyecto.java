package com.procesos.demo.entity.emprendedor.modelobasico;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;


@Data
public class InformacionProyecto {


    private String resumen;
    private String problemas;
    private String objetivos;
    private String mision;
    private String vision;
    private String valoresCorporativos;
    private String impactoAmbiental;
    private String impactoSocial;
}
