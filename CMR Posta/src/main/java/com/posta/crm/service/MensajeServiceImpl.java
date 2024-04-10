/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.posta.crm.service;

import com.posta.crm.entity.Mensaje;
import com.posta.crm.repository.MensajeRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author crowl
 */
@Service
public class MensajeServiceImpl implements IMensajeService{
    
    @Autowired
    private MensajeRepository mensajeRepository;
    

    @Override
    public List<Mensaje> findAll() {
        return mensajeRepository.findAll();
    }

    @Override
    public Optional<Mensaje> findById(Long id) {
        return mensajeRepository.findById(id);
    }

    @Override
    public Mensaje save(Mensaje mensaje) {
        return mensajeRepository.save(mensaje);
    }

    @Override
    public List<Mensaje> findByRemitenteId(Long id) {
        return mensajeRepository.findMensajesByUser(id);
    }
    
}
