package com.posta.crm.cambios.entity;

import com.posta.crm.entity.Client;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;


@Document(collection = "procesos")
@Data
public class Proceso {
    @Field(targetType = FieldType.OBJECT_ID)

    private String id;
    private Client cliente;
    private Long idCliente;

    private String estadoDelProceso;


    private Date fechaCreacion;


    private Date fechaModificacion;

    private ProcesoEmprendedor procesoEmprendedor;

    private ProcesoEmpresario procesoEmpresario;
    private String documentoCompromiso;
    private String encuestaSatisfaccion;
    private String actaCierre;
    private String impacto;
}
