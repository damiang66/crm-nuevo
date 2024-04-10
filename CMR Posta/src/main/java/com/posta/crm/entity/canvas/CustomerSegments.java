
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
public class CustomerSegments {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    

    @Column(length = 3000)
    private String  demograficas;
    
    @Column(length = 3000)
    private String  geograficas;
    
    @Column(length = 3000)
    private String  psicograficas;
    
    @Column(length = 3000)

    private String  comportanmiento;
    
    
    
}
