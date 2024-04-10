
package com.posta.crm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Municipio {
    
    @Id
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String country;
    
}
