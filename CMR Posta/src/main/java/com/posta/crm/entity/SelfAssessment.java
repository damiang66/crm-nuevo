
package com.posta.crm.entity;

import com.posta.crm.enums.Answer;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.Data;

@Entity
@Data
public class SelfAssessment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(value = EnumType.STRING)
    private List<Answer>selfAssessment;
    private Integer score;
    
    @OneToOne
    private Client client;
    
    
}
