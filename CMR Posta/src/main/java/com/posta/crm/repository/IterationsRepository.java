package com.posta.crm.repository;

import com.posta.crm.entity.Iterations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IterationsRepository extends JpaRepository<Iterations, Long>{
    
}
