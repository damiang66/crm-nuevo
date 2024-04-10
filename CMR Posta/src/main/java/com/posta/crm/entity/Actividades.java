
package com.posta.crm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Actividades {
    
    @Id
    @Column(name = "actividad_id")
    private String id;
    @Column(name = "actividad_titulo", length = 500)
    private String titulo;
    
}
