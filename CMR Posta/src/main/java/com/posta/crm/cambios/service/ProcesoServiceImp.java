package com.posta.crm.cambios.service;

import com.posta.crm.cambios.entity.Proceso;
import com.posta.crm.cambios.repository.ProcesoRepositorio;

import com.posta.crm.service.IClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProcesoServiceImp implements ProcesoService {
    @Autowired
    private ProcesoRepositorio processRepository;
    @Override
    public List<Proceso> findAll() {
        return processRepository.findAll();
    }

    @Override
    public Optional<Proceso> findById(String id) {
        return processRepository.findById(id);
    }

    @Override
    public Proceso save(Proceso process) {
        return processRepository.save(process);
    }

    @Override
    public List<Proceso> findAllUltimo() {
        return processRepository.findTop6ByOrderByFechaAltaDesc();
    }

    @Override
    public List<Proceso> findByNombreCliente(String termino) {
        return processRepository.findByNombreCliente(termino);
    }

    @Override
    public List<Proceso> findByTypeCliente(String type) {
        if(type.equals("entrepreneur")){
            return processRepository.findByTypeCliente(type);
        }
        return processRepository.findByTypeClienteE(type);

    }

    @Override
    public List<Proceso> findByTerminado(Boolean terminado) {
        return processRepository.findByTerminado(terminado);
    }

    @Override
    public List<Proceso> findByEstado(String estado) {
        return processRepository.findByEstado(estado);
    }
    @Override
    public Page<Proceso> paginacion(Pageable pageable) {
        return processRepository.findAll(pageable);
    }

    @Override
    public Page<Proceso> buscarPorUsuario(Long id, Pageable pageable) {
        return processRepository.buscarPorUsuario(id,pageable);
    }



    @Override
    public List<Proceso> findProcessesByClientId(Long clientId) {
        return processRepository.findProcessesByClientId(clientId);
    }
}
