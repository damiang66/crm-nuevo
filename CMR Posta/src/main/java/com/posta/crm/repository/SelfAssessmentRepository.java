
package com.posta.crm.repository;

import com.posta.crm.entity.SelfAssessment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface SelfAssessmentRepository extends JpaRepository<SelfAssessment, Long>{
    
    @Query("SELECT p FROM SelfAssessment p WHERE p.client.id = ?1")
    Optional<SelfAssessment>findByClientId(Long clientId);
    
}
