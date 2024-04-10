package com.procesos.demo.entity.empresario.diagnosticoEmpresarial;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "diagnostico")
@Data
public class Diagnostico {



    private List<ConceptosGenerales> conceptosGenerales;

    private List<Integer> gestionEstrategica;

    private List<Integer> gestionProductividad;

    private List<Integer> gestionOperacional;

    private List<Integer> gestionCalidad;

    private List<Integer> gestionInnovacion;

    private List<Integer> gestionFinanciera;

    private List<Integer> gestionLogistica;

    private List<Integer> gestionDigital;

    private List<Integer> gestionAmbiental;

    private List<Integer> gestionIntelectual;

    private List<Float> totales;

    private Float total = 0.0f;

    public void calcularTotales() {

        Float totalAux = 0.0f;
        this.totales = new ArrayList<>();

        for (Integer integer : this.gestionEstrategica) {
            totalAux += integer;
        }
        totalAux = totalAux /18;
        this.totales.add(totalAux);
        totalAux = 0.0f;
        for (Integer integer : this.gestionProductividad) {
            totalAux += integer;
        }
        totalAux = totalAux / 13;
        this.totales.add(totalAux);
        totalAux = 0.0f;
        for (Integer integer : this.gestionOperacional) {
            totalAux += integer;
        }
        totalAux = totalAux / 11;
        this.totales.add(totalAux);
        totalAux = 0.0f;
        for (Integer integer : this.gestionCalidad) {
            totalAux += integer;
        }
        totalAux = totalAux / 10;
        this.totales.add(totalAux);
        totalAux = 0.0f;
        for (Integer integer : this.gestionInnovacion) {
            totalAux += integer;
        }
        totalAux = totalAux / 4;
        this.totales.add(totalAux);
        totalAux = 0.0f;
        for (Integer integer : this.gestionFinanciera) {
            totalAux += integer;
        }
        totalAux = totalAux / 16;
        this.totales.add(totalAux);
        totalAux = 0.0f;
        for (Integer integer : this.gestionLogistica) {
            totalAux += integer;
        }
        totalAux = totalAux / 6;
        this.totales.add(totalAux);
        totalAux = 0.0f;
        for (Integer integer : this.gestionDigital) {
            totalAux += integer;
        }
        totalAux = totalAux / 6;
        this.totales.add(totalAux);
        totalAux = 0.0f;
        for (Integer integer : this.gestionAmbiental) {
            totalAux += integer;
        }
        totalAux = totalAux / 9;
        this.totales.add(totalAux);
        totalAux = 0.0f;
        for (Integer integer : this.gestionIntelectual) {
            totalAux += integer;
        }
        totalAux = totalAux / 6;
        this.totales.add(totalAux);
        totalAux = 0.0f;


    }
}
