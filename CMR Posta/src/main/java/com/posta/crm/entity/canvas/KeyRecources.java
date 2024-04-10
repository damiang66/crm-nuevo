
package com.posta.crm.entity.canvas;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class KeyRecources {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
   

    @Column(length = 3000)
    private String recursosHumanos;
    
    @Column(length = 3000)
    private String recursosFisicos;
    
    @Column(length = 3000)
    private String recursosIntelectuales;
    
    @Column(length = 3000)
    private String recursosTecnologicos;
    
    @Column(length = 3000)
    private String otros;
    
}
