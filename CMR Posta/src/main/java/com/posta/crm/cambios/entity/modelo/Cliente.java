package com.posta.crm.cambios.entity.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.procesos.demo.enumeraciones.Etnia;
import com.procesos.demo.enumeraciones.Genero;
import com.procesos.demo.enumeraciones.NivelEstudio;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class Cliente {

    private Long id;


    private String nombre;

    private String apellido;

    private Long dni;

    private Genero genero;

    private NivelEstudio nivelEstudio;

    private Etnia etnia;

    private Boolean victima;

    private Boolean discapacidad;

    private Boolean desplazamiento;


    private String telefono;

    private String email;


    private Date fechaNacimiento;

    private Municipio municipio;


    private String direccion;


    private String comentario;
    private Boolean activo;
    private String tipo;

    private Long usuario;

}
