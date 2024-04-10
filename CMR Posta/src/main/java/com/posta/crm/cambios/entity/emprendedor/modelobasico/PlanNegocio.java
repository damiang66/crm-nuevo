package com.procesos.demo.entity.emprendedor.modelobasico;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;


@Data
public class PlanNegocio {



    private AnalisisInternoExterno analisis;
    private DofaAnalisis dofaAnalisis;
    private InformacionProyecto informacionProyecto;


}
