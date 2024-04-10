package com.posta.crm.cambios.entity;

import com.procesos.demo.entity.emprendedor.AutoEvaluacion;
import com.procesos.demo.entity.emprendedor.canvas.Canvas;
import com.procesos.demo.entity.emprendedor.modelobasico.PlanNegocio;
import com.procesos.demo.entity.emprendedor.modelofinanciero.PlanFinanciero;
import lombok.Data;



@Data
public class ProcesoEmprendedor {
    private String nombre;
    private AutoEvaluacion autoEvaluacion;
    private PlanNegocio planNegocio;
    private Canvas canvas;
    private PlanFinanciero planFinanciero;

}
