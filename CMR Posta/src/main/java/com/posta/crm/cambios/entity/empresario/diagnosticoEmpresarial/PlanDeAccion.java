package com.procesos.demo.entity.empresario.diagnosticoEmpresarial;

import lombok.Data;

@Data
public class PlanDeAccion {


    private AreaIntervenir lineamientosBasicos;

    private AreaIntervenir mercadeoVentas;

    private AreaIntervenir produccionOperaciones;

    private AreaIntervenir talentoHumano;
}
