
package com.posta.crm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.posta.crm.enums.Contracting;
import com.posta.crm.enums.TypeOfCompany;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
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
public class Businessman extends Client{

    @Enumerated(value = EnumType.STRING)
    private Contracting contracting;
    @NotBlank
    private String companyName;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Bogota")
    private Date fechaAlta;
    @Enumerated(value = EnumType.STRING)
    private TypeOfCompany typeOfCompany;
    @NotNull
    private Integer employeePartTime;
    @NotNull
    private Integer employeeFullTime;
    private Boolean registroMercantil;
    private String numberMercantilRegistry;
    @ManyToOne
    private Actividades ciiu;
    
    @PrePersist
    public void types(){
        this.setType("businessman");
    }
    
    
    
    
    
}
