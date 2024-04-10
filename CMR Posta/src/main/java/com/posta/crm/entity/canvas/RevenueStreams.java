
package com.posta.crm.entity.canvas;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class RevenueStreams {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 3000)
    private String capitalPorpio;
    
    @Column(length = 3000)
    private String capitalPrestamo;
    
    @Column(length = 3000)
    private String canalesPago;
    
    @Column(length = 3000)
    private String otros;
}
