
package com.posta.crm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Entrepreneur extends Client{
    
    @NotBlank
    private String businessIdea;
    @NotBlank
    private String product;
    
    @PrePersist
    public void types(){
        this.setType("entrepreneur");
    }
}
