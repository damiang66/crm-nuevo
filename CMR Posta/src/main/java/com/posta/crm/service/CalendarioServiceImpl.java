/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.posta.crm.service;


import com.posta.crm.entity.Calendario;
import com.posta.crm.repository.CalendarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author crowl
 */
@Service
public class CalendarioServiceImpl implements ICalendarioService{
    
    @Autowired
    private CalendarioRepository calendarioRepository;

    @Override
    public List<Calendario> findByUserId(Long userId) {
        return calendarioRepository.findByUsuarioId(userId);
    }

    @Override
    public Calendario save(Calendario calendario) {
        return calendarioRepository.save(calendario);
    }
    
}
