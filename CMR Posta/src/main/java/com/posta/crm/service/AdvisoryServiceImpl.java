
package com.posta.crm.service;

import com.posta.crm.entity.Advisory;
import com.posta.crm.repository.AdvisoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AdvisoryServiceImpl implements IAdvisoryService{
    
    @Autowired
    private AdvisoryRepository advisoryRepository;
            
    @Override
    public Page<Advisory> findAll(Pageable pageable) {
        return advisoryRepository.findAll(pageable);
    }

    @Override
    public Page<Advisory> findByUser(Long userId, Pageable pageable) {
        return advisoryRepository.findByUser(userId, pageable);
    }

    @Override
    public Advisory save(Advisory advisory) {
       return advisoryRepository.save(advisory);
               }

    
}
