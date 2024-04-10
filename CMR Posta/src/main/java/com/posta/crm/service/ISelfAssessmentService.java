
package com.posta.crm.service;

import com.posta.crm.entity.SelfAssessment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ISelfAssessmentService {
    
    public SelfAssessment save(SelfAssessment selfAssessment);
    public Page<SelfAssessment>findAll(Pageable pageable);
}
