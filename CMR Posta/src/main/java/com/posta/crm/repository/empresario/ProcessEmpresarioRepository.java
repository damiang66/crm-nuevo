/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.posta.crm.repository.empresario;

import com.posta.crm.entity.ProcessEmpresario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author crowl
 */
@Repository
public interface ProcessEmpresarioRepository extends JpaRepository<ProcessEmpresario, Long>{
     List<ProcessEmpresario> findTop6ByOrderByFechaAltaDesc();
   // List<Process>findByNombreContainingIgnoreCase(String term);
    // buscar por nombre de cliente mientras tipiamos
    @Query("select p from ProcessEmpresario p where p.client.name like %?1%")
    List<ProcessEmpresario>findByNombreCliente(String termino);
    @Query("select p from ProcessEmpresario p where p.client.type = ?1")
    List<ProcessEmpresario>findByTypeCliente(String type);
    @Query("select p from ProcessEmpresario p where p.terminado = ?1")
    List<ProcessEmpresario>findByTerminado(Boolean terminado);
    List<ProcessEmpresario>findByEstado(String estado);
    @Query("select p from ProcessEmpresario p where p.client.id = ?1")
    public ProcessEmpresario findByClient(Long id);
    
}
