/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.posta.crm.entity.empresario;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 *
 * @author crowl
 */
@Entity
@Data
public class Diagnostico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
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
        this.totales = new ArrayList();

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

//        for (Float totale : totales) {
//            this.total = 0.0f;
//            this.total += totale;
//        }

    }

}
