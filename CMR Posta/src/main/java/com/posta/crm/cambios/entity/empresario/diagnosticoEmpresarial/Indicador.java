package com.procesos.demo.entity.empresario.diagnosticoEmpresarial;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "indicador")
@Data
public class Indicador {


    private String mes1;

    private String mes2;

    private String mes3;

    private String mes4;

    private String mes5;

    private String observaciones;

}
