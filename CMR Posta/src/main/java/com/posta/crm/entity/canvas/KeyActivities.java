
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
public class KeyActivities {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
   

    @Column(length = 3000)
    private String actividadPrincipal;
    
    @Column(length = 3000)
    private String prestacionServicio;
    
    @Column(length = 3000)
    private String comunicacionMarketing;
    
    @Column(length = 3000)
    private String postVenta;
    
    @Column(length = 3000)
    private String otros;
    
}
