
package com.posta.crm.service;

import com.posta.crm.entity.Advisory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface IAdvisoryService {
    
    public Page<Advisory>findAll(Pageable pageable);
    public Page<Advisory>findByUser(@Param("user_id") Long userId, Pageable pageable);
    public Advisory save(Advisory advisory);
}
