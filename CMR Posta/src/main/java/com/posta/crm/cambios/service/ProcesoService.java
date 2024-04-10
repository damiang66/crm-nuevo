package com.posta.crm.cambios.service;

import com.posta.crm.cambios.entity.Proceso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface ProcesoService {
    public List<Proceso> findAll();
    public Optional<Proceso> findById(String id);
    public List<Proceso> findProcessesByClientId(Long clientId);
    public Proceso save(Proceso process);
    public List<Proceso>findAllUltimo();
    List<Proceso>findByNombreCliente(String termino);
    List<Proceso>findByTypeCliente(String type);
    List<Proceso>findByTerminado(Boolean terminado);
    List<Proceso>findByEstado(String estado);
    public Page<Proceso> paginacion(Pageable pageable);
    Page<Proceso> buscarPorUsuario(Long id, Pageable pageable);

}
