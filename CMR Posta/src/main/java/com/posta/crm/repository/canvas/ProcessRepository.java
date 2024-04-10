package com.posta.crm.repository.canvas;

import com.posta.crm.entity.Process;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProcessRepository extends JpaRepository<Process,Long> {
    List<Process> findTop6ByOrderByFechaAltaDesc();
   // List<Process>findByNombreContainingIgnoreCase(String term);
    // buscar por nombre de cliente mientras tipiamos
    @Query("select p from Process p where p.canvasModel.client.name like %?1%")
    List<Process>findByNombreCliente(String termino);
 @Query("select p from Process p where p.processEmpresario.client.name like %?1%")
 List<Process>findByNombreClienteE(String termino);

    @Query("select p from Process p where p.canvasModel.client.type = ?1")
    List<Process>findByTypeCliente(String type);
    @Query("select p from Process p where p.processEmpresario.client.type = ?1")
    List<Process>findByTypeClienteE(String type);
    @Query("select p from Process p where p.terminado = ?1")
    List<Process>findByTerminado(Boolean terminado);
    List<Process>findByEstado(String estado);
    @Query("select p from Process p where p.user.id = ?1")
    Page<Process> buscarPorUsuario(Long id, Pageable pageable);
    
    @Query("SELECT p FROM Process p WHERE p.client.id = ?1")
List<Process> findProcessesByClientId(Long clientId);

}

