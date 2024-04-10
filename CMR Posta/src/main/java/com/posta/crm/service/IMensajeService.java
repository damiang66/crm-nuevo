/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.posta.crm.service;

import com.posta.crm.entity.Mensaje;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author crowl
 */
public interface IMensajeService {
    
    
    public List<Mensaje>findAll();
    public Optional<Mensaje> findById(Long id);
    public Mensaje save(Mensaje mensaje);
    public List<Mensaje>findByRemitenteId(Long id);
}
