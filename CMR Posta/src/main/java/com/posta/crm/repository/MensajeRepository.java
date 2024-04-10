/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.posta.crm.repository;

import com.posta.crm.entity.Mensaje;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author crowl
 */
@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long>{
    public List<Mensaje>findByRemitenteId(Long id);
    
    @Query("SELECT m FROM Mensaje m WHERE m.remitente.id = :userId OR m.destinatario.id = :userId")
    List<Mensaje> findMensajesByUser(Long userId);
    
}
