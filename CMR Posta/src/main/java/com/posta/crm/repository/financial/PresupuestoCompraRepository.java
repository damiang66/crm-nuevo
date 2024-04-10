/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.posta.crm.repository.financial;

import com.posta.crm.entity.financiero.PresupuestoCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author crowl
 */
@Service
public interface PresupuestoCompraRepository extends JpaRepository<PresupuestoCompra, Long>{
    
}
