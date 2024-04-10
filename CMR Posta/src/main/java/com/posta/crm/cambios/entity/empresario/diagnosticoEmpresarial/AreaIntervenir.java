package com.procesos.demo.entity.empresario.diagnosticoEmpresarial;

import lombok.Data;

import java.util.Date;

@Data
public class AreaIntervenir {


    private String objetivoEstrategico;

    private String actividadEstrategica;

    private String responsable;

    private String aliadosEstrategicos;

    private Date fechaInicio;

    private Date fechaCierre;

    private String cumplimiento;

    private String observaciones;
}
