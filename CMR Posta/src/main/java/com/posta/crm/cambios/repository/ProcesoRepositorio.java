package com.posta.crm.cambios.repository;

import com.posta.crm.cambios.entity.Proceso;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface ProcesoRepositorio extends MongoRepository<Proceso,String> {

    List<Proceso> findTop6ByOrderByFechaAltaDesc();

    @Query("{ 'canvasModel.client.name': { $regex:?0, $options: 'i' } }")
    List<Proceso> findByNombreCliente(String termino);

    @Query("{ 'processEmpresario.client.name': { $regex:?0, $options: 'i' } }")
    List<Proceso> findByNombreClienteE(String termino);

    @Query("{ 'canvasModel.client.type':?0 }")
    List<Proceso> findByTypeCliente(String type);

    @Query("{ 'processEmpresario.client.type':?0 }")
    List<Proceso> findByTypeClienteE(String type);

    @Query("{ 'terminado':?0 }")
    List<Proceso> findByTerminado(Boolean terminado);

    List<Proceso> findByEstado(String estado);

    @Query("{ 'user.id':?0 }")
    Page<Proceso> buscarPorUsuario(Long id, Pageable pageable);

    @Query("{ 'client.id':?0 }")
    List<Proceso> findProcessesByClientId(Long clientId);
}
