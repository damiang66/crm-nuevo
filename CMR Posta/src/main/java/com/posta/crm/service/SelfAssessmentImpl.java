
package com.posta.crm.service;


import com.posta.crm.entity.SelfAssessment;
import com.posta.crm.enums.Answer;
import com.posta.crm.repository.SelfAssessmentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SelfAssessmentImpl implements ISelfAssessmentService{
    @Autowired
    private SelfAssessmentRepository selfAssessmentRepository;

   

    @Override
    public Page<SelfAssessment> findAll(Pageable pageable) {
        return selfAssessmentRepository.findAll(pageable);
    }

    @Override
    public SelfAssessment save(SelfAssessment selfAssessment) {
        List<Answer> answers=selfAssessment.getSelfAssessment();
        int score=0;
        for (Answer answer : answers) {
            if(answer.toString().equals("SI")){
                score+=3;
            }else if(answer.toString().equals("QUIZAS")){
                score+=2;
            }
        }
        selfAssessment.setScore(score);
        return selfAssessmentRepository.save(selfAssessment);
    }
    
}
