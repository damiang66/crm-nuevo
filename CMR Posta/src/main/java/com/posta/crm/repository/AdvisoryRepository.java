
package com.posta.crm.repository;

import com.posta.crm.entity.Advisory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvisoryRepository extends JpaRepository<Advisory, Long>{
    
    @Query("SELECT a FROM Advisory a WHERE a.user.id=?1")
    public Page<Advisory>findByUser(@Param("user_id") Long userId, Pageable pageable);
    public Page<Advisory>findAll(Pageable pageable);
}
