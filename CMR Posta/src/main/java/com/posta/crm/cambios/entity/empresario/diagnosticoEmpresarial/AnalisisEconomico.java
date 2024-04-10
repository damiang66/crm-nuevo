package com.procesos.demo.entity.empresario.diagnosticoEmpresarial;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = "analisis_economico")
@Data
public class AnalisisEconomico {


    private Indicador ventasMes;

    private Indicador aumentoVentas;

    private Indicador empleosFormales;

    private Indicador empleosInformales;

    private Indicador empleosNuevos;

    private Indicador empresaExportando;

    private Indicador ventasExportacion;

    private Indicador diversificacionProductos;

    private Indicador aperturaNuevosMercados;

    private Indicador accesoOtrasFuentes;
}
