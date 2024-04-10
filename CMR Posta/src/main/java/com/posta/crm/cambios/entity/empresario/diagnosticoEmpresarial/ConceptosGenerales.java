package com.procesos.demo.entity.empresario.diagnosticoEmpresarial;

import com.procesos.demo.enumeraciones.DiagnosticoEnum;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "conceptos_generales")
@Data
public class ConceptosGenerales {



    private DiagnosticoEnum diagnosticoEnum;

    private String observaciones;
}
