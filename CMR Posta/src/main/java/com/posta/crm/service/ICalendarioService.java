/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.posta.crm.service;

import com.posta.crm.entity.Calendario;
import java.util.List;

/**
 *
 * @author crowl
 */
public interface ICalendarioService {
    
    public List<Calendario>findByUserId(Long userId);
    public Calendario save(Calendario calendario);
     
}
