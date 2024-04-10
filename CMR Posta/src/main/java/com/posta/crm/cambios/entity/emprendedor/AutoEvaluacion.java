package com.procesos.demo.entity.emprendedor;

import com.procesos.demo.enumeraciones.Evaluacion;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;


@Data
public class AutoEvaluacion {



    @Field("evaluacion")
    private List<Evaluacion> evaluacion;

    private Integer puntuacion;
}
